package ru.meshkov.workout.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.meshkov.workout.models.Athlete;

import java.util.Collection;
import java.util.Collections;

public class AthleteDetails implements UserDetails {

    private final Athlete athlete;
    private final static String ROLE_ADMIN = "ROLE_ADMIN";
    private final static String ROLE_USER = "ROLE_USER";

    public AthleteDetails(Athlete athlete) {
        this.athlete = athlete;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(athlete.getRole()));
    }

    public int getId() {
        return athlete.getId();
    }
    public boolean isAdmin() {
        return athlete.getRole().equals(ROLE_ADMIN);
    }

    public boolean isCurrentUserById(int id) {
        return id == athlete.getId();
    }

    @Override
    public String getPassword() {
        return athlete.getPassword();
    }

    @Override
    public String getUsername() {
        return athlete.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
