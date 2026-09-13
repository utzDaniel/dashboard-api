package br.com.dashboard.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Testes unitários de ApiException")
class ApiExceptionTest {

    @Test
    @DisplayName("Deve criar ApiException notFound com método estático")
    void deveCriarNotFoundComMetodoEstatico() {
        // Arrange
        String message = "Recurso não encontrado";

        // Act
        ApiException exception = ApiException.notFound(message);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Não Encontrado", exception.getError());
        assertEquals(message, exception.getMessage());
    }


    @Test
    @DisplayName("Deve criar ApiException forbidden com método estático")
    void deveCriarForbiddenComMetodoEstatico() {
        // Arrange
        String message = "Acesso não permitido";

        // Act
        ApiException exception = ApiException.forbiden(message);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
        assertEquals("Acesso negado", exception.getError());
        assertEquals(message, exception.getMessage());
    }
}

