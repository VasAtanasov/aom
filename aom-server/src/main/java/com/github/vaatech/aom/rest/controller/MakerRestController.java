package com.github.vaatech.aom.rest.controller;

import com.github.vaatech.aom.api.dto.MakerDTO;
import com.github.vaatech.aom.api.rest.controller.MakerRestApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MakerRestController implements MakerRestApi {

  @Override
  public ResponseEntity<List<MakerDTO>> fetchMakers() {
    return null;
  }

  @Override
  public ResponseEntity<MakerDTO> findMakerById(Long id) {
    return null;
  }

  @Override
  public ResponseEntity<MakerDTO> createMaker(MakerDTO maker) {
    return null;
  }

  @Override
  public ResponseEntity<MakerDTO> updateMaker(Long id, MakerDTO maker) {
    return null;
  }

  @Override
  public ResponseEntity<Void> deleteMaker(Long id) {
    return null;
  }
}
