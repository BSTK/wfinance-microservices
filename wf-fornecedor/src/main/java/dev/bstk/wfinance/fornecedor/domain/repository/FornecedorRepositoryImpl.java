package dev.bstk.wfinance.fornecedor.domain.repository;

import dev.bstk.wfinance.fornecedor.domain.entidade.Fornecedor;
import dev.bstk.wfinance.fornecedor.domain.helper.AbstractRepositoryQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public class FornecedorRepositoryImpl extends AbstractRepositoryQuery implements FornecedorRepositoryQuery {

    private static final String QUERY_PARAM = "nome";
    private static final String QUERY_COUNT = "SELECT count(*) FROM Pessoa p ";
    private static final String QUERY_FILTRO = "SELECT p FROM Pessoa p WHERE UPPER(p.nome) LIKE UPPER(CONCAT(:nome, '%')) ";

    @Override
    public Page<Fornecedor> filtar(final Pageable pageable, final String nome) {
        final var query = manager.createQuery(QUERY_FILTRO, Fornecedor.class);
        return executar(query, pageable, Map.of(QUERY_PARAM, nome));
    }

    @Override
    public String queryCount() {
        return QUERY_COUNT;
    }

}
