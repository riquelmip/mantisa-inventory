package com.mantisa.inventory.security;

import com.mantisa.inventory.util.ResponseObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.OutputStream;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        // Construir el objeto de respuesta personalizado
        ResponseObject responseObject = new ResponseObject(
                false,
                HttpStatus.FORBIDDEN,
                "Acceso denegado: No tienes permisos para acceder a este recurso",
                accessDeniedException.getMessage());

        // Convertir a JSON y enviar la respuesta
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseObject);

        OutputStream out = response.getOutputStream();
        out.write(jsonResponse.getBytes("UTF-8"));
        out.flush();
    }
}
