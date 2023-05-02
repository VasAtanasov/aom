package com.github.vaatech.aom.rest.controller;

import com.github.vaatech.aom.BaseRestEndpointTestSupport;
import com.github.vaatech.aom.api.dto.MakerDTO;
import com.github.vaatech.aom.test.rest.ApiEndpoint;
import com.github.vaatech.aom.test.rest.PageResponse;
import com.github.vaatech.aom.test.rest.endpoint.MakerApiEndpointAbstractFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

public class MakerRestControllerTest extends BaseRestEndpointTestSupport {

  @Autowired MakerApiEndpointAbstractFactory endpointFactory;

  @Test
  void whenCreateMakerShouldSuccess() {
    MakerDTO makerDTO = MakerDTO.builder().name("Toyota").build();
    ApiEndpoint endpoint = endpointFactory.createMakerApiEndpointBuilder(makerDTO).build();

    ResponseEntity<MakerDTO> response = execute(endpoint, MakerDTO.class);
    assertThat(response.getStatusCode().value()).isEqualTo(201);
    MakerDTO maker = response.getBody();
    assertThat(maker).isNotNull();
    assertThat(maker.getId()).isNotNull();
    assertThat(maker.getName()).isEqualTo("Toyota");
  }

  @Test
  @Sql(statements = "INSERT INTO maker VALUES (1, 'BMW'), (2, 'VW'), (3, 'AUDI');")
  void testDB() {
    ApiEndpoint endpoint = endpointFactory.fetchMakersPageApiEndpointBuilder(0, 20).build();
    ResponseEntity<PageResponse<MakerDTO>> response =
        executeForPage(endpoint, new ParameterizedTypeReference<>() {});
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    PageResponse<MakerDTO> page = response.getBody();
    assertThat(page).isNotNull();
    assertThat(page.getContent()).isNotNull();
    assertThat(page.getContent()).isNotEmpty();
  }
}
