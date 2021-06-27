package alkemi.disney.Disney.controller;

import alkemi.disney.Disney.dto.JwtDto;
import alkemi.disney.Disney.email.SendGridEmailService;
import alkemi.disney.Disney.exception.ValidationException;
import alkemi.disney.Disney.jwt.JwtProvider;
import alkemi.disney.Disney.model.User;
import alkemi.disney.Disney.model.UserLogin;
import alkemi.disney.Disney.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

@Controller
public class AuthController {

    private UserService userService;
    private JwtProvider jwtProvider;
    private AuthenticationManager authenticationManager;


    @Autowired
    public AuthController(UserService userService,JwtProvider jwtProvider,AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.authenticationManager = authenticationManager;
    }

    public User save(User user) throws ValidationException {
        User u=userService.save(user);
        return u;
    }

    public JwtDto login(UserLogin userLogin){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getUsername(),userLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return jwtDto;
    }
}
