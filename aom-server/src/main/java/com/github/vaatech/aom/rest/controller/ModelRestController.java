package com.github.vaatech.aom.rest.controller;

import com.github.vaatech.aom.api.dto.ModelDTO;
import com.github.vaatech.aom.api.rest.controller.ModelRestApi;
import com.github.vaatech.aom.bl.ModelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModelRestController implements ModelRestApi {
  private final ModelService modelService;

  public ModelRestController(ModelService modelService) {
    this.modelService = modelService;
  }

  @Override
  public ResponseEntity<Page<ModelDTO>> fetchMakerModels(String makerName, Pageable pageable) {
    return ResponseEntity.ok(modelService.fetchMakerModels(makerName, pageable));
  }
}
