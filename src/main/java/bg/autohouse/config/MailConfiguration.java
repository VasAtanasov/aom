package bg.autohouse.config;

import bg.autohouse.config.properties.MailProperties;
import java.util.Collections;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

@Slf4j
@Configuration
@Import(MailProperties.class)
public class MailConfiguration {

  @Autowired private MailProperties mailProperties;

  @Bean
  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);
    mailSender.setProtocol("smtp");
    mailSender.setUsername(mailProperties.getDefaultFromAddress());
    mailSender.setPassword(mailProperties.getPassword());
    Properties props = mailProperties.getJavaMailProperties();
    mailSender.setJavaMailProperties(props);
    return mailSender;
  }

  @Bean
  public ResourceBundleMessageSource htmlEmailSource() {
    final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("mail/messages");
    return messageSource;
  }

  @Bean(name = "emailTemplateEngine")
  public TemplateEngine emailTemplateEngine() {
    log.info("Constructing email template engine");
    final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.addTemplateResolver(textTemplateResolver());
    log.info("Added text template resolver");
    templateEngine.addTemplateResolver(htmlTemplateResolver());
    templateEngine.addTemplateResolver(stringTemplateResolver());
    templateEngine.setTemplateEngineMessageSource(htmlEmailSource());
    log.info("Email template engine constructed");
    return templateEngine;
  }

  private ITemplateResolver textTemplateResolver() {
    final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setOrder(1);
    templateResolver.setResolvablePatterns(Collections.singleton("text/*"));
    templateResolver.setPrefix("/mail/");
    templateResolver.setSuffix(".txt");
    templateResolver.setTemplateMode(TemplateMode.TEXT);
    templateResolver.setCharacterEncoding("UTF-8");
    templateResolver.setCacheable(false);
    return templateResolver;
  }

  private ITemplateResolver htmlTemplateResolver() {
    final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setOrder(2);
    templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
    templateResolver.setPrefix("/mail/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCharacterEncoding("UTF-8");
    templateResolver.setCacheable(false);
    return templateResolver;
  }

  private ITemplateResolver stringTemplateResolver() {
    final StringTemplateResolver templateResolver = new StringTemplateResolver();
    templateResolver.setOrder(3);
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCacheable(false);
    return templateResolver;
  }
}
