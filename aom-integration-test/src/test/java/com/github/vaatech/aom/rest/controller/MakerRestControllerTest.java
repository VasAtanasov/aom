package com.github.vaatech.aom.rest.controller;

import com.github.vaatech.aom.BaseRestEndpointTestSupport;
import com.github.vaatech.aom.api.dto.MakerDTO;
import com.github.vaatech.aom.config.jackson.PageResource;
import com.github.vaatech.aom.test.rest.ApiEndpoint;
import com.github.vaatech.aom.test.rest.endpoint.MakerApiEndpointAbstractFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class MakerRestControllerTest extends BaseRestEndpointTestSupport {

  @Autowired MakerApiEndpointAbstractFactory endpointFactory;

  @Test
  void whenCreateMakerShouldSuccess() {
    MakerDTO makerDTO = MakerDTO.builder().name("VW").build();
    ApiEndpoint endpoint = endpointFactory.createMakerApiEndpointBuilder(makerDTO).build();

    ResponseEntity<MakerDTO> response = execute(endpoint, MakerDTO.class);
    assertThat(response.getStatusCode().value()).isEqualTo(201);
    MakerDTO maker = response.getBody();
    assertThat(maker).isNotNull();
    assertThat(maker.getId()).isNotNull();
    assertThat(maker.getName()).isEqualTo("VW");
  }

  @Test
  void testDB() {
    ApiEndpoint endpoint = endpointFactory.fetchMakersPageApiEndpointBuilder(0, 20).build();
    MakerDTO makerDTO = MakerDTO.builder().name("VW").build();
    ResponseEntity<MakerDTO> execute =
        execute(endpointFactory.createMakerApiEndpointBuilder(makerDTO).build(), MakerDTO.class);
    var response = execute(endpoint, new ParameterizedTypeReference<>() {});
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    //    Page<MakerDTO> page = response.getBody();
    int a = 5;
  }
}
