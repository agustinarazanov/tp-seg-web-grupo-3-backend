package saw.services;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import saw.models.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
public class CustomUserDetails implements UserDetails {
    private final String id;
    private final String username;
    private final String role;
    private final String password;
    private final Set<GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.id = user.getId().toString();
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.authorities = new HashSet<>(){{
            add(new SimpleGrantedAuthority(user.getRole()));
        }};
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public String toString() {
        return this.getClass().getName() + " [" +
                "Username=" + this.username + ", " +
                "Password=[PROTECTED], " +
                "Granted Authorities=" + this.authorities + "]";
    }
}
