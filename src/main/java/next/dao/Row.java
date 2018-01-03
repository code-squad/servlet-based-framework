package next.dao;

import java.util.HashMap;

public class Row {
	private HashMap<String, String> row;

	public Row() {
		row = new HashMap<String, String>();
	}
	
	public void addValue(String column, String value) {
		row.put(column.toUpperCase(), value);
	}
	
	public String getString(String column) {
		return row.get(column);
	}

	public HashMap<String, String> getRow() {
		return row;
	}
}
