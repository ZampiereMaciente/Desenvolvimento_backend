package br.edu.ifmg.produto.services;

import br.edu.ifmg.produto.dtos.RequestTokenDTO;
import br.edu.ifmg.produto.entities.User;
import br.edu.ifmg.produto.repository.PasswordRecoverRepository;
import br.edu.ifmg.produto.repository.UserRepository;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    @Value("${email.password-recover.token.minutes}")
    private int TokenMinutes;

    @Value("{email.password-recover}")
    private String uri;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    private PasswordRecoverRepository passwordRecoverRepository;

    public void createRecoverToken(RequestTokenDTO dto){


        User user = userRepository.findByEmail(dto.getEmail());

        String token = UUID.randomUUID().toString();
    }

}
