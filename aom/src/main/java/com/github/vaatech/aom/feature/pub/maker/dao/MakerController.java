package com.github.vaatech.aom.feature.pub.maker.dao;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/maker/v1")
public class MakerController {

  private final MakerRepository makerRepository;

  public MakerController(MakerRepository makerRepository) {
    this.makerRepository = makerRepository;
  }

  @GetMapping("_count")
  public ResponseEntity<Long> getCount() {
    return ResponseEntity.ok(makerRepository.count());
  }
}
