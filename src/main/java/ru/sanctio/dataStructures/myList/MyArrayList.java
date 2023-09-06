package ru.sanctio.dataStructures.myList;

import java.util.Arrays;

/**
 * Реализация интерфейса List с изменяемым размером массива.
 * Реализует ???все необязательные операции??? со списком и разрешает все элементы, включая нулевые.
 * @author Captain America
 *
 */
public class MyArrayList<E> implements MyList<E> {

    private Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Создает пустой список с начальной емкостью десять.
     */
    public MyArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Создает пустой список с указанной начальной емкостью.
     * @param initialCapacity – начальная емкость списка.
     * @throws IllegalArgumentException – если заданная начальная емкость отрицательна
     */
    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("InitialCapacity can't be negative. Your value: " + initialCapacity);
        }
        elements = new Object[initialCapacity];
    }

    /**
     * Добавляет указанный элемент в конец этого списка.
     * @param element - элемент, который будет добавлен в этот список
     */
    public void add(E element) {
        checkCapacity(size);
        elements[size++] = element;
    }

    /**
     * Добавляет указанный элемент на указанный индекс.
     * @param element - элемент, который будет добавлен в этот список
     * @throws IndexOutOfBoundsException - если заданный индекс вышел за рамки этого списка.
     */
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        checkCapacity(size);
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    /**
     * Проверяет возможность добавить новый элемент в этот список.
     * @param minCapacity – необходимая минимальная емкость.
     */
    private void checkCapacity(int minCapacity) {
        if (minCapacity == elements.length) {
            increasedCapacity(minCapacity + 1);
        }
    }

    /**
     * Увеличивает емкость, чтобы гарантировать, что она может содержать как минимум количество элементов,
     * указанное аргументом минимальной емкости.
     * @param minCapacity – необходимая минимальная емкость.
     */
    private void increasedCapacity(int minCapacity) {
        int oldCapacity = elements.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }
        elements = Arrays.copyOf(elements, newCapacity);
    }

    private void rangeCheckForAdd(int index) {
        if (index >= elements.length || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + " - has gone beyond the scope of this list");
        }
    }

//
//    public E get(int index) {
//        rangeCheck(index);
//        return (E) elements[index];
//    }
//
//    public E remove(int index) {
//        rangeCheck(index);
//        E oldValue = (E) elements[index];
//        int numMoved = size - index - 1;
//        if (numMoved > 0) {
//            System.arraycopy(elements, index + 1, elements, index, numMoved);
//        }
//        elements[--size] = null;
//        return oldValue;
//    }
//
//    public boolean remove(Object o) {
//        if (o == null) {
//            for (int i = 0; i < size; i++) {
//                if (elements[i] == null) {
//                    remove(i);
//                    return true;
//                }
//            }
//        } else {
//            for (int i = 0; i < size; i++) {
//                if (o.equals(elements[i])) {
//                    remove(i);
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//

    /**
     * Проверяет количество элементов в этом списке.
     * @return - количество элементов в этом списке.
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет отсутствие элементов в этом списке.
     * @return - true, если этот список не содержит элементов.
     */
    public boolean isEmpty() {
        return size == 0;
    }


    private void rangeCheck(int index) {
        if (index >= elements.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}