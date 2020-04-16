package bg.autohouse.config;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DropboxConfiguration {

  @Value("${app.dropbox.access.token}")
  private String accessToken;

  @Bean
  public DbxClientV2 dropboxClient() {
    // TODO externalize folder
    DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/autohouse").build();
    return new DbxClientV2(config, accessToken);
  }
}
