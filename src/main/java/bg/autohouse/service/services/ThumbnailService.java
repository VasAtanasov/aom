package bg.autohouse.service.services;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface ThumbnailService {

  String generateThumbnailUrl(String url);

  String generateScreenshotApiUrl(Map<String, String> options) throws UnsupportedEncodingException;

  String generatePdfApiUrl(Map<String, String> options) throws UnsupportedEncodingException;
}
