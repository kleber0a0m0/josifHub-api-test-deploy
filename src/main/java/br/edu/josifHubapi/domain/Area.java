package br.edu.josifHubapi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;



@Entity
@Table(name = "areas")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo", nullable = false)
    private Long codigo;

    @Column(name = "nome", length = 5000, nullable = false)
    private String nome;

    @ManyToMany(mappedBy = "areasAtuacao",fetch = FetchType.LAZY)
    @JsonBackReference
    Set<Avaliador> avaliadores;

}
