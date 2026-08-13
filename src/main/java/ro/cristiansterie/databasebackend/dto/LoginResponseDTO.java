package ro.cristiansterie.databasebackend.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record LoginResponseDTO(String username, Collection<? extends GrantedAuthority> authorities, String token) {
}
