package alkemi.disney.Disney.service;

import alkemi.disney.Disney.exception.ValidationException;
import alkemi.disney.Disney.model.User;
import alkemi.disney.Disney.model.UserLogin;
import alkemi.disney.Disney.model.UserMain;
import alkemi.disney.Disney.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public User save(User user) throws ValidationException {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        User u= userRepository.save(user);
        return Optional.ofNullable(u).orElseThrow(()-> new ValidationException("there was an error register user..."));
    }

    //public User login(UserLogin userLogin) {return null;}

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User u=userRepository.findByUsername(s);
        Optional.ofNullable(u).orElseThrow(()->new UsernameNotFoundException("username not located"));

        //List<GrantedAuthority> authorities=new ArrayList<>();
        //authorities.add(new SimpleGrantedAuthority(u.getUserType().name()));

        return UserMain.build(u);
    }


}
