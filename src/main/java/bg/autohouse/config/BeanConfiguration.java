package bg.autohouse.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Properties;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

// TODO Uncomment when spring security dependency is added
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeanConfiguration {
  private static ModelMapper mapper;

  static {
    mapper = new ModelMapper();
  }

  @Bean
  public ModelMapper modelMapper() {

    mapper
        .getConfiguration()
        .setFieldAccessLevel(AccessLevel.PRIVATE)
        .setFieldMatchingEnabled(true);

    return mapper;
  }

  @Bean
  public Gson gson() {
    return new GsonBuilder().setPrettyPrinting().create();
  }

  // TODO Uncomment when spring security dependency is added
  // @Bean
  // public BCryptPasswordEncoder bCryptPasswordEncoder() {
  //   return new BCryptPasswordEncoder();
  // }

  @Bean
  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);

    mailSender.setUsername("");
    mailSender.setPassword("");
    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");
    return mailSender;
  }

  // @Bean
  // public MessageSource messageSource() {
  //   ReloadableResourceBundleMessageSource messageSource =
  //       new ReloadableResourceBundleMessageSource();
  //   messageSource.setBasename("classpath:messages");
  //   messageSource.setDefaultEncoding("UTF-8");
  //   return messageSource;
  // }

  // @Bean
  // public LocalValidatorFactoryBean validator() {
  //   LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
  //   bean.setValidationMessageSource(messageSource());
  //   return bean;
  // }
}
