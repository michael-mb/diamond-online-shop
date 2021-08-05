package com.shop.diamond.user.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class User implements UserDetails, Serializable {
    @Id
    @Column
    @GeneratedValue
    public Long id;

    @Column
    public String firstName;

    @Column
    public String lastName;

    @Column
    public String email;

    @Column
    public String hashedPassword;

    @Column
    private String validationToken;

    @ElementCollection
    public Set<UserRole> grantedAuthorities = new HashSet<>();


    @SuppressWarnings("unused")
    public User(){};

    public User(String firstName , String lastName , String email , String password , Set<UserRole> grantedAuthorities){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hashedPassword = password;
        this.grantedAuthorities = grantedAuthorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.grantedAuthorities == null) {
            return Collections.emptySet();
        } else {
            return this.grantedAuthorities.stream()
                    .map(ga -> new SimpleGrantedAuthority(ga.toString()))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getValidationToken() {
        return validationToken;
    }

    public void setValidationToken(String validationToken) {
        this.validationToken = validationToken;
    }

    public Set<UserRole> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    public void setGrantedAuthorities(Set<UserRole> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    public boolean hasAuthority(UserRole role){
        return grantedAuthorities.contains(role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", validationToken='" + validationToken + '\'' +
                ", grantedAuthorities=" + grantedAuthorities +
                '}';
    }
}
