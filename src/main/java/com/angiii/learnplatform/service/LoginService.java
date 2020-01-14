package com.angiii.learnplatform.service;

import com.angiii.learnplatform.dto.AuthenticationRequest;
import com.angiii.learnplatform.dto.AuthenticationResponse;
import com.angiii.learnplatform.dto.RespBean;
import com.angiii.learnplatform.exception.BadLoginException;
import com.angiii.learnplatform.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    public RespBean login(AuthenticationRequest authenticationRequest) throws BadLoginException {
        log.info("用户名：{}, 密码{}", authenticationRequest.getUsername(), authenticationRequest.getPassword());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadLoginException("用户名或者密码错误", e.getMessage());
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        Set<String> roles = AuthorityUtils.authorityListToSet(userDetails.getAuthorities());

        String role = "";

        if (roles.contains("ROLE_ADMIN")) {
            role = "ROLE_ADMIN";
        }

        if (roles.contains("ROLE_TEACHER")) {
            role = "ROLE_TEACHER";
        }

        return RespBean.ok("登录成功",
                new AuthenticationResponse(authenticationRequest.getUsername(), jwt, role)
        );
    }
}
