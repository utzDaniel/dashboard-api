package br.com.dashboard.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("GlobalExceptionHandler - Testes")
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new GlobalExceptionHandler();
        when(request.getRequestURI()).thenReturn("/api/v1/test");
    }

    @Test
    @DisplayName("handleResponseStatus - deve retornar resposta padronizada para ResponseStatusException")
    void handleResponseStatusShouldReturnStandardizedResponse() {
        ResponseStatusException ex = new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso não encontrado");

        ResponseEntity<Map<String, Object>> response = handler.handleResponseStatus(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().get("status"));
        assertEquals("Recurso não encontrado", response.getBody().get("message"));
        assertEquals("/api/v1/test", response.getBody().get("path"));
        assertNotNull(response.getBody().get("timestamp"));
    }


    @Test
    @DisplayName("handleApiException - deve retornar resposta com mensagem")
    void handleApiExceptionSemViolacoes() {
        // Arrange
        ApiException ex = ApiException.notFound("Usuário não encontrado");

        // Act
        ResponseEntity<Map<String, Object>> response = handler.handleApiException(ex, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().get("status"));
        assertEquals("Não Encontrado", response.getBody().get("error"));
        assertEquals("Usuário não encontrado", response.getBody().get("message"));
        assertEquals("/api/v1/test", response.getBody().get("path"));
        assertNotNull(response.getBody().get("timestamp"));
    }

    @Test
    @DisplayName("handleMediaTypeNotSupported - deve retornar resposta de tipo de mídia não suportado")
    void handleMediaTypeNotSupported() {
        // Act
        ResponseEntity<Map<String, Object>> response = handler.handleMediaTypeNotSupported(request);

        // Assert
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(415, response.getBody().get("status"));
        assertEquals("Tipo de mídia não suportado", response.getBody().get("error"));
        assertEquals("Deve usar application/json", response.getBody().get("message"));
        assertEquals("/api/v1/test", response.getBody().get("path"));
        assertNotNull(response.getBody().get("timestamp"));
    }

    @Test
    @DisplayName("handleNoResourceFound - deve retornar resposta de recurso não encontrado")
    void handleNoResourceFound() {
        // Arrange
        NoResourceFoundException ex = new NoResourceFoundException(HttpMethod.GET, "/api/v1/inexistente", "");

        // Act
        ResponseEntity<Map<String, Object>> response = handler.handleNoResourceFound(ex, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().get("status"));
        assertEquals("Não encontrado", response.getBody().get("error"));
        assertEquals("O endpoint solicitado não existe", response.getBody().get("message"));
        assertEquals("/api/v1/test", response.getBody().get("path"));
        assertNotNull(response.getBody().get("timestamp"));
    }

    @Test
    @DisplayName("handleNoHandlerFound - deve retornar resposta de handler não encontrado")
    void handleNoHandlerFound() {
        // Arrange
        NoHandlerFoundException ex = new NoHandlerFoundException("GET", "/api/v1/inexistente", null);

        // Act
        ResponseEntity<Map<String, Object>> response = handler.handleNoHandlerFound(ex, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().get("status"));
        assertEquals("Não encontrado", response.getBody().get("error"));
        assertEquals("O endpoint solicitado não existe", response.getBody().get("message"));
        assertEquals("/api/v1/test", response.getBody().get("path"));
        assertNotNull(response.getBody().get("timestamp"));
    }

    @Test
    @DisplayName("handleGenericException - deve retornar resposta de erro interno")
    void handleGenericException() {
        // Arrange
        Exception ex = new RuntimeException("Erro inesperado");

        // Act
        ResponseEntity<Map<String, Object>> response = handler.handleGenericException(ex, request);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().get("status"));
        assertEquals("Erro interno do servidor", response.getBody().get("error"));
        assertEquals("Ocorreu um erro inesperado. Por favor, tente novamente mais tarde", response.getBody().get("message"));
        assertEquals("/api/v1/test", response.getBody().get("path"));
        assertNotNull(response.getBody().get("timestamp"));
    }

    private record ConstraintPayload(
            @NotBlank(message = "Nome é obrigatório")
            String name
    ) {
    }

}
