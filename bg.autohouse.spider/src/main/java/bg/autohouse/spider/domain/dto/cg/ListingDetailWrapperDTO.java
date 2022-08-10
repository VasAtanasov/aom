package bg.autohouse.spider.domain.dto.cg;

import lombok.Data;

@Data
public class ListingDetailWrapperDTO {

  @Data
  private static class AutoEntityInfo {
    private String entityId;
    private String entityType;
    private String make;
    private String model;
    private String year;
    private String trim;
    private String bodyStyle;
    private String[] autoCategories;
  }

  private ListingDetailDTO listing;
  private AutoEntityInfo autoEntityInfo;
}
