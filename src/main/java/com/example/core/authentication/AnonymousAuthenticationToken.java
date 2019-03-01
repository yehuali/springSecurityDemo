package com.example.core.authentication;

import com.example.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;

public class AnonymousAuthenticationToken extends AbstractAuthenticationToken implements
        Serializable {

    private static final long serialVersionUID = 1L;
    private final Object principal;
    private final int keyHash;

    public AnonymousAuthenticationToken(String key, Object principal,
                                        Collection<? extends GrantedAuthority> authorities) {
        this(extractKeyHash(key), principal, authorities);
    }

    private AnonymousAuthenticationToken(Integer keyHash, Object principal,
                                         Collection<? extends GrantedAuthority> authorities) {
//        super(authorities);

        if (principal == null || "".equals(principal)) {
            throw new IllegalArgumentException("principal cannot be null or empty");
        }
        Assert.notEmpty(authorities, "authorities cannot be null or empty");

        this.keyHash = keyHash;
        this.principal = principal;
        setAuthenticated(true);
    }



    private static Integer extractKeyHash(String key) {
        Assert.hasLength(key, "key cannot be empty or null");
        return key.hashCode();
    }

}
