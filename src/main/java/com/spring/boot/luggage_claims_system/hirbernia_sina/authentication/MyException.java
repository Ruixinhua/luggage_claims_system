package com.spring.boot.luggage_claims_system.hirbernia_sina.authentication;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Liu Dairui
 * @date 2019-05-12 23:46
 */

public class MyException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    public MyException(String msg) {
        super(msg);
    }

}
