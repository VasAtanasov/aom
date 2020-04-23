package bg.autohouse.service.services.impl;

import static org.assertj.core.api.Assertions.assertThat;

import bg.autohouse.config.DatabaseSeeder;
import bg.autohouse.data.models.User;
import bg.autohouse.data.projections.user.UserIdUsername;
import bg.autohouse.data.repositories.UserRepository;
import bg.autohouse.service.services.AdminService;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
@TestPropertySource("classpath:test.properties")
public class AdminServiceImplTest {
  static final Charset ENCODING = StandardCharsets.UTF_8;
  static List<String> emails = new ArrayList<>();

  @Autowired private AdminService adminService;
  @Autowired private UserRepository userRepository;

  @BeforeAll
  static void init() throws IOException {
    Path source = Paths.get("src", "test", "resources", "data", "mails.txt");
    File file = source.toFile();
    assertThat(file.exists()).isTrue();
    readSmallTextFile(source).forEach(emails::add);
  }

  @Test
  public void bulk_insert_test() throws IOException {
    User user = userRepository.findByUsernameIgnoreCase(DatabaseSeeder.ROOT_USERNAME).orElse(null);
    adminService.bulkRegisterUsers(user.getId(), emails);
  }

  @Test
  void when_loadAllUsers_shouldGetAll() {
    List<UserIdUsername> users = adminService.loadAllUsers();
    assertThat(users).isNotEmpty();
  }

  static List<String> readSmallTextFile(Path path) throws IOException {
    return Files.lines(path)
        .map(line -> line.split(","))
        .flatMap(Arrays::stream)
        .collect(Collectors.toList());
  }
}
