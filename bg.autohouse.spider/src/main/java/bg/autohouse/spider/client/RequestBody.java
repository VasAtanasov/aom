package bg.autohouse.spider.client;

import bg.autohouse.spider.api.MediaType;
import bg.autohouse.spider.util.IOUtil;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public abstract class RequestBody implements Serializable {
  private static final String HEADER_NAME = "Content-Type";

  private final InputStream body;
  private Header contentType;

  protected RequestBody(InputStream body, String contentType) {
    this.body = body;
    setContentType(contentType);
  }

  public InputStream getBody() {
    return body;
  }

  public Header getContentType() {
    return contentType;
  }

  public void setContentType(Header contentType) {
    this.contentType = contentType;
  }

  public void setContentType(String contentType) {
    Header header = null;
    if (contentType != null) {
      header = new Header(HEADER_NAME, contentType);
    }
    this.setContentType(header);
  }

  public static class JsonRequestBody extends RequestBody {
    public JsonRequestBody(InputStream body) {
      super(body, MediaType.APPLICATION_JSON_VALUE);
    }
  }

  public static class StringRequestBody extends RequestBody {
    public StringRequestBody(InputStream body) {
      super(body, MediaType.TEXT_PLAIN_VALUE);
    }
  }

  public static class FormRequestBody extends RequestBody {
    public FormRequestBody(InputStream body) {
      super(body, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
    }

    public FormRequestBody(FormEntity formData) {
      super(
          IOUtil.toInputStream(ofFormData(formData)), MediaType.APPLICATION_FORM_URLENCODED_VALUE);
    }

    public static String ofFormData(FormEntity formEntity) {
      var builder = new StringBuilder();
      for (FormField entry : formEntity.data()) {
        if (builder.length() > 0) {
          builder.append("&");
        }
        builder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
        builder.append("=");
        builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
      }
      return builder.toString();
    }
  }
}
