package ro.cristiansterie.databasebackend.dto;

import java.util.Set;

public record UserDTO(Long id, String username, String email, Set<RoleDTO> roles) {
}
