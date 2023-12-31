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
@Table(name = "areas")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_area", nullable = false)
    private Long idArea;

    @Column(name = "codigo_area", length = 5000, nullable = false)
    private String codigoArea;

    @Column(name = "nome", length = 5000, nullable = false)
    private String nome;


}
