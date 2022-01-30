package br.com.logtransaction.api.controllers;

import br.com.logtransaction.api.models.Client;
import br.com.logtransaction.api.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public Map<String,Client> findAll() {
        return clientService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Client client) {
        clientService.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
