package business.configuration;

import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class Mapper {

    private static ModelMapper modelMapepr = new ModelMapper();

    private Mapper(){};

    public static <S, D> D map(S src, Class<D> dest) {
        return modelMapepr.map(src,dest);
    }

    public static <S, D> Collection<D> mapCollection(Collection<S> src, Class<D> dest) {
        return src.stream().map(s -> map(s,dest)).collect(Collectors.toList());
    }



}
