package br.edu.josifHubapi.domain;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trabalhos_cadastrados")
public class Trabalho implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo", nullable = false)
    private Long codigo;

    @Column(name = "titulo", length = 5000, nullable = false)
    private String titulo;

    @Column(name = "resumo", length = 5000, nullable = false)
    private String resumo;

    @Column(name = "palavras-chave", length = 5000, nullable = false)
    private String palavrasChave;

    @Column(name = "area", length = 5000, nullable = false)
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

    public void setAutores(List<Autor> autores) {
        if (autores == null || autores.isEmpty()) {
            return;
        }

        // Define o primeiro autor obrigatoriamente
        this.autor1 = autores.get(0);

        // Define os demais autores, se houverem
        for (int i = 1; i < autores.size(); i++) {
            Autor autor = autores.get(i);
            switch (i) {
                case 1:
                    this.autor2 = autor;
                    break;
                case 2:
                    this.autor3 = autor;
                    break;
                case 3:
                    this.autor4 = autor;
                    break;
                case 4:
                    this.autor5 = autor;
                    break;
                case 5:
                    this.autor6 = autor;
                    break;
                case 6:
                    this.autor7 = autor;
                    break;
                case 7:
                    this.autor8 = autor;
                    break;
                case 8:
                    this.autor9 = autor;
                    break;
                case 9:
                    this.autor10 = autor;
                    break;
                case 10:
                    this.autor11 = autor;
                    break;
                default:
                    break;
            }
        }
    }


}
