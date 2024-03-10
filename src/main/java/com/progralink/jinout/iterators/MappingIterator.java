package com.progralink.jinout.iterators;

import java.util.Iterator;
import java.util.function.Function;

public class MappingIterator<A, B> implements Iterator<B> {
    private Iterator<A> sourceIterator;
    private Function<A, B> mapper;

    public MappingIterator(Iterator<A> sourceIterator, Function<A, B> mapper) {
        this.sourceIterator = sourceIterator;
        this.mapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return sourceIterator.hasNext();
    }

    @Override
    public B next() {
        return mapper.apply(sourceIterator.next());
    }

    @Override
    public void remove() {
        sourceIterator.remove();
    }
}
