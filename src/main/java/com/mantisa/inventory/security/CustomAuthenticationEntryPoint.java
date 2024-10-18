package com.mantisa.inventory.security;

import com.mantisa.inventory.util.ResponseObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.OutputStream;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /*
     * Metodo sobreescrito de AuthenticationEntryPoint que sirve para dar una respuesta personalizada, cuando Spring
     * Security lance una excepcion a alguna ruta
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Construir el ResponseObject
        ResponseObject responseObject = new ResponseObject(
                false,
                HttpStatus.UNAUTHORIZED,
                "Acceso no autorizado ó ruta no encontrada",
                authException.getMessage());

        // Convertir el ResponseObject a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseObject);

        // Crear ResponseEntity
        ResponseObject.build(false, HttpStatus.UNAUTHORIZED, "Acceso no autorizado ó ruta no encontrada", authException.getMessage());

        // Enviar la respuesta
        OutputStream out = response.getOutputStream();
        out.write(jsonResponse.getBytes("UTF-8")); // Especificar la codificación de caracteres
        out.flush();
    }
}
