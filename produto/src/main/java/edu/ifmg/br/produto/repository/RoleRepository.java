package edu.ifmg.br.produto.repository;

import edu.ifmg.br.produto.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {


}
