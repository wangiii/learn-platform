package com.angiii.learnplatform.controller;

import com.angiii.learnplatform.dto.AuthenticationRequest;
import com.angiii.learnplatform.dto.AuthenticationResponse;
import com.angiii.learnplatform.dto.RespBean;
import com.angiii.learnplatform.exception.BadLoginException;
import com.angiii.learnplatform.service.MyUserDetailsService;
import com.angiii.learnplatform.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@CrossOrigin
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping(value = "login")
    public RespBean login(@RequestBody AuthenticationRequest authenticationRequest) throws BadLoginException {
        log.info("用户名：{}, 密码{}", authenticationRequest.getUsername(), authenticationRequest.getPassword());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new BadLoginException("用户名或者密码错误", e.getMessage());
        }


        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return RespBean.ok("登录成功", new AuthenticationResponse(authenticationRequest.getUsername(), jwt));
    }
}
