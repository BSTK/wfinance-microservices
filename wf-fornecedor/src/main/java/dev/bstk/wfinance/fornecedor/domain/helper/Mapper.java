package dev.bstk.wfinance.fornecedor.domain.helper;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Mapper {

    private static final ModelMapper MAPPER = new ModelMapper();

    private Mapper() { }

    public static <T> T map(final Object source, final Class<T> clazz) {
        return MAPPER.map(source, clazz);
    }

    public static <S, T> List<T> list(final List<S> source, final Class<T> clazz) {
        return source
            .stream()
            .map(element -> MAPPER.map(element, clazz))
            .collect(Collectors.toList());
    }
}
