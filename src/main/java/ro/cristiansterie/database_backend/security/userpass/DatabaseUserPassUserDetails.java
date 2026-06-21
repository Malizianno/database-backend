package ro.cristiansterie.database_backend.security.userpass;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ro.cristiansterie.database_backend.model.UserEntity;

import java.util.ArrayList;
import java.util.Collection;

public class DatabaseUserPassUserDetails implements UserDetails {
    private final UserEntity user;

    public DatabaseUserPassUserDetails(UserEntity user) {
        this.user = user;
    }

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
