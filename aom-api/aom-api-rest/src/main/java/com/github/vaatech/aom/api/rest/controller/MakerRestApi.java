package com.github.vaatech.aom.api.rest.controller;

import com.github.vaatech.aom.api.dto.MakerDTO;
import com.github.vaatech.aom.api.rest.constants.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = {Constants.API_V1_0 + MakerRestApi.MAKER_URL})
public interface MakerRestApi {

  String MAKER_URL = "/makers";

  @GetMapping
  ResponseEntity<List<MakerDTO>> fetchMakers();

  @GetMapping("/{id}")
  ResponseEntity<MakerDTO> findMakerById(@PathVariable Long id);

  @PostMapping
  ResponseEntity<MakerDTO> createMaker(@RequestBody MakerDTO maker);

  @PutMapping("/{id}")
  ResponseEntity<MakerDTO> updateMaker(@PathVariable Long id, @RequestBody MakerDTO maker);

  @DeleteMapping("/{id}")
  ResponseEntity<Void> deleteMaker(@PathVariable Long id);
}
