package com.github.vaatech.aom.api.rest.controller;

import com.github.vaatech.aom.api.dto.ModelDTO;
import com.github.vaatech.aom.api.rest.constants.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = {Constants.API_V1_0 + ModelRestApi.MODEL_URL})
public interface ModelRestApi {
  String MODEL_URL = "/models";

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Page<ModelDTO>> fetchMakerModels(
      @RequestParam String makerName,
      @PageableDefault(size = 20)
          @SortDefault.SortDefaults({
            @SortDefault(sort = "name", direction = Sort.Direction.ASC),
            @SortDefault(sort = "id", direction = Sort.Direction.ASC)
          })
          Pageable pageable);
}
