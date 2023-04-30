package com.github.vaatech.aom.api.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MakerDTO implements Serializable
{
  private Integer id;
  private String name;
}
