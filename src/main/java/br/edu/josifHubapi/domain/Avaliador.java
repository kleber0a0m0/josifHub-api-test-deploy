package br.edu.josifHubapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "avaliador")
public class Avaliador implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo", nullable = false)
    private Long id;

    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "senha", length = 50, nullable = false)
    private String senha;

    @Column(name = "telefone", length = 50, nullable = false)
    private String telefone;

    @Column(name = "cpf", length = 50, nullable = false)
    private String cpf;

    @Column(name = "lattes", length = 50, nullable = false)
    private String lattes;

    @Column(name = "area-formacao", length = 50, nullable = false)
    private String areaFormacao;

    @Column(name = "instituicao", length = 50, nullable = false)
    private String instituicao;

    @Column(name = "area-atuacao", length = 50, nullable = false)
    private String areaAtuacao;

    @Column(name = "titulacao", length = 50, nullable = false)
    private String titulacao;

}
