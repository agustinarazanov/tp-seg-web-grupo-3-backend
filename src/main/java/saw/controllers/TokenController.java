package saw.controllers;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import saw.models.LoginRequest;

@RestController
public class TokenController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtEncoder encoder;

    @Value("${jwt.expiration}")
    private long expirationTime;

    @PostMapping("/users/login")
    public String token(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expirationTime))
                .subject(authentication.getName())
                .claim("roles", authentication.getAuthorities())
                .build();
        JwsHeader header = JwsHeader.with(() -> "HS256").build();
        return this.encoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

}