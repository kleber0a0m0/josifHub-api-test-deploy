package br.edu.josifHubapi.dto;

import br.edu.josifHubapi.domain.Avaliador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AreaDTO {
    private String codigo_area;

    private String nome;

}
