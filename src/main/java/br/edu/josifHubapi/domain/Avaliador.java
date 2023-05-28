package br.edu.josifHubapi.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "avaliadores")
public class Avaliador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_avaliador", nullable = false)
    private Long codigoAvaliador;

    @Column(name = "nome", length = 5000, nullable = false)
    private String nome;

    @Column(name = "email", length = 5000, nullable = false)
    private String email;

    @Column(name = "senha", length = 5000, nullable = false)
    private String senha;

    @Column(name = "telefone", length = 5000, nullable = false)
    private String telefone;

    @Column(name = "cpf", length = 5000, nullable = false)
    private String cpf;

    @Column(name = "lattes", length = 5000, nullable = false)
    private String lattes;

    @Column(name = "area-formacao", length = 5000, nullable = false)
    private String areaFormacao;

    @Column(name = "instituicao", length = 5000, nullable = false)
    private String instituicao;

    @Column(name = "titulacao", length = 5000, nullable = false)
    private String titulacao;

}
