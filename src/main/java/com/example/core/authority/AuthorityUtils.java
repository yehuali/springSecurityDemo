package com.example.core.authority;

import com.example.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public abstract class AuthorityUtils {

    public static List<GrantedAuthority> createAuthorityList(String... roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(roles.length);

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }
}
