package br.edu.josifHubapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "area_avaliadores")
public class AreaAvaliadores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo", nullable = false)
    private Long codigo;

    @Column(name = "codigo_area", length = 5000, nullable = false)
    private String codigoArea;

    @Column(name = "codigo_avaliador", length = 5000, nullable = false)
    private String codigoAvaliador;

}
