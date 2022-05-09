package com.bosonit.virtualtravel.security;

import com.bosonit.virtualtravel.security.usuario.IUsuarioRepositoryJPA;
import com.bosonit.virtualtravel.security.usuario.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private IUsuarioRepositoryJPA repositoryJPA;

    @PostMapping("token")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(
            @RequestHeader String username,
            @RequestHeader String password) {

        // creamos usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setRol("USUARIO");

        // asignamos token y persistimos
        usuario.setToken(getJWTToken(username, "USUARIO"));
        log.info("Usuario " + usuario.getUsername() + " registrado correctamente");
        repositoryJPA.save(usuario);

        return ResponseEntity.status(HttpStatus.OK).body(usuario.getToken());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("token")
    public ResponseEntity<Void> checkToken(String token) {
        if (!token.equals(repositoryJPA.checkToken(token).getToken())) {
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