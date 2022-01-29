package br.com.logtransaction.api.controllers;

import br.com.logtransaction.api.models.Client;
import br.com.logtransaction.api.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}")
    public Client findById(@PathVariable  String id) {
        return clientService.findById(id);
    }

    @PostMapping
    public void save(@RequestBody Client user) {
        clientService.save(user);
        ResponseEntity.status(HttpStatus.CREATED);
    }

}
