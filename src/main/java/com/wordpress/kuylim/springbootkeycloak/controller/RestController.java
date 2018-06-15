package com.wordpress.kuylim.springbootkeycloak.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
@org.springframework.web.bind.annotation.RestController
public class RestController {

  @GetMapping("")
  public String rest(){
    return "This is rest api.";
  }
}
