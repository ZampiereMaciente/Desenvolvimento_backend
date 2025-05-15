package edu.ifmg.br.produto.dtos;

import edu.ifmg.br.produto.entities.Role;

public class RoleDTO {

    private long id;
    private String authority;

    public RoleDTO(Role role){
        this.id = role.getId();
        this.authority = role.getAuthority();
    }

    public RoleDTO (long id, String authority){
        this.id = id;
        this.authority = authority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                '}';
    }
}
