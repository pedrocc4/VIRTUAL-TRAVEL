package com.bosonit.virtualtravel.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/v0")
public class LoginController {

    @PostMapping("token")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(
            @RequestHeader String username,
            @RequestHeader String password) {

        // Rescatamos persona & password
//        PersonaOutputDTO personaOutputDTO = service.getPersonaByUser(username);
//        String password = personaOutputDTO.getPassword();

        // Comprobamos credenciales y asignamos rol
//        if (!pwd.equals(password))
//            return ResponseEntity.unprocessableEntity().body("Pass incorrecto");
//        String rol = personaOutputDTO.isAdmin() ? "ADMIN" : "USER";

        return new ResponseEntity<>(getJWTToken(username, "ADMIN"), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("token")
    public ResponseEntity<Void> checkToken(@PathVariable String token) {
        if (!token.equals(getJWTToken("admin", "ADMIN"))) {
            log.info("Autorización fallida");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        log.info("Autorización completada de forma satisfactoria");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

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