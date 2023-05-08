package com.github.vaatech.aom.rest.controller;

import com.github.vaatech.aom.BaseRestEndpointTestSupport;
import com.github.vaatech.aom.api.dto.MakerDTO;
import com.github.vaatech.aom.rest.controller.error.RestError;
import com.github.vaatech.aom.test.rest.ApiEndpoint;
import com.github.vaatech.aom.test.rest.PageResponse;
import com.github.vaatech.aom.test.rest.endpoint.MakerApiEndpointAbstractFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.ErrorResponse;

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
  void whenFetchPageShouldReturn() {
    ApiEndpoint endpoint = endpointFactory.fetchMakersPageApiEndpointBuilder(0, 20).build();
    ResponseEntity<PageResponse<MakerDTO>> response =
        executeForPage(endpoint, new ParameterizedTypeReference<>() {});
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    PageResponse<MakerDTO> page = response.getBody();
    assertThat(page).isNotNull();
    assertThat(page.getContent()).isNotNull();
    assertThat(page.getContent()).isNotEmpty();
  }

  @Test
  void whenUpdateMakerShouldSuccess() {
    MakerDTO makerDTO = MakerDTO.builder().name("Audi").build();
    ApiEndpoint endpoint = endpointFactory.createMakerApiEndpointBuilder(makerDTO).build();

    ResponseEntity<MakerDTO> response = execute(endpoint, MakerDTO.class);
    assertThat(response.getStatusCode().value()).isEqualTo(201);
    MakerDTO maker = response.getBody();
    assertThat(maker).isNotNull();
    assertThat(maker.getId()).isNotNull();
    assertThat(maker.getName()).isEqualTo("Audi");

    makerDTO = MakerDTO.builder().id(maker.getId()).name("Audi2").build();
    endpoint = endpointFactory.updateMakerApiEndpointBuilder(makerDTO).build();
    response = execute(endpoint, MakerDTO.class);
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    maker = response.getBody();
    assertThat(maker).isNotNull();
    assertThat(maker.getId()).isNotNull();
    assertThat(maker.getName()).isEqualTo("Audi2");
  }

  /*@Test
  void whenUpdateMakerNotFoundShouldThrow() {
    MakerDTO makerDTO = MakerDTO.builder().id(55).name("Audi").build();
    ApiEndpoint endpoint = endpointFactory.updateMakerApiEndpointBuilder(makerDTO).build();
    ResponseEntity<RestError> response = executeForError(endpoint);
    assertThat(response.getStatusCode().value()).isEqualTo(404);
  }*/

  @Test
  void whenFetchPageShouldReturnEmpty() {
    ApiEndpoint endpoint = endpointFactory.fetchMakersPageApiEndpointBuilder(0, 20).build();
    ResponseEntity<PageResponse<MakerDTO>> response =
        executeForPage(endpoint, new ParameterizedTypeReference<>() {});
    assertThat(response.getStatusCode().value()).isEqualTo(200);
    PageResponse<MakerDTO> page = response.getBody();
    assertThat(page).isNotNull();
    assertThat(page.getContent()).isNotNull();
    assertThat(page.getContent()).isEmpty();
  }
}
