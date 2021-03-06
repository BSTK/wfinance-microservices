package dev.bstk.wfinance.categorias.domain.repository;

import dev.bstk.wfinance.categorias.domain.entidade.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaRepositoryQuery {

    Page<Categoria> filtar(final Pageable pageable, final String nome);

}
