package bg.autohouse.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtil {

  public static String readFileContent(String fileName) {
    Path source = Paths.get("src", "test", "resources", "bg", "autohouse", fileName);
    try (Stream<String> lines = Files.lines(source)) {
      return String.join("\n", lines.collect(Collectors.toList()));
    } catch (IOException e) {
      System.err.format("IOException: %s%n", e);
      return "";
    }
  }
}
