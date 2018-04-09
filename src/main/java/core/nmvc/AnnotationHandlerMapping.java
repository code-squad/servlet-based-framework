package core.nmvc;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import org.reflections.Reflections;
import static org.reflections.ReflectionUtils.*;
public class AnnotationHandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {// url + HttpMethod 와 해당 컨트롤러의 @RequestMapping 이 달린 메소드를 매칭 시키는 것 같음.
        Reflections reflections = new Reflections(basePackage);
        // @Controller 가 달린 클래스들만 추출해 Set에 담기
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
        annotated.forEach(annotatedClass -> {
            // 클래스의 모든 메소드.
            Method[] allMethods  = annotatedClass.getDeclaredMethods();
            // 1. @RequestMapping 붙은 method 만 필터.
            List<Method> annotatedMethods = Arrays.stream(allMethods).filter(m -> m.isAnnotationPresent(RequestMapping.class)
            ).collect(Collectors.toList());
            // 2. 필터한 메소드 맵에 넣는다.
            annotatedMethods.forEach(m -> {
                RequestMapping requestMapping = m.getAnnotation(RequestMapping.class);
                handlerExecutions.put(new HandlerKey(requestMapping.value(), requestMapping.method()), new HandlerExecution());
            });
        });

    }

    public HandlerExecution getHandler(HttpServletRequest request) {// 해당 url 과 http method 에 해당하는 handlerExecution 을 가져옴.
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
        // handlerexecution.handle(); -> requestMapping 달린 해당 메소드 실행.
    }
}
