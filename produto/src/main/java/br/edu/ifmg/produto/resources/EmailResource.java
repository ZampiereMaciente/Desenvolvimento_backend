package br.edu.ifmg.produto.resources;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/email")
public class EmailResource {

    @PostMapping
    public ResponseEntity<void> sendEmail(@Valid @RequestBody EmailDTO) {
        // Implement email sending logic here
        // This is a placeholder method for demonstration purposes
        return ResponseEntity.ok().build();
    }

}
