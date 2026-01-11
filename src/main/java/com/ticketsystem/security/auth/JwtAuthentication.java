package com.ticketsystem.security.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthentication implements Authentication {

    private final UserPrincipal principal;
    private boolean authenticated = true;

    public JwtAuthentication(UserPrincipal principal) {
        this.principal = principal;
    }

    @Override
    public UserPrincipal getPrincipal() {
        return principal;
    }

    @Override public Object getCredentials() { return null; }
    @Override public Object getDetails() { return null; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return null; }
    @Override public String getName() { return principal.getUserId().toString(); }
    @Override public boolean isAuthenticated() { return authenticated; }
    @Override public void setAuthenticated(boolean isAuthenticated) {
        this.authenticated = isAuthenticated;
    }
}