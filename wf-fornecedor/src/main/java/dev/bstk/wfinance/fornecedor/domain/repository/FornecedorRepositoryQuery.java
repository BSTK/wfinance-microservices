package dev.bstk.wfinance.fornecedor.domain.repository;

import dev.bstk.wfinance.fornecedor.domain.entidade.Fornecedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FornecedorRepositoryQuery {

    Page<Fornecedor> filtar(final Pageable pageable, final String nome);
}
