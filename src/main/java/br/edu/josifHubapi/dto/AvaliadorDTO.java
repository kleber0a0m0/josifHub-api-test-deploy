package br.edu.josifHubapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvaliadorDTO {

        private Long id;
        private String nome;
        private String email;
        private String senha;
        private String telefone;
        private String cpf;
        private String lattes;
        private String areaFormacao;
        private String instituicao;
        private String areaAtuacao;
        private String titulacao;
}
