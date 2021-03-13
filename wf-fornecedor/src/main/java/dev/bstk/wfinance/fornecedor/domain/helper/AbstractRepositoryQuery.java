package dev.bstk.wfinance.fornecedor.domain.helper;

import org.hibernate.query.internal.QueryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.Map;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public abstract class AbstractRepositoryQuery {

    private static final String QUERY_WHERE = "WHERE";

    @PersistenceContext
    protected EntityManager manager;

    public abstract String queryCount();

    protected <T> Page<T> executar(final Query query,
                                   final Pageable pageable,
                                   final Map<String, Object> params) {

        final var queryFiltro = ((QueryImpl) query).getQueryString();
        final var totalRegistrosPorPagina = pageable.getPageSize();
        final var totalRegistros = calcularTotalRegistros(params, queryFiltro);
        final var primeiraPgaina = pageable.getPageNumber() * pageable.getPageSize();

        params.forEach((k, v) -> {
            if (isNotEmpty(v.toString())) {
                query.setParameter(k, v);
            }
        });

        query.setFirstResult(primeiraPgaina);
        query.setMaxResults(totalRegistrosPorPagina);

        final var resultado = query.getResultList();

        return new PageImpl<>(resultado, pageable, totalRegistros);
    }

    private Long calcularTotalRegistros(final Map<String, Object> params,
                                        final String queryFiltro) {
        final var where = Arrays.asList(queryFiltro.split(QUERY_WHERE));

        if (isNotEmpty(where)) {
            final Query queryCount = where.size() == 1
                ? manager.createQuery(queryCount(), Long.class)
                : manager.createQuery(queryCount().concat(QUERY_WHERE) + where.get(1), Long.class);

            params.forEach((k, v) -> {
                if (isNotEmpty(v.toString())) {
                    queryCount.setParameter(k, v);
                }
            });

            return (Long) queryCount.getSingleResult();
        }

        return 0L;
    }

}
