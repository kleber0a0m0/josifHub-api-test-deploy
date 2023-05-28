package br.edu.josifHubapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AreaAvaliadoresDTO {

    private String codigo_area;
    private String codigo_avaliador;
}
