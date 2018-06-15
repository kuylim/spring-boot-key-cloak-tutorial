package com.wordpress.kuylim.springbootkeycloak.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

  @GetMapping("")
  public String user(){
    return "This is user page!!!";
  }

}
