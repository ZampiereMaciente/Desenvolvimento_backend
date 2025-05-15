package edu.ifmg.br.produto.repository;

import edu.ifmg.br.produto.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
}
