package com.springSecurity.springJWT.service.auth;

import com.springSecurity.springJWT.config.JwtService;
import com.springSecurity.springJWT.controllers.auth.AuthenticationRequest;
import com.springSecurity.springJWT.controllers.auth.AuthenticationResponse;
import com.springSecurity.springJWT.controllers.auth.RegisterRequest;
import com.springSecurity.springJWT.model.user.Role;
import com.springSecurity.springJWT.model.user.User;
import com.springSecurity.springJWT.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String token = jwtService.generateTokenUser(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.generateTokenUser(user);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
