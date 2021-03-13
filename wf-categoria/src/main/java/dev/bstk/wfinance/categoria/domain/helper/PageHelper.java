package dev.bstk.wfinance.categoria.domain.helper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public final class PageHelper {

    private PageHelper() { }

    public static <T, R> Page<R> page(final List<R> response,
                                      final Page<T> page) {
        return new PageImpl<>(response, page.getPageable(), page.getTotalElements());
    }

}
