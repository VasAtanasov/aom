package bg.autohouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Autohouse {
  static {
    Thread.currentThread().setName("Vili");
  }

  public static void main(String[] args) {
    SpringApplication.run(Autohouse.class, args);
  }
}