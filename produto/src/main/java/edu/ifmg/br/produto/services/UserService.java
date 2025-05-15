package edu.ifmg.br.produto.services;

import edu.ifmg.br.produto.dtos.ProductDTO;
import edu.ifmg.br.produto.dtos.RoleDTO;
import edu.ifmg.br.produto.dtos.UserDTO;
import edu.ifmg.br.produto.dtos.UserInsertDTO;
import edu.ifmg.br.produto.entities.Product;
import edu.ifmg.br.produto.entities.Role;
import edu.ifmg.br.produto.entities.User;
import edu.ifmg.br.produto.repository.RoleRepository;
import edu.ifmg.br.produto.repository.UserRepository;
import edu.ifmg.br.produto.resources.ProductResource;
import edu.ifmg.br.produto.services.exceptions.DataBaseException;
import edu.ifmg.br.produto.services.exceptions.ResourceNotFound;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable){

        Page<User> list = repository.findAll((org.springframework.data.domain.Pageable) pageable);

        return list.map(u -> new UserDTO(u));

    }

    @Transactional(readOnly = true)
    public UserDTO findByid(Long id){

        Optional<User> opt = repository.findById(id);
        User user = opt.orElseThrow( () -> new ResourceNotFound("User not found"));
        return new UserDTO(user);

    }
    

    @Transactional
    public UserDTO insert(UserInsertDTO dto){

        User entity = new User();
        copyDTOToEntity(dto, entity);
        entity.setPassword(
                passwordEncoder.encode(dto.getPassword())
        );

        User novo = repository.save(entity);

        return new UserDTO();

    }

    private void copyDTOToEntity(UserDTO dto, User entity) {

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();

        for (RoleDTO role: dto.getRoles()){

            Role r =
                    roleRepository.getReferenceById(role.getId());
            entity.getRoles().add(r);
        }

    }

    @Transactional
    public UserDTO update(Long id, UserDTO dto){

        try {
            User entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);

            entity = repository.save(entity);

            return new UserDTO(entity);

        }
        catch (EntityNotFoundException e){
            throw new ResourceNotFound("Product not found: " + id);
        }

    }

    private void copyDtoToEntity(UserDTO dto, User entity) {
    }

    @Transactional
    public void delete(Long id){

        if (!repository.existsById(id)) {
            throw new ResourceNotFound("User not found: " + id);
        }

        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DataBaseException("Integrity violation: " + id);
        }

    }

    

}
