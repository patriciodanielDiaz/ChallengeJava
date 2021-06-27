package alkemi.disney.Disney.controllerWeb;

import alkemi.disney.Disney.controller.AuthController;
import alkemi.disney.Disney.dto.JwtDto;
import alkemi.disney.Disney.email.SendGridEmailService;
import alkemi.disney.Disney.exception.ValidationException;
import alkemi.disney.Disney.model.User;
import alkemi.disney.Disney.model.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("auth/")
public class AuthControllerWeb {

    private AuthController authController;
    private SendGridEmailService sendGridEmailService;

    @Autowired
    public AuthControllerWeb(AuthController authController,SendGridEmailService sendGridEmailService) {
        this.authController = authController;
        this.sendGridEmailService = sendGridEmailService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> userRegister(@Valid @RequestBody User user) throws ValidationException {

        User u= authController.save(user);
        this.sendGridEmailService.sendHTML("patriciodanielcaro@gmail.com",u.getMail(), "Thank you for registering "+u.getFirstname(), "Hello, <strong>how are you doing? It has been registered via web</strong>");
        return ResponseEntity.ok(u);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> userLogin(@Valid @RequestBody UserLogin userLogin){
        JwtDto jwtDto=authController.login(userLogin);
        return new ResponseEntity<>(jwtDto, HttpStatus.OK);
    }
}
