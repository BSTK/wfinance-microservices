package dev.bstk.wfinance.categorias.domain.service;

import dev.bstk.wfinance.categorias.api.request.NovaCategoriaRequest;
import dev.bstk.wfinance.categorias.domain.entidade.Categoria;
import dev.bstk.wfinance.categorias.domain.repository.CategoriaRepository;
import dev.bstk.wfinance.core.exception.DadosInvalidosException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static dev.bstk.wfinance.categorias.api.CategoriaMapper.entidade;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(final CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Page<Categoria> categorias(final String nome, final Pageable pageable) {
        return StringUtils.isEmpty(nome)
            ? categoriaRepository.findAll(pageable)
            : categoriaRepository.filtar(pageable, nome);
    }

    public Categoria novaCategoria(final NovaCategoriaRequest request) {
        boolean existeCategoriaCadastrada = categoriaRepository.existeCategoriaCadastrada(request.getNome());

        if (Objects.isNull(request.getId()) && existeCategoriaCadastrada) {
            throw new DadosInvalidosException("Categoria.Nome", request.getNome(),
                "Já existe uma Categoria cadastrada para o nome: \"" + request.getNome() + "\"");
        }

        final var categoriaNova = entidade(request);
        return categoriaRepository.save(categoriaNova);
    }

    /// TODO: IMPLEMENTAR CHAMADA LÓGICA PARA O SERVIÇO DE LANÇAMENTO
    public void excluir(final Long categoriaId) {
        categoriaRepository.deleteById(categoriaId);
    }
}
