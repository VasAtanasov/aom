package bg.autohouse.spider.domain.dto.cg;

import lombok.Data;

import java.util.Map;

@Data
public class ListingPictureDTO {
  private String url;
  private String thumbnailUrl;
  private Integer height;
  private Integer width;
  private Map<String, ListingPictureDTO> scaledPictures;
}
