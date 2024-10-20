package com.mantisa.inventory.controller.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.mantisa.inventory.model.UserEntity;
import com.mantisa.inventory.model.dto.AuthLoginRequestDTO;
import com.mantisa.inventory.model.dto.AuthResponseDTO;
import com.mantisa.inventory.security.UserDetailServiceImpl;
import com.mantisa.inventory.service.implementation.UserServiceImpl;
import com.mantisa.inventory.util.ResponseObject;
import com.mantisa.inventory.security.UserDetailServiceImpl;
import com.mantisa.inventory.util.ResponseObject;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<ResponseObject> login(@RequestBody @Valid AuthLoginRequestDTO authRequest) {
        try {
            AuthResponseDTO authResponseDTO = this.userDetailService.loginUser(authRequest);
            return ResponseObject.build(true, HttpStatus.OK, "User logged in successfully", authResponseDTO);
        } catch (BadCredentialsException e) { // Usuario o contraseña incorrectos
            logger.error("BadCredentialsException: {}", e.getMessage());
            return ResponseObject.build(false, HttpStatus.UNAUTHORIZED, "Invalid username or password", null);
        } catch (DisabledException e) { // La cuenta está deshabilitada
            logger.error("DisabledException: {}", e.getMessage());
            return ResponseObject.build(false, HttpStatus.FORBIDDEN, "User account is disabled", null);
        } catch (LockedException e) { // La cuenta está bloqueada
            logger.error("LockedException: {}", e.getMessage());
            return ResponseObject.build(false, HttpStatus.LOCKED, "User account is locked", null);
        } catch (AuthenticationException e) { // Error de autenticación
            logger.error("AuthenticationException: {}", e.getMessage());
            return ResponseObject.build(false, HttpStatus.UNAUTHORIZED, "Authentication failed", null);
        } catch (Exception e) { // Error general
            logger.error("Exception: {}", e.getMessage());
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Ocurrió un error", null);
        }

    }

    @PostMapping("/validate-token")
    public @ResponseBody ResponseEntity<ResponseObject> validateToken(@Param("token") String token) {
        try {
            String username = this.userDetailService.validateToken(token);

            if (username != null) {
                AuthResponseDTO authResponseDTO = new AuthResponseDTO(username, "Token is valid", token, true);
                return ResponseObject.build(true, HttpStatus.OK, "Token is valid", authResponseDTO);
            } else {
                return ResponseObject.build(false, HttpStatus.UNAUTHORIZED, "Token is invalid", null);
            }
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage());
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Ocurrió un error", null);
        }
    }


}
