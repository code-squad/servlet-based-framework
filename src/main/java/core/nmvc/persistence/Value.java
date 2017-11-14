package core.nmvc.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Value implements KeyValue{
	private static final Logger log = LoggerFactory.getLogger(Value.class);
	
	private int columnIndex = 0;
	private Object columnValue = null;

	public Value(Object columnValue, int columnIndex) { 
		this.columnIndex = columnIndex;
		this.columnValue = columnValue;
	}

	@Override
	public Object getKeyObject() {
		return this.columnValue;
	}

	@Override
	public int getColumnIndex() {
		return this.columnIndex;
	}
	
	@Override
	public int compareTo(Object o) {
		return 0;
	}

}
