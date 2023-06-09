package com.gima.gimastore.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectMapperUtils {

    private static ModelMapper modelMapper;


    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    private ObjectMapperUtils() {
    }


    public static <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }


    public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }

//    /**
//     * Maps {@code source} to {@code destination}.
//     *
//     * @param source      object to map from
//     * @param destination object to map to
//     */
//    public static <S, D> D map(final S source, D destination) {
//        modelMapper.map(source, destination);
//        return destination;
//    }

}