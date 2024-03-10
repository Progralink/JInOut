package com.progralink.jinout.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class FilteringIterator<E> implements Iterator<E> {
    private Iterator<E> source;
    private Predicate<E> filter;
    private E next;

    public FilteringIterator(Iterator<E> source, Predicate<E> filter) {
        this.source = source;
        this.filter = filter;
    }

    @Override
    public boolean hasNext() {
        if (next == null) {
            while (source.hasNext()) {
                next = source.next();
                if (filter.test(next)) {
                    return true;
                }
                next = null;
            }
        }
        return next != null;
    }

    @Override
    public E next() {
        if (next == null) {
            throw new NoSuchElementException();
        }
        E result = next;
        next = null;
        return result;
    }

    @Override
    public void remove() {
        source.remove();
    }
}
