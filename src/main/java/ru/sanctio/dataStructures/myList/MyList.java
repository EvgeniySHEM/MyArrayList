package ru.sanctio.dataStructures.myList;

import java.util.Comparator;

public interface MyList<E> {
    void add(E element);
    void add(int index, E element);
    int size();
    boolean isEmpty();
    E get(E element);
    boolean remove(E element);
    void clear();
    E set(int index, E element);
    void sort(int fromIndex, int toIndex, Comparator<? super E> comparator);
    void sort(int fromIndex, int toIndex);

}
