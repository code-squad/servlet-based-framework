package core.nmvc.persistence.utils;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import core.nmvc.persistence.KeyValue;
import next.model.User;

public class KeyValueUtilsTest {

	@Test
	public void test() {
		Set<KeyValue> values = KeyValueUtils.keyValueParser(new User("javajigi", "password", "박재성", "slipp@javajigi.net"));
		values.stream().forEach(System.out::println);
	}

}
