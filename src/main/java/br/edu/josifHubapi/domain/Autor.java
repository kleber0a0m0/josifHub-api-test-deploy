package br.edu.josifHubapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo", nullable = false)
    private Long id;

    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "sobrenome", length = 50, nullable = false)
    private String sobrenome;

    @Column(name = "ORCID", length = 50, nullable = false)
    private String ORCID;

    @Column(name = "pais", length = 50, nullable = false)
    private String pais;

    @Column(name = "instituicao", length = 50, nullable = false)
    private String instituicao;

    @Column(name = "resumo-biografico", length = 50, nullable = false)
    private String resumoBiografico;

}