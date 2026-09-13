package br.com.dashboard.config;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private final HttpStatus status;
    private final String error;

    public ApiException(HttpStatus status, String error, String message) {
        super(message);
        this.status = status;
        this.error = error;
    }

    public static ApiException notFound(String message) {
        return new ApiException(HttpStatus.NOT_FOUND, "Não Encontrado", message);
    }

    public static ApiException forbiden(String message) {
        return new ApiException(HttpStatus.FORBIDDEN, "Acesso negado", message);
    }

}

