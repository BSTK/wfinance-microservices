package dev.bstk.wfinance.fornecedor.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class NovoFornecedorRequest implements Serializable {

    @NotNull
    @NotEmpty
    private String nome;

    @NotNull
    private boolean ativo = true;

    private ContatoRequest contato;
    private EnderecoRequest endereco;

}
