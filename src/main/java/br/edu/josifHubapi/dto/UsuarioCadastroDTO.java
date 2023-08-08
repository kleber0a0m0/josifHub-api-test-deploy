package br.edu.josifHubapi.dto;

import br.edu.josifHubapi.enums.RoleName;

public record UsuarioCadastroDTO(String email, String senha, RoleName funcao) {
}