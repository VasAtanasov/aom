package bg.autohouse.web.interseptors;

import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Slf4j
public class SimpleLoggingInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(
      final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
    log.debug("Handling request, IP appears as: {}", request.getRemoteAddr());
    final List<String> headers = Collections.list(request.getHeaderNames());
    final Map<String, String> allHeaders =
        headers.stream().collect(Collectors.toMap(name -> name, request::getHeader));
    log.info("All headers: {}", allHeaders);
    final long startTime = System.currentTimeMillis();
    request.setAttribute("startTime", startTime);
    return true;
  }

  @Override
  public void postHandle(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final Object handler,
      final ModelAndView modelAndView) {
    response.addHeader("X-Grassroot-Logged", "true");
    final long startTime = (Long) request.getAttribute("startTime");
    final long endTime = System.currentTimeMillis();
    final long executeTime = endTime - startTime;
    log.info("{} ms : [{}]", executeTime, handler);
  }
}
