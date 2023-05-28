package br.edu.josifHubapi.dto;

import br.edu.josifHubapi.domain.Area;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvaliadorDTO {

        private Long codigo;
        private String nome;
        private String email;
        private String senha;
        private String telefone;
        private String cpf;
        private String lattes;
        private String areaFormacao;
        private String instituicao;
        private String titulacao;
}
