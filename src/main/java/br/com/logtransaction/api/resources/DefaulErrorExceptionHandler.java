package br.com.logtransaction.api.resources;

import br.com.logtransaction.api.services.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


@ControllerAdvice
public class DefaulErrorExceptionHandler {
    

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<DefaultError> badRequest(BadRequestException ex, HttpServletRequest httpServletRequest) {

        DefaultError defaultError =  DefaultError.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Bad Request")
            .path(httpServletRequest.getRequestURI())
            .message(ex.getMsg())
            .build();
                   
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(defaultError);
    }


}
