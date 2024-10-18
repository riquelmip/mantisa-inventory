package com.mantisa.inventory.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ResponseObject {
    /* MESSAGES */
    public static final String MSG_GENERAL = "Error general";
    public static final String MSG_SUCCESS = "Solicitud realizada";
    public static final String MSG_ERROR = "Se produjo un error en la solicitud";
    public static final String MSG_UNAUTHORIZED = "Sin autorización";
    public static final String MSG_FORBIDDEN = "Sin autorización";
    public static final String MSG_NO_PARAMS = "No se recibieron los parámetros necesarios";
    public static final String MSG_LOGIC_ERROR = "Error lógico inesperado";
    public static final String MSG_BAD_REQUEST = "Solicitud incorrecta";
    public static final String MSG_INACTIVE_USER = "Usuario inactivo";
    public static final String MSG_SESSION_EXP = "La sesión ha expirado";
    public static final String MSG_METHOD_NOT_ALLOWED = "Método no soportado";
    public static final String MSG_REGISTER = "Registrado correctamente";
    public static final String MSG_UPDATE = "Editado correctamente";
    public static final String MSG_DELETE = "Eliminado correctamente";
    public static final String MSG_EXISTS = "Ese registro ya existe";
    public static final String MSG_NOT_FOUND = "No encontrado";

    private Boolean isSuccess;
    private HttpStatus status;
    private String message;
    private Object data;

    public static ResponseEntity<ResponseObject> build(Boolean isSuccess, HttpStatus status, String message, Object data) {
        ResponseObject responseObject = new ResponseObject(isSuccess, status, message, data);
        return ResponseEntity.status(status).body(responseObject);
    }

    public ResponseEntity<ResponseObject> toResponseEntity() {
        HttpStatus code = (status != null) ? status : HttpStatus.OK;
        return ResponseEntity.status(code).body(this);
    }
}
