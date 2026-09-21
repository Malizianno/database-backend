package ro.cristiansterie.databasebackend.security.userdetails;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ro.cristiansterie.databasebackend.model.UserEntity;

import java.util.ArrayList;
import java.util.Collection;

public record DatabaseUserDetails(
		UserEntity user) implements UserDetails {

	@Override
	public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
		var authorities = user.getGrantedAuthorities();
		return authorities != null ? authorities : new ArrayList<>();
	}

	@Override
	public @Nullable String getPassword() {
		return user.getPassword();
	}

	@Override
	public @NonNull String getUsername() {
		return user.getUsername();
	}
}
