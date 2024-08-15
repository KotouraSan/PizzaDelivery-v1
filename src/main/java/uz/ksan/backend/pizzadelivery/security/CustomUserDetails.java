package uz.ksan.backend.pizzadelivery.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.ksan.backend.pizzadelivery.models.entities.ClientEntity;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public record CustomUserDetails(ClientEntity clientEntity) implements UserDetails {
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(clientEntity.getRole().split(", "))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return clientEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return clientEntity.getUsername();
    }



}
