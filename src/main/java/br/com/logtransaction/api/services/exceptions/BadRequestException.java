package br.com.logtransaction.api.services.exceptions;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.BindingResult;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BadRequestException extends RuntimeException {
    
    private List<String> msg = new ArrayList<>();
    
    public BadRequestException(BindingResult result) {
        super();

        List<String> errors = new ArrayList<String>();
		result.getAllErrors().forEach(erro -> errors.add(erro.getDefaultMessage()));
        this.msg = errors;           
    }
}
