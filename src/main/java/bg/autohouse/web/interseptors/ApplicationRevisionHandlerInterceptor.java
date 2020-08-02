package bg.autohouse.web.interseptors;

import bg.autohouse.service.services.AdminService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@RequiredArgsConstructor
public class ApplicationRevisionHandlerInterceptor extends HandlerInterceptorAdapter {

  private final AdminService adminService;

  @Override
  public final boolean preHandle(
      final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
    if (shouldAddRevisionHeader(request, response)) {
      response.setHeader("X-Revision", adminService.getRevision());
    }

    return true;
  }

  private static boolean shouldAddRevisionHeader(
      final HttpServletRequest request, final HttpServletResponse response) {
    return !response.isCommitted() && request.getRequestURI().startsWith("/api");
  }
}
