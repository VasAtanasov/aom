package bg.autohouse.web.controllers;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(HealthCheckController.URI_HEALTH_CHECK)
public class HealthCheckController {
  public static final String URI_HEALTH_CHECK = " /app-health";

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public HealthCheckController(final DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @GetMapping
  public ResponseEntity<String> health() {
    try {
      jdbcTemplate.queryForObject("SELECT 1", Integer.class);
      return ResponseEntity.ok("ok");

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Could not connect database");
    }
  }
}
