package org.example.erudio.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.example.erudio.data.dto.v1.security.TokenDto;
import org.example.erudio.exceptions.InvalidJwtAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Date;
import java.util.List;


@Service
public class JwtTokenProvider  {

   @Value("${security.jwt.token.secret-key:secret}")
   private String secretKey = "secret";

   @Value("${security.jwt.token.expire-length:3600000}")
   private long validInMilliseconds = 3600000;

   @Autowired
   private UserDetailsService userDetailsService;

   private Algorithm algorithm = null;

   @PostConstruct
   protected void init() {
      secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
      this.algorithm = Algorithm.HMAC256(secretKey.getBytes());
   }

   public TokenDto createAccessToken(String username, List<String> roles) {
      Date now = new Date();
      Date expiration = new Date(now.getTime() + validInMilliseconds);
      var accessToken = getAccessToken(username, roles, now, expiration);
      var refreshToken = getRefreshToken(username, roles, now);
      return new TokenDto(username,true, now, expiration, accessToken, refreshToken);
   }

   public TokenDto refreshToken(String refreshToken) {
      if (refreshToken.contains("Bearer "))
         refreshToken = refreshToken.substring("Bearer ".length());
      DecodedJWT decoded = decodedToken(refreshToken);
      String username = decoded.getSubject();
      List<String> roles = decoded.getClaim("roles").asList(String.class);
      return createAccessToken(username, roles);
   }

   private String getAccessToken(String username, List<String> roles, Date now, Date expiration) {
      String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
      return JWT.create()
              .withClaim("roles", roles)
              .withIssuedAt(now)
              .withExpiresAt(expiration)
              .withSubject(username)
              .withIssuer(issuerUrl)
              .sign(algorithm)
              .strip();
   }

   private String getRefreshToken(String username, List<String> roles, Date now) {
      Date validityRefreshToken = new Date(now.getTime() + (validInMilliseconds * 3));
      return JWT.create()
              .withClaim("roles", roles)
              .withIssuedAt(now)
              .withExpiresAt(validityRefreshToken)
              .withSubject(username)
              .sign(algorithm)
              .strip();
   }

   public Authentication getAuthentication(String token) {
      DecodedJWT decoded = decodedToken(token);
      UserDetails userDetails = this.userDetailsService
              .loadUserByUsername(decoded.getSubject());
      return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
   }

   public DecodedJWT decodedToken(String token) {
      Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());
      JWTVerifier verifier = JWT.require(alg).build();
      return verifier.verify(token);
   }

   public String resolveToken(HttpServletRequest request) {
      String bearerToken = request.getHeader("Authorization");

      if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
         return bearerToken.substring(7);
      }

      return null;
   }

   public boolean validateToken(String token) {
      DecodedJWT decoded = decodedToken(token);
      try {
         if (decoded.getExpiresAt().before(new Date())) {
            return false;
         }

         return true;
      } catch (Exception e) {
         throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
      }
   }
}
