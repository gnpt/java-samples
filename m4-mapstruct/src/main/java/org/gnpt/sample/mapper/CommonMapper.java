package org.gnpt.sample.mapper;

import java.util.Collection;
import java.util.List;

public abstract class CommonMapper<S, T> {

    public abstract List<T> toDtos(Collection<S> source);

}
