package dev.bstk.wfinance.categorias.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CategoriaResumoResponse implements Serializable {

    @EqualsAndHashCode.Include
    private Long id;
    private String nome;

}
