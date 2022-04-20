package com.bosonit.virtualtravel.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class LoginController {

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(
            @RequestParam("user") String username,
            @RequestParam(required = false, name = "password") String pwd) {

        // Rescatamos persona & password
//        PersonaOutputDTO personaOutputDTO = service.getPersonaByUser(username);
//        String password = personaOutputDTO.getPassword();

        // Comprobamos credenciales y asignamos rol
//        if (!pwd.equals(password))
//            return ResponseEntity.unprocessableEntity().body("Pass incorrecto");
//        String rol = personaOutputDTO.isAdmin() ? "ADMIN" : "USER";

        return new ResponseEntity<>(getJWTToken(username, "ADMIN"), HttpStatus.OK);
    }

    /**
     * Token JWT
     *
     * @param username
     * @param rol
     * @return
     */
    private String getJWTToken(String username, String rol) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(rol);

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}