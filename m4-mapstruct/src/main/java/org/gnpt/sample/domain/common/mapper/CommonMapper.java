package org.gnpt.sample.domain.common.mapper;

import java.util.Collection;
import java.util.List;

public interface CommonMapper<S, T> {

    List<T> toDtos(Collection<S> source);

}
