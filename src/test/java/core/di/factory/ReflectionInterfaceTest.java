package core.di.factory;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.Service;

import core.annotation.Controller;
import core.annotation.Repository;
import core.di.factory.example.JdbcUserRepository;
import core.di.factory.example.UserRepository;

public class ReflectionInterfaceTest {

	private static final Logger logger = LoggerFactory.getLogger(ReflectionInterfaceTest.class);

	private Reflections r;

	@Before
	public void setUp() {
		this.r = new Reflections("core.di.factory.example");
	}

	@Test
	public void initialize_test() {
		Set<Class<?>> clazz = r.getTypesAnnotatedWith(Repository.class);
		assertEquals(2, clazz.size());
		clazz.stream().forEach(c -> {
			logger.debug("is this an impl class of UserRepository? {} ", UserRepository.class.isAssignableFrom(c));
		});
	}
	
	@Test
	public void simple_reflections_Test() {
		Object repo = new JdbcUserRepository();
		assertTrue(UserRepository.class.isAssignableFrom(repo.getClass()));
	}

}
