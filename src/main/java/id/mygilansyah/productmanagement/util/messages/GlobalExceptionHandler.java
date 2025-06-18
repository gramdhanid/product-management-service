package id.mygilansyah.productmanagement.util.messages;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    private final CustomResponseGenerator<Object> customResponseGenerator;

    public GlobalExceptionHandler(CustomResponseGenerator<Object> customResponseGenerator) {
        this.customResponseGenerator = customResponseGenerator;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomResponse<Object>> handleValitionException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        String errorDetail = String.join(", ", errors.values());
        CustomResponse<Object> response = customResponseGenerator.errorResponse(errorDetail);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
