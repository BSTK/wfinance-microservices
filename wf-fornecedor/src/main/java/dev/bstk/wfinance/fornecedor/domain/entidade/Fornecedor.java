package dev.bstk.wfinance.fornecedor.domain.entidade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "FORNECEDOR")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Fornecedor implements Serializable {

    @Id
    @Column(name = "ID")
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(name = "NOME")
    private String nome;

    @Column(name = "ATIVO")
    private boolean ativo;

    @Embedded
    private Endereco endereco;

    @Valid
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnoreProperties("fornecedor")
    @OneToMany(
        mappedBy = "fornecedor",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<Contato> contatos = new HashSet<>();

}
