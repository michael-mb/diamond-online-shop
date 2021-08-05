package com.shop.diamond.user.entities;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum TestUser {
    ADMIN("admin" , "admin" , "admin@admin.com" , "test" ,
            new HashSet<UserRole>(Arrays.asList(UserRole.USER , UserRole.ADMIN , UserRole.MODERATOR))),
    MODERATOR("moderator" , "moderator" , "moderator@test.com" ,
            "test" , new HashSet<UserRole>(Arrays.asList(UserRole.USER , UserRole.MODERATOR))),
    USER("John" , "Doe" , "johnDoe@test.com" , "test" ,
            new HashSet<UserRole>(Arrays.asList(UserRole.USER)));

    public final String firstName;
    public final String lastName;
    public final String email;
    public final String password;
    public final Set<UserRole> grantedAuthorities;

    TestUser(String firstName , String lastName , String email , String password , Set<UserRole> grantedAuthorities){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;
    }
}
