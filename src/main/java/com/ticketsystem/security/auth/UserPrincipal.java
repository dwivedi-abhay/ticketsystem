package com.ticketsystem.security.auth;

public class UserPrincipal {

    private final Long userId;

    public UserPrincipal(Long userId){
        this.userId = userId;
    }

    public Long getUserId(){
        return userId;
    }

}
