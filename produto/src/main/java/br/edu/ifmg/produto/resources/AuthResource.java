package br.edu.ifmg.produto.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/auth")
public class AuthResource {

    @Autowired
    private AuthService authService;

    PostMapping(value="recover_token")
    public ResponseEntity<Void> createRecoverToken(){
        authoSer.createRecoverToken(dto);
        return ResponseEntity.noContent().build();
    }
}
