package core.nmvc;

import java.util.Set;

import com.google.common.collect.Sets;

import core.mvc.requestmapping.LegacyRequestMapping;

public class HandlerMappingFactory {
	
	private static Set<HandlerMapping> mappers = Sets.newHashSet();
	
	public static Set<HandlerMapping> getSets(String basePackage) {
		
		AnnotationHandlerMapping ahm = new AnnotationHandlerMapping(basePackage);
		ahm.initialize();
		
		mappers.add(LegacyRequestMapping.getInstance());
		mappers.add(ahm);
		return mappers;
	}

}
