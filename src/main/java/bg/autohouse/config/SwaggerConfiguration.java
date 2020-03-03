package bg.autohouse.config;

import bg.autohouse.Autohouse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

  private static final String SNAPSHOT = "1.0-SNAPSHOT";
  private static final String HTTP_WWW_APACHE_ORG_LICENSES_LICENSE_2_0_HTML =
      "Usage of these application code is forbidden without creator's approval";
  private static final String APACHE_2_0 = "Apache 2.0";

  @Bean
  public Docket api() {

    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        // .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        .apis(RequestHandlerSelectors.basePackage(Autohouse.class.getPackage().getName()))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiEndPointsInfo());
  }

  private ApiInfo apiEndPointsInfo() {

    return new ApiInfoBuilder()
        .title("Autohouse api")
        .description("Autohouse Api")
        .contact(new Contact("Vasil Atanasov", "NO URL", "vasil@atanasov.com"))
        .license(APACHE_2_0)
        .licenseUrl(HTTP_WWW_APACHE_ORG_LICENSES_LICENSE_2_0_HTML)
        .version(SNAPSHOT)
        .build();
  }
}
