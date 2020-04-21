package bg.autohouse.service.services.impl;

import bg.autohouse.service.services.ThumbnailService;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ThumbnailServiceImpl implements ThumbnailService {

  private static final String API_BASE_URL = "https://api.screenshotmachine.com/?";
  private static final String PDF_API_BASE_URL = "https://pdfapi.screenshotmachine.com/?";

  @Value("${app.screenshot-machine.customer-key}")
  private String customerKey;

  @Override
  public String generateThumbnailUrl(String url) {
    Map<String, String> options = new HashMap<>();
    options.put("url", url);
    try {
      return generateUrl(API_BASE_URL, options);
    } catch (UnsupportedEncodingException e) {
      log.error("Thumbnail creation failed.");
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public String generateScreenshotApiUrl(Map<String, String> options)
      throws UnsupportedEncodingException {
    return generateUrl(API_BASE_URL, options);
  }

  @Override
  public String generatePdfApiUrl(Map<String, String> options) throws UnsupportedEncodingException {
    return generateUrl(PDF_API_BASE_URL, options);
  }

  private String generateUrl(String baseUrl, Map<String, String> options)
      throws UnsupportedEncodingException {
    StringBuilder apiUrl = new StringBuilder(baseUrl);
    apiUrl.append("key=").append(customerKey);

    for (String key : options.keySet()) {
      apiUrl
          .append("&")
          .append(key)
          .append("=")
          .append(URLEncoder.encode(options.get(key), StandardCharsets.UTF_8.toString()));
    }
    return apiUrl.toString();
  }
}
