package saw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import saw.models.Login;
import saw.repositories.LoginRepository;
import saw.services.AuthService;

public class LoginController {
    
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    private final LoginRepository loginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    private final AuthService authService;

    public LoginController(LoginRepository repository,
        AuthService authService) {
        this.loginRepository = repository;
        this.authService = authService;
    }

    @PostMapping("/login")
    public String loginAttempt(@RequestBody Login login) {
        return authService.login(login.getEmail(), login.getPassword());
    }

    /* 
    @PostMapping("/login/register")
    public Login register(@RequestBody Login login) {
        login.setPassword(passwordEncoder.encode(login.getPassword()));
        return loginRepository.save(login);
    }
    */
}
