package next.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class RowMapper {
	private ArrayList<Row> rows;
	private int columnCount = 0;

	public RowMapper(ResultSet rs) throws SQLException {
		rows = new ArrayList<Row>();
		ResultSetMetaData columns = rs.getMetaData();
		columnCount = columns.getColumnCount();

		ArrayList<String> columnNames = new ArrayList<String>();
		for (int i = 1; i < columnCount + 1; i++) {
			columnNames.add(columns.getColumnName(i).toUpperCase());
		}

		while (rs.next()) {
			Row row = new Row();
			for (String column : columnNames) {
				row.addValue(column, rs.getString(column));
			}
			rows.add(row);
		}
	}

	public String getValue(String columnName, int index) {
		Row row = rows.get(index);
		return row.getString(columnName.toUpperCase());
	}

	public int size() {
		return rows.size();
	}

	public int columnCount() {
		return columnCount;
	}
}