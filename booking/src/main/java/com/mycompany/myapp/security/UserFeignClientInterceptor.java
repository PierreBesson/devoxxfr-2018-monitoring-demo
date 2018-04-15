package com.mycompany.myapp.security;

import com.mycompany.myapp.security.jwt.TokenProvider;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserFeignClientInterceptor implements RequestInterceptor {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_TYPE = "Bearer";

    private final TokenProvider tokenProvider;

    @Autowired
    public UserFeignClientInterceptor(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void apply(RequestTemplate template) {
        Optional<String> currentUserJWT = SecurityUtils.getCurrentUserJWT();
        currentUserJWT.ifPresent(s -> template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, s)));
    }
}

