package bg.autohouse.spider.domain.dto.cg;

import lombok.Data;

import java.util.List;

@Data
public class TransmissionWrapper
{
    @Data
    public static class InnerWrapper
    {
        private String label;
        private List<TransmissionDTO> values;
    }

    private InnerWrapper all;
    private InnerWrapper trimSpecific;
}
