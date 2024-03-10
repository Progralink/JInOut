package com.progralink.jinout.iterators;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class Iterators {
    private Iterators() { }

    public static <E> Iterator<E> of(E... data) {
        return Arrays.asList(data).iterator();
    }

    public static <E> Iterator<E> filter(Iterator<E> iterator, Predicate<E> filter) {
        return new FilteringIterator<>(iterator, filter);
    }

    public static <A,B> Iterator<B> map(Iterator<A> iterator, Function<A,B> mapper) {
        return new MappingIterator<>(iterator, mapper);
    }

    public static <E> Stream<E> asStream(Iterator<E> iterator) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
    }

    public static <E> Iterable<E> asIterable(Iterator<E> iterator) {
        return () -> iterator;
    }

    public static <E> Iterable<E> asIterable(Supplier<Iterator<E>> iteratorSupplier) {
        return iteratorSupplier::get;
    }

}
