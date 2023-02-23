package com.github.vaatech.aom.web.controller.rest;

import com.github.vaatech.aom.core.RuntimeEnvironmentUtil;
import com.github.vaatech.aom.core.repository.MakerRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
public class KeepaliveController {

  private final MakerRepository makerRepository;
  private final RuntimeEnvironmentUtil runtimeEnvironmentUtil;

  public KeepaliveController(MakerRepository makerRepository, RuntimeEnvironmentUtil runtimeEnvironmentUtil) {
    this.makerRepository = makerRepository;
    this.runtimeEnvironmentUtil = runtimeEnvironmentUtil;
  }

  @RequestMapping(value = "/api/ping", produces = MediaType.TEXT_PLAIN_VALUE)
  public String ping(HttpSession session) {
    session.setAttribute("ts", System.currentTimeMillis());

    return "ok";
  }
}
