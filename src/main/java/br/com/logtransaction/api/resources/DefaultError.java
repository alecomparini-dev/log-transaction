package br.com.logtransaction.api.resources;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class DefaultError {

    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private List<String> message;
    private String path;

}
