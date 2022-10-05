package bg.autohouse.backend.config;

import bg.autohouse.backend.feature.pub.KeepaliveController;
import bg.autohouse.backend.feature.pub.maker.dao.MakerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestControllerBeanConfiguration {
  @Bean
  public KeepaliveController keepaliveController(MakerRepository makerRepository) {
    return new KeepaliveController(makerRepository);
  }
}
