package org.example.erudio.services;

import org.example.erudio.data.dto.v1.security.AccountCredentialsDto;
import org.example.erudio.data.dto.v1.security.TokenDto;
import org.example.erudio.repositories.UserRepository;
import org.example.erudio.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServices {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<TokenDto> signin(AccountCredentialsDto accountCredentialsDto) {
        try {
            var username = accountCredentialsDto.getUsername();
            var password = accountCredentialsDto.getPassword();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            var user = userRepository.findUserByUserName(username);

            var tokenResponse = new TokenDto();
            if (user != null) {
                tokenResponse = jwtTokenProvider.createAccessToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Username" +username+" not found");
            }

            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username/password");
        }
    }

    public ResponseEntity<TokenDto> refreshToken(String username, String refreshToken) {
            var user = userRepository.findUserByUserName(username);

            var tokenResponse = new TokenDto();
            if (user != null) {
                tokenResponse = jwtTokenProvider.refreshToken(refreshToken);
            } else {
                throw new UsernameNotFoundException("Username" +username+" not found");
            }

            return ResponseEntity.ok(tokenResponse);
    }
}
