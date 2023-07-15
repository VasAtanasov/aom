package com.github.vaatech.aom.rest.controller;

import com.github.vaatech.aom.api.dto.MakerDTO;
import com.github.vaatech.aom.api.rest.controller.MakerRestApi;
import com.github.vaatech.aom.bl.MakerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MakerRestController implements MakerRestApi {

  private final MakerService makerService;

  public MakerRestController(MakerService makerService) {
    this.makerService = makerService;
  }

  @Override
  public ResponseEntity<Page<MakerDTO>> fetchMakers(Pageable pageable) {
    Page<MakerDTO> page = makerService.fetchMakers(pageable);
    return ResponseEntity.ok(page);
  }

  @Override
  public ResponseEntity<MakerDTO> findMakerById(Integer id) {
    MakerDTO makerDTO = makerService.read(id);
    return ResponseEntity.ok(makerDTO);
  }

  @Override
  public ResponseEntity<MakerDTO> createMaker(MakerDTO maker) {
    MakerDTO makerDTO = makerService.create(maker);
    return ResponseEntity.status(HttpStatus.CREATED).body(makerDTO);
  }

  @Override
  public ResponseEntity<MakerDTO> updateMaker(Integer id, MakerDTO maker) {
    maker.setId(id);
    MakerDTO makerDTO = makerService.update(maker);
    return ResponseEntity.ok(makerDTO);
  }

  @Override
  public ResponseEntity<Void> deleteMaker(Integer id) {
    makerService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
