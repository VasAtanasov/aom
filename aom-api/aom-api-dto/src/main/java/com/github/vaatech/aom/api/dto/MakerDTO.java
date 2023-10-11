package com.github.vaatech.aom.api.dto;

import com.github.vaatech.aom.commons.basic.dto.BaseDTO;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MakerDTO implements BaseDTO<Integer> {
    private Integer id;
    private String name;
}
