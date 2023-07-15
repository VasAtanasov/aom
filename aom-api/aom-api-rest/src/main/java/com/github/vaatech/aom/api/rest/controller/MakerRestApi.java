package com.github.vaatech.aom.api.rest.controller;

import com.github.vaatech.aom.api.dto.MakerDTO;
import com.github.vaatech.aom.api.rest.constants.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = {Constants.API_V1_0 + MakerRestApi.MAKER_URL})
public interface MakerRestApi {

  String MAKER_URL = "/makers";

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Page<MakerDTO>> fetchMakers(
      @PageableDefault(size = 20)
          @SortDefault.SortDefaults({
            @SortDefault(sort = "name", direction = Sort.Direction.ASC),
            @SortDefault(sort = "id", direction = Sort.Direction.ASC)
          })
          Pageable pageable);

  @GetMapping(value = "/{id:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<MakerDTO> findMakerById(@PathVariable Integer id);

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<MakerDTO> createMaker(@RequestBody MakerDTO maker);

  @PutMapping(
      value = "/{id:\\d+}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<MakerDTO> updateMaker(@PathVariable Integer id, @RequestBody MakerDTO maker);

  @DeleteMapping("/{id:\\d+}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  ResponseEntity<Void> deleteMaker(@PathVariable Integer id);
}
