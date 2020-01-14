package com.angiii.learnplatform.controller;

import com.angiii.learnplatform.dto.AuthenticationRequest;
import com.angiii.learnplatform.dto.RespBean;
import com.angiii.learnplatform.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "login")
    public RespBean login(@RequestBody AuthenticationRequest authenticationRequest) {
        return loginService.login(authenticationRequest);
    }
}
