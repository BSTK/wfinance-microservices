package dev.bstk.wfinance.core.helper;

import org.modelmapper.ModelMapper;

public abstract class Mapper {

    private static final ModelMapper INSTANCE = new ModelMapper();

    public static <D> D map(Object objeto, Class<D> klass) {
        return getInstance().map(objeto, klass);
    }

    private static ModelMapper getInstance() {
        synchronized (Mapper.class) {
            return INSTANCE;
        }
    }

}
