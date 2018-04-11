package core.nmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class HandlerExecution {
    private static final Logger log = LoggerFactory.getLogger(HandlerExecution.class);
    private Class<?> clazz;
    private Method method;

    public HandlerExecution(Class<?> clazz, Method method) {// handlerExecution 이 class , method 정보 모두 갖고 있을 수 있도록 했다.
        this.clazz = clazz;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest req, HttpServletResponse res) {
        // 맵에서 해당 메소드 선택해 그 메소드 실행.
        // 해당 클래스 가져오기
        ModelAndView modelAndView = new ModelAndView();
        try {
            // 해당 메소드의 파라미터들 다 넣어주어야 함.
            modelAndView = (ModelAndView)this.method.invoke(this.clazz.newInstance(), req, res);
        } catch (IllegalAccessException e) {
            log.debug("IllegalAccessException occured");
        } catch (InvocationTargetException e) {
            log.debug("InvocationTargetException occured");
        } catch (InstantiationException e) {
            log.debug("InstantiationException occured");
        }
        return modelAndView;
    }

}
