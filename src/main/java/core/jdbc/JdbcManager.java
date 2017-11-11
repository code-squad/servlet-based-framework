package core.jdbc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import core.db.exceptions.MultipleDataException;

public class JdbcManager {

	private static final JdbcManager jdbm = new JdbcManager();
	private Connection conn = ConnectionManager.getConnection();
	private static final Logger logger = LoggerFactory.getLogger(JdbcManager.class);

	private JdbcManager() {
	}

	public static JdbcManager getInstance() {
		return jdbm;
	}

	public final void insertObject(final String sql, Object... objects) {

		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			setParameters(pstmt, objects);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public void insertObject(String tableName, Object object) {
		PreparedStatement pstmt = null;

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("INSERT INTO ");
		queryBuilder.append(tableName + " VALUES ");
		queryBuilder.append(generateQueryStatement(object.getClass()));
		logger.debug(queryBuilder.toString());
		try {
			pstmt = conn.prepareStatement(queryBuilder.toString());
			setParametersByFields(pstmt, object);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException(e);
		}

	}

	public <T> T find(String sql, RowMapper<T> rm, Object... objects) {
		List<T> objectList = findAll(sql, rm, objects);
		if (objectList.isEmpty()) {
			return null;
		}
		if (objectList.size() > 1) {
			throw new MultipleDataException(MultipleDataException.STANDARD_MULTI_DATA_ERR_MSG);
		}
		return objectList.get(0);
	}

	public <T> List<T> findAll(String sql, RowMapper<T> rm, Object... objects) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<T> objectList = new ArrayList<T>();

		try {
			pstmt = conn.prepareStatement(sql);

			setParameters(pstmt, objects);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				objectList.add(rm.mapRow(rs));
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return objectList;
	}

	private void setParameters(PreparedStatement pstmt, Object... objects) {
		try {
			int index = 1;
			for (Object o : objects) {
				pstmt.setString(index++, (String) o);
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	private void setParametersByFields(PreparedStatement pstmt, Object o) {
		Class<?> targetClass = o.getClass();

		List<Field> fields = Arrays.asList(targetClass.getDeclaredFields());
		
		logger.debug(String.format("%d", fields.size()));
		int index = 1;
		
		
		for (Field f : fields) {
			f.setAccessible(true);
			try {
				logger.debug(f.get(o).toString());
				pstmt.setString(index, f.get(o).toString());
				index++;
			} catch (IllegalArgumentException | IllegalAccessException | SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private String generateQueryStatement(Class<?> targetClass) {
		Field[] allFields = targetClass.getDeclaredFields();
		StringBuilder sb = new StringBuilder();

		sb.append("(");
		for (int i = 0; i < allFields.length; i++) {
			sb.append("?");
			if (i == allFields.length - 1) {
				break;
			}
			sb.append(", ");
		}

		sb.append(")");
		return sb.toString();
	}
}
