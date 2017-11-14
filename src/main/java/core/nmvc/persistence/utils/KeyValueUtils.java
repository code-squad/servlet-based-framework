package core.nmvc.persistence.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

import core.annotation.ColumnOrder;
import core.nmvc.persistence.KeyValue;
import core.nmvc.persistence.PrimaryKey;
import core.nmvc.persistence.Value;

public class KeyValueUtils {
	private static final Logger log = LoggerFactory.getLogger(KeyValueUtils.class);
	
	public static Set<KeyValue> keyValueParser(Object o) {
		Class<?> clazz = o.getClass();
		Set<KeyValue> returnSet = Sets.newHashSet();
		
		PrimaryKey pk = new PrimaryKey(o);
		Arrays.stream(clazz.getDeclaredFields()).peek(f -> f.setAccessible(true))
				.filter(f -> f.isAnnotationPresent(ColumnOrder.class))
				.forEach(f -> {
			ColumnOrder co = f.getAnnotation(ColumnOrder.class);
			
			try {
				returnSet.add(new Value(f.get(o), co.order()));
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		});
		returnSet.add(pk);
		return returnSet;
	}

}
