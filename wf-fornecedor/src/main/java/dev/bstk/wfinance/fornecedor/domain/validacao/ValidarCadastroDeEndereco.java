package dev.bstk.wfinance.fornecedor.domain.validacao;

import dev.bstk.wfinance.fornecedor.api.request.NovoFornecedorRequest;
import dev.bstk.wfinance.fornecedor.domain.exception.EnderecoJaCadastradoException;
import dev.bstk.wfinance.fornecedor.domain.helper.DadosInvalidosException;
import dev.bstk.wfinance.fornecedor.domain.repository.FornecedorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Slf4j
@Component
public class ValidarCadastroDeEndereco {

    private final FornecedorRepository fornecedorRepository;

    @Autowired
    public ValidarCadastroDeEndereco(final FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public void executar(final NovoFornecedorRequest request) {
        final var endereco = request.getEndereco();

        if (Objects.nonNull(endereco)) {
            validar(endereco.getCep(), "Endereco.cep", "CEP deve ser informado!");
            validar(endereco.getNumero(), "Endereco.numero", "Número deve ser informado!");
            validar(endereco.getBairro(), "Endereco.bairro", "Bairro deve ser informado!");
            validar(endereco.getCidade(), "Endereco.cidade", "Cidade deve ser informado!");
            validar(endereco.getEstado(), "Endereco.estado", "Estado deve ser informado!");
            validar(endereco.getLogradouro(), "Endereco.logradouro", "Logradouro deve ser informado!");
        }

        final var existeEnderecoCadastrado = fornecedorRepository.existeEnderecoCadastrado(
            endereco.getCep(),
            endereco.getLogradouro(),
            endereco.getNumero());

        if (existeEnderecoCadastrado) {
            log.warn("Já existe uma pessoa cadastrada com este endereco. Dados: Cep: {}, Logradouro: {}, Numero: {}",
                endereco.getCep(), endereco.getLogradouro(), endereco.getNumero());
            throw new EnderecoJaCadastradoException("Já existe uma pessoa cadastrada com este endereco.");
        }
    }

    private void validar(final String valor, final String campo, final String mensagem) {
        if (Objects.nonNull(valor) && StringUtils.isEmpty(valor)) {
            log.warn(mensagem);
            throw new DadosInvalidosException(campo, valor, mensagem);
        }
    }
}
