package bg.autohouse.web.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JsonReaderUtil {

  public static String readJsonContent(String fileName) {
    Path source =
        Paths.get("src", "test", "resources", "bg", "autohouse", "web", "controllers", fileName);
    try (Stream<String> lines = Files.lines(source)) {
      return String.join("\n", lines.collect(Collectors.toList()));
    } catch (IOException e) {
      System.err.format("IOException: %s%n", e);
      return "";
    }
  }
}
