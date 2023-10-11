package com.github.vaatech.aom.api.dto;

import com.github.vaatech.aom.commons.basic.dto.BaseDTO;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ModelDTO implements BaseDTO<Integer> {
    private Integer id;
    private String name;
    private Integer makerId;
    private String makerName;
    private MakerDTO maker;
}
