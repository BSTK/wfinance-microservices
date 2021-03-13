package dev.bstk.wfinance.categoria.domain.entidade;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Table(name = "CATEGORIA")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Categoria implements Serializable {

    @Id
    @Column(name = "ID")
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(name = "NOME")
    private String nome;

    @NotNull
    @NotEmpty
    @Size(min = 7, max = 7)
    @Column(name = "COR")
    private String cor;

    public Categoria() { }

    public Categoria(@NotNull @NotEmpty final String nome,
                     @NotNull @NotEmpty final String cor) {
        this.nome = nome;
        this.cor = cor;
    }

}
