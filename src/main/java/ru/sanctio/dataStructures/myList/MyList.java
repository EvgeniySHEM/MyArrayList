package ru.sanctio.dataStructures.myList;

public interface MyList<E> {
    void add(E element);
    void add(int index, E element);
    int size();
    boolean isEmpty();

}
