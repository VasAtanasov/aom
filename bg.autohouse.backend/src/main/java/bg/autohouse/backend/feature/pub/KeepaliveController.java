package bg.autohouse.backend.feature.pub;

import bg.autohouse.backend.feature.RuntimeEnvironmentUtil;
import bg.autohouse.backend.feature.pub.maker.dao.MakerRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class KeepaliveController {

  private final MakerRepository makerRepository;
  private final RuntimeEnvironmentUtil runtimeEnvironmentUtil;

  public KeepaliveController(MakerRepository makerRepository, RuntimeEnvironmentUtil runtimeEnvironmentUtil) {
    this.makerRepository = makerRepository;
    this.runtimeEnvironmentUtil = runtimeEnvironmentUtil;
  }

  @RequestMapping(value = "/api/ping", produces = MediaType.TEXT_PLAIN_VALUE)
  public String ping(HttpSession session) {
    session.setAttribute("ts", System.currentTimeMillis());

    return "ok";
  }
}
