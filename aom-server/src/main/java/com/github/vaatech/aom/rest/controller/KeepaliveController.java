package com.github.vaatech.aom.rest.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeepaliveController {
    @RequestMapping(value = "/api/ping", produces = MediaType.TEXT_PLAIN_VALUE)
    public String ping(HttpSession session) {
        session.setAttribute("ts", System.currentTimeMillis());
        return "ok";
    }
}
