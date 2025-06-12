package br.edu.ifmg.produto.dtos;

public class RequestTokenDTO {

    private String email;

    public RequestTokenDTO() {
    }

    public RequestTokenDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
