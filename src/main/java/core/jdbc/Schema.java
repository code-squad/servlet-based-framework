package core.jdbc;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Schema {
	
	private Map<Integer, String> items = new HashMap<Integer, String>();
	
	public Schema(Class<?> targetClass) {
		Field[] fields = targetClass.getDeclaredFields();
		int index = 1;
		
		for (Field f : fields) {
			items.put(index, f.getName());
		}
		
	}
	
	public boolean isCorrectPosition(int index, String fieldName) {
		return items.get(index).toLowerCase().equals(fieldName.toLowerCase());
	}
	
	public String getField(int index) {
		return items.get(index);
	}
	
}
