package core.di.factory;

import static org.junit.Assert.assertNotNull;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
import core.di.factory.example.MyQnaService;
import core.di.factory.example.QnaController;

public class BeanFactoryTest {
	private static final Logger logger = LoggerFactory.getLogger(BeanFactoryTest.class);
    private Reflections reflections;
    private BeanFactory beanFactory;

    @Before
    @SuppressWarnings("unchecked")
    public void setup() {
        reflections = new Reflections("core.di.factory.example");
        Set<Class<?>> preInstantiateClazz = getTypesAnnotatedWith(Controller.class, Service.class, Repository.class);
        beanFactory = new BeanFactory(preInstantiateClazz);
        beanFactory.initialize();
    }

    @Test
    public void di() throws Exception {
        QnaController qnaController = beanFactory.getBean(QnaController.class);

        assertNotNull(qnaController);
        assertNotNull(qnaController.getQnaService());

        MyQnaService qnaService = qnaController.getQnaService();
        logger.debug("injected UserRepository impl is : {}" , qnaService.getUserRepository().getClass().getName());
        logger.debug("injected QuestionRepository impl is : {}", qnaService.getQuestionRepository().getClass().getName());
        assertNotNull(qnaService.getUserRepository());
        assertNotNull(qnaService.getQuestionRepository());
        
        
    }
    
    @Test
    public void findAnnotatedControllers() throws Exception { 
    	
    		Map<Class<?>, Object> controllers = beanFactory.getControllers();
    		assertNotNull(controllers);
    		controllers.values().stream().forEach(c -> logger.debug("야생의 컨트롤러이(가) 등장했다! 이름은 : {}", c.getClass().getName()));
    }


    @SuppressWarnings("unchecked")
    private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
        Set<Class<?>> beans = Sets.newHashSet();
        for (Class<? extends Annotation> annotation : annotations) {
            beans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return beans;
    }
}
