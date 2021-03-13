package dev.bstk.wfinance.fornecedor.domain;

import dev.bstk.wfinance.fornecedor.api.request.EnderecoRequest;
import dev.bstk.wfinance.fornecedor.api.request.NovoFornecedorRequest;
import dev.bstk.wfinance.fornecedor.domain.entidade.Endereco;
import dev.bstk.wfinance.fornecedor.domain.entidade.Fornecedor;
import dev.bstk.wfinance.fornecedor.domain.helper.DadosInvalidosException;
import dev.bstk.wfinance.fornecedor.domain.helper.Mapper;
import dev.bstk.wfinance.fornecedor.domain.repository.FornecedorRepository;
import dev.bstk.wfinance.fornecedor.domain.validacao.ValidarCadastroDeEndereco;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final ValidarCadastroDeEndereco validarCadastroDeEndereco;

    @Autowired
    public FornecedorService(final FornecedorRepository fornecedorRepository,
                             final ValidarCadastroDeEndereco validarCadastroDeEndereco) {
        this.fornecedorRepository = fornecedorRepository;
        this.validarCadastroDeEndereco = validarCadastroDeEndereco;
    }

    public Page<Fornecedor> pessoas(final String nome, final Pageable pageable) {
        return StringUtils.isEmpty(nome)
            ? fornecedorRepository.findAll(pageable)
            : fornecedorRepository.filtar(pageable, nome);
    }

    public Fornecedor novaPessoa(final NovoFornecedorRequest request) {
        validarCadastroDeEndereco.executar(request);

        boolean existePessoaCadastradaComNome = fornecedorRepository.existeFornecedorCadastradaComNome(request.getNome());

        if (existePessoaCadastradaComNome) {
            throw new DadosInvalidosException("Fornecedor.nome", request.getNome(),
                "Já existe um fornecedor com este nome!");
        }

        final var novaPessoa = Mapper.map(request, Fornecedor.class);
        return fornecedorRepository.save(novaPessoa);
    }

    public Optional<Fornecedor> atualizar(final Long id, final NovoFornecedorRequest request) {
        final var fornecedorOptional = fornecedorRepository.findById(id);

        if (fornecedorOptional.isPresent()) {
            final var pessoaAtualizada = Mapper.map(request, Fornecedor.class);
            pessoaAtualizada.setId(id);

            final var pessoaSalva = fornecedorRepository.save(pessoaAtualizada);
            return Optional.of(pessoaSalva);
        }

        return Optional.empty();
    }

    public Optional<Fornecedor> atualizarEndereco(final Long id, final EnderecoRequest request) {
        final var fornecedorOptional = fornecedorRepository.findById(id);

        if (fornecedorOptional.isPresent()) {
            final var pessoa = fornecedorOptional.get();
            final var endereco = Mapper.map(request, Endereco.class);
            pessoa.setEndereco(endereco);

            final var pessoaSalva = fornecedorRepository.save(pessoa);
            return Optional.of(pessoaSalva);
        }

        return Optional.empty();
    }

    /// TODO: APLICAR VALIDAÇÃO DE LANÇAMENTO
    public Optional<Fornecedor> atualizarAtivo(final Long fornecedorId, final Boolean ativo) {
        final var fornecedorOptional = fornecedorRepository.findById(fornecedorId);

        if (fornecedorOptional.isPresent()) {
            final var pessoa = fornecedorOptional.get();
            pessoa.setAtivo(ativo);

            final var pessoaSalva = fornecedorRepository.save(pessoa);
            return Optional.of(pessoaSalva);
        }

        return Optional.empty();
    }

    /// TODO: APLICAR VALIDAÇÃO DE LANÇAMENTO
    public void excluir(final Long fornecedorId) {
        fornecedorRepository.deleteById(fornecedorId);
    }

}
