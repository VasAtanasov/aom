package com.github.vaatech.aom.test;

import com.github.vaatech.aom.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

@Import({Application.class})
public class SpringBootDevApp {
  public static void main(String[] args) {
    System.setProperty("spring.profiles.active", "test");
    SpringApplication.run(SpringBootDevApp.class, args);
  }
}
