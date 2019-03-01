package com.example.core;

import java.io.Serializable;

public interface GrantedAuthority extends Serializable {

    String getAuthority();

}
