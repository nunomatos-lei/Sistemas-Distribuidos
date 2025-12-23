package pt.ulusofona.cd.projeto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Not found
    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProductNotFoundException(RestaurantNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Restaurant Not Found");
        error.put("message", ex.getMessage());
        error.put("timestamp", Instant.now());
        error.put("status", HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MenuItemNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleMenuItemNotFoundException(MenuItemNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Menu item Not Found");
        error.put("message", ex.getMessage());
        error.put("timestamp", Instant.now());
        error.put("status", HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(AvailabilitySlotNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAvailabilitySlotNotFoundException(AvailabilitySlotNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Availability slot Not Found");
        error.put("message", ex.getMessage());
        error.put("timestamp", Instant.now());
        error.put("status", HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }




    // Invalid
    @ExceptionHandler(InvalidAvailabilitySlotException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidAvailabilitySlotException(InvalidAvailabilitySlotException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Invalid availability slot");
        error.put("message", ex.getMessage());
        error.put("timestamp", Instant.now());
        error.put("status", HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InvalidMenuItemException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidMenuItemException(InvalidMenuItemException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Invalid menu item");
        error.put("message", ex.getMessage());
        error.put("timestamp", Instant.now());
        error.put("status", HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }




    // Generic
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", "Bad Request");
        error.put("message", ex.getMessage());
        error.put("timestamp", Instant.now());
        error.put("status", HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
