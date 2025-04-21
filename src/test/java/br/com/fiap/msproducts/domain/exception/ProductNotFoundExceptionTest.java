package br.com.fiap.msproducts.domain.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        String message = "Product with ID 10 not found.";
        ProductNotFoundException exception = new ProductNotFoundException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void shouldBeInstanceOfRuntimeException() {
        ProductNotFoundException exception = new ProductNotFoundException("Not found");
        assertTrue(exception instanceof RuntimeException);
    }
}
