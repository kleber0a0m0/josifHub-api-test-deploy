package br.edu.josifHubapi.domain;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trabalhos_cadastrados")
public class Trabalhos implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo", nullable = false)
    private Long id;

    @Column(name = "titulo", length = 50, nullable = false)
    private String titulo;

    @Column(name = "resumo", length = 50, nullable = false)
    private String resumo;

    @Column(name = "palavras-chave", length = 50, nullable = false)
    private String palavrasChave;

    @Column(name = "area", length = 50, nullable = false)
    private String area;

    @JoinColumn(name = "autor1")
    @ManyToOne
    @NotNull
    private Autor autor1;

    @JoinColumn(name = "autor2")
    @ManyToOne
    private Autor autor2;

    @JoinColumn(name = "autor3")
    @ManyToOne
    private Autor autor3;

    @JoinColumn(name = "autor4")
    @ManyToOne
    private Autor autor4;

    @JoinColumn(name = "autor5")
    @ManyToOne
    private Autor autor5;

    @JoinColumn(name = "autor6")
    @ManyToOne
    private Autor autor6;

    @JoinColumn(name = "autor7")
    @ManyToOne
    private Autor autor7;

    @JoinColumn(name = "autor8")
    @ManyToOne
    private Autor autor8;

    @JoinColumn(name = "autor9")
    @ManyToOne
    private Autor autor9;

    @JoinColumn(name = "autor10")
    @ManyToOne
    private Autor autor10;

    @JoinColumn(name = "autor11")
    @ManyToOne
    private Autor autor11;

}
