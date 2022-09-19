package bg.autohouse.spider.util;

import bg.autohouse.spider.util.json.JsonUtil;
import bg.autohouse.spider.util.json.ObjectMapperFactory;
import bg.autohouse.spider.util.mappings.Mapping;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class MappingsUtil {
  private static final Path MAPPINGS_BASE_FOLDER_PATH =
      Paths.get("/home/vas/projects/wiremock-mappings/original");
  private static final Path MAPPINGS_EDITED_FOLDER_PATH =
      Paths.get("/home/vas/projects/wiremock-mappings/edited");

  private static final Path ORIGINAL_MAPPINGS_FOLDER_PATH =
      MAPPINGS_BASE_FOLDER_PATH.resolve("mappings");
  private static final Path ORIGINAL_FILES_FOLDER_PATH =
      MAPPINGS_BASE_FOLDER_PATH.resolve("__files");

  private static final Path EDITED_MAPPINGS_FOLDER_PATH =
      MAPPINGS_EDITED_FOLDER_PATH.resolve("mappings");
  private static final Path EDITED_FILES_FOLDER_PATH =
      MAPPINGS_EDITED_FOLDER_PATH.resolve("__files");

  private static final Set<String> urls = new HashSet<>();

  public static void main(String[] args) {

    try (Stream<Path> walkMappings = Files.walk(ORIGINAL_MAPPINGS_FOLDER_PATH);
        Stream<Path> walkFiles = Files.walk(ORIGINAL_FILES_FOLDER_PATH)) {

      var mappingPaths = walkMappings.filter(Files::isRegularFile).toList();

      for (Path mappingPath : mappingPaths) {
        var file = mappingPath.toFile();
        Mapping mapping = toMapping(file);
        if (mapping == null) continue;
        add(mapping, urls);
        Mapping edited = Mapping.from(mapping);
        String json = JsonUtil.toJSON(edited);
        if (json == null) continue;
        var filename = file.getName();
        Path destination = EDITED_MAPPINGS_FOLDER_PATH.resolve(filename);
        Files.writeString(destination, json, StandardCharsets.UTF_8);

        int a = 5;
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void add(Mapping mapping, Set<String> urls) {
    if ("POST".equals(mapping.getRequest().getMethod())) {
      List<Map<String, String>> bodyPatterns = mapping.getRequest().getBodyPatterns();
      urls.add(bodyPatterns.get(0).get("equalTo"));
      return;
    }
    urls.add(mapping.getRequest().getUrl());
  }

  private static Mapping toMapping(File jsonFile) {
    try {
      return ObjectMapperFactory.mapper().readValue(jsonFile, Mapping.class);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private static String readString(Path path) {
    try {
      return Files.readString(path);
    } catch (IOException e) {
      e.printStackTrace();
      return "";
    }
  }

  private static Stream<String> readLines(Path path) {
    try {
      return Files.lines(path);
    } catch (IOException e) {
      e.printStackTrace();
      return Stream.empty();
    }
  }
}
