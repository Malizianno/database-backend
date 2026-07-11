package ro.cristiansterie.databasebackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

public record UserDTO(
		Long id,
		String username,
		String password,
		String email,
		Set<RoleDTO> roles,
		@JsonIgnore List<GrantedAuthority> grantedAuthorities) {
}
