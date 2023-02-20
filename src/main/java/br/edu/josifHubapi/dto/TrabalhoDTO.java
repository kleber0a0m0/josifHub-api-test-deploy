package br.edu.josifHubapi.dto;

import br.edu.josifHubapi.domain.Autor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrabalhoDTO {

    Long codigo;
    String area;
    String palavrasChave;
    String resumo;
    String titulo;
    Autor autor1;
    Autor autor2;
    Autor autor3;
    Autor autor4;
    Autor autor5;
    Autor autor6;
    Autor autor7;
    Autor autor8;
    Autor autor9;
    Autor autor10;
    Autor autor11;
}
