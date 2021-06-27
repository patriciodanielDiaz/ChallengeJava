package alkemi.disney.Disney.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserMain implements UserDetails{

        private String userName;
        private String password;
        private Collection<? extends GrantedAuthority> authorities;

        public UserMain(String userName, String password, Collection<? extends GrantedAuthority> authorities) {
            this.userName = userName;
            this.password = password;
            this.authorities = authorities;
        }

        public static UserMain build(User user){

            List<GrantedAuthority> authorities=new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getUserType().name()));
            /*List<GrantedAuthority> authorities =
                    user.getRol()
                            .stream()
                            .map(rol -> new SimpleGrantedAuthority(rol.getRolNombre().name()))
                            .collect(Collectors.toList());*/

            return new UserMain(user.getUsername(),user.getPassword(),authorities);
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
            return this.userName;
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
