package ro.cristiansterie.databasebackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public record UserDTO(
		@JsonProperty(access = JsonProperty.Access.READ_ONLY)
		UUID id,
		String username,
		@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
		String password,
		String email,
		Set<RoleDTO> roles,
		@JsonIgnore List<GrantedAuthority> grantedAuthorities) {
}
