package br.com.logtransaction.api.resources;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import br.com.logtransaction.api.services.exceptions.BadRequestException;
import br.com.logtransaction.api.services.exceptions.CustomerNameException;


@ControllerAdvice
public class DefaulErrorExceptionHandler {
    
    @ExceptionHandler(CustomerNameException.class)
    public ResponseEntity<DefaultError> customerName(CustomerNameException ex, HttpServletRequest httpServletRequest) {
        List<String> messages = new ArrayList<String>();
        messages.add(ex.getMessage());
        DefaultError defaultError =  DefaultError.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Bad Request")
            .path(httpServletRequest.getRequestURI())
            .message(messages)
            .build();
                   
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(defaultError);
    }

    
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
