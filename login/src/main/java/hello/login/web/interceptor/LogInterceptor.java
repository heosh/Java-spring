package hello.login.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        request.setAttribute(LOG_ID, uuid);

        /**
         * handler : 어떤 컨트롤러가 호출되는지 확인할 수 있음
         * @RequestMapping: HandlerMethod
         * RequestMapping 을 활용한 핸들러 매핑을 사용하는데, 이 경우 핸들러 정보로
         * HandlerMethod 가 넘어온다.
         *
         * 정적 리소스: ResourceHttpRequestHandler
         * 정적 리소스가 호출 되는 경우
         * ResourceHttpRequestHandler 가 핸들러 정보로 넘어온다.
         */
        if(handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;// 호출할 컨트롤러 메서드의 모든 정보가 포함되어 있음.

        }
        log.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        Object logId = request.getAttribute(LOG_ID);
        log.info("RESPONSE [{}][{}][{}]", logId, requestURI, handler);
        if(ex != null) {
            log.error("afterCompletion error!!", ex);
        }
    }
}
