package com.angiii.learnplatform.util;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public class AuthUtil {

    public static Set<String> getRoles() {
        return AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
    }

    public static UserDetails getUserDetails() {
        return (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getAuthPhone() {
        return getUserDetails().getUsername();
    }
}
