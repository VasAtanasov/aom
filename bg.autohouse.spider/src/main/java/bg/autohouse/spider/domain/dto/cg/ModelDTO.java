package bg.autohouse.spider.domain.dto.cg;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModelDTO implements Serializable
{
    private String id;
    private String name;
    private boolean popular;
}
