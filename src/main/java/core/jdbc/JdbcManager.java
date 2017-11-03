package core.jdbc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import core.db.exceptions.MultipleDataException;

public class JdbcManager {

	private static JdbcManager jdbm;
	private Connection conn = ConnectionManager.getConnection();

	private JdbcManager() {

	}

	public static JdbcManager getInstance() {
		if (jdbm == null) {
			jdbm = new JdbcManager();
		}
		return jdbm;
	}

	public void insertObject(String sql, Object... objects) {
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
		System.out.println(queryBuilder);
		try {
			pstmt = conn.prepareStatement(queryBuilder.toString());
			setParameters(pstmt, object);

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

	private void setParameters(PreparedStatement pstmt, Object o) {
		Class<?> targetClass = o.getClass();

		List<Object> objects = new ArrayList<Object>();
		List<Method> getters = Arrays.asList(targetClass.getMethods()).stream().filter(m -> m.getName().contains("get"))
				.collect(Collectors.toList());

		try {
			int index = 1;
			for (Method m : getters) {
				System.out.println(m.getName());
				objects.add(m.invoke(o, (Object[]) null));

			}
			for (Object item : objects) {
				System.out.println(item);
				pstmt.setString(index++, item.toString());
				if (index > targetClass.getDeclaredFields().length) {
					break;
				}
			}

		} catch (Exception e) {
			throw new DataAccessException(e);
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
			sb.append(",");
		}
		sb.append(")");
		return sb.toString();

	}

}
