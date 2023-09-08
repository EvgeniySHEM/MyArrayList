package ru.sanctio.dataStructures.myList;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Реализация интерфейса MyList с изменяемым размером массива.
 * Каждый элемент находится на определенном индексе этого массива, индексация начинается с нуля.
 * Реализует операции со списком интерфейса MyList и разрешает все элементы, включая null значения.
 * Не является потокобезопасным.
 *
 * @author Sharychenkov Eugene
 */
public class MyArrayList<E> implements MyList<E> {

    private Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;
    private static final Random random = ThreadLocalRandom.current();

    /**
     * Создает пустой список с начальной емкостью равной десяти.
     */
    public MyArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Создает пустой список с указанной начальной емкостью.
     *
     * @param initialCapacity начальная емкость списка.
     * @throws IllegalArgumentException если заданная начальная емкость отрицательна
     */
    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("InitialCapacity can't be negative. Your value: " + initialCapacity);
        }
        elements = new Object[initialCapacity];
    }

    /**
     * Добавляет указанный элемент в конец этого списка.
     *
     * @param element элемент, который будет добавлен в этот список
     * @throws ArrayIndexOutOfBoundsException если вместимость может превысить Integer.MAX_VALUE.
     */
    public void add(E element) {
        checkCapacity(size);
        elements[size++] = element;
    }

    /**
     * Добавляет указанный элемент на указанный индекс.
     *
     * @param index   индекс, на который будет добавлен элемент.
     * @param element элемент, который будет добавлен в этот список.
     * @throws ArrayIndexOutOfBoundsException если вместимость может превысить Integer.MAX_VALUE.
     * @throws IndexOutOfBoundsException      если индекс выходит за пределы диапазона (index < 0 || index > size())
     */
    public void add(int index, E element) {
        if(index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index +
                    "out of bounds for length: " + size);
        }
        checkCapacity(size);
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    /**
     * Проверяет возможность добавить новый элемент в этот список.
     *
     * @param minCapacity необходимая минимальная емкость.
     */
    private void checkCapacity(int minCapacity) {
        long checkSize = minCapacity;
        if (checkSize + 1 > Integer.MAX_VALUE) {
            throw new ArrayIndexOutOfBoundsException("Capacity there can't be more Integer.MAX_VALUE");
        }
        if (minCapacity == elements.length) {
            increasedCapacity(minCapacity + 1);
        }
    }

    /**
     * Увеличивает емкость, чтобы гарантировать, что она может содержать как минимум количество элементов,
     * указанное аргументом минимальной емкости.
     *
     * @param minCapacity необходимая минимальная емкость.
     */
    private void increasedCapacity(int minCapacity) {
        int oldCapacity = elements.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }
        elements = Arrays.copyOf(elements, newCapacity);
    }

    /**
     * Возвращает элемент в указанной позиции в этом списке.
     *
     * @param index индекс возвращаемого элемента.
     * @return элемент в указанной позиции в этом списке.
     * @throws IndexOutOfBoundsException если индекс выходит за пределы диапазона (index < 0 || index >= size())
     */
    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        return (E) elements[index];
    }

    /**
     * Возвращает первое вхождение указанного элемента из этого списка, если оно присутствует,
     * если элемент отсутствует в этом списке, вернет null. Вернет исключение при поиске null элемента.
     *
     * @param element элемент, который нужно найти в этом списке, если он присутствует.
     * @return первое вхождение указанного элемента из этого списка.
     * @throws NullPointerException если указанная ссылка на объект равна null.
     */
    public E get(E element) {
        Objects.requireNonNull(element);
        if (size == 0) {
            return null;
        }
        for (int i = 0; i < size; i++) {
            if (elements[i] != null && elements[i].equals(element)) {
                return element;
            }
        }
        return null;
    }

    /**
     * Удаляет первое вхождение указанного элемента из этого списка, если оно присутствует.
     * Если список не содержит элемента, он не изменяется.
     *
     * @param element элемент, который нужно удалить из этого списка, если он присутствует
     * @return true, если этот список содержит указанный элемент
     * (или, что, то же самое, если этот список изменился в результате вызова).
     */
    public boolean remove(E element) {
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) {
                    fastRemove(i);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(elements[i])) {
                    fastRemove(i);
                    return true;
                }
            }

        }
        return false;
    }

    private void fastRemove(int i) {
        int lastIndex = size - 1;
        if (lastIndex > i) {
            System.arraycopy(elements, i + 1, elements, i, lastIndex - i);
        }
        size = lastIndex;
        elements[lastIndex] = null;
    }

    /**
     * Удаляет все элементы из этого списка.
     * Список будет пуст и будет иметь емкость равной десяти, после возврата этого вызова.
     */
    public void clear() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * Заменяет элемент в указанной позиции в этом списке указанным элементом.
     *
     * @param index   индекс элемента для замены
     * @param element элемент, который будет сохранен в указанной позиции
     * @return элемент, ранее находившийся в указанной позиции
     * @throws IndexOutOfBoundsException если индекс выходит за пределы диапазона (индекс < 0 || индекс >= размер())
     */
    public E set(int index, E element) {
        Objects.checkIndex(index, size);
        E oldValue = (E) elements[index];
        elements[index] = element;
        return oldValue;
    }

    /**
     * Проверяет количество элементов в этом списке.
     *
     * @return количество элементов в этом списке.
     */
    public int size() {
        return size;
    }

    /**
     * Проверяет отсутствие элементов в этом списке.
     *
     * @return true, если этот список не содержит элементов.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Сортирует указанный диапазон этого списка объектов
     * в соответствии с указанным Comparator'ом.
     * Если fromIndex>=toIndex, диапазон для сортировки пуст.
     * Все элементы в этом диапазоне должны быть взаимно сопоставимы
     * (то есть e1.compareTo(e2) не должно вызывать исключение ClassCastException
     * для любых элементов e1 и e2 в массиве).
     * <p>
     * Для сортировки используется алгоритм быстрой сортировки.
     *
     * @param fromIndex  начальный индекс диапазона сортировки(включительно).
     * @param toIndex    конечный индекс диапазона сортировки(включительно).
     * @param comparator Comparator для определения сортировки этого списка.
     * @throws ClassCastException             если массив содержит элементы,
     *                                        которые не являются взаимно сопоставимыми (например, строки и целые числа).
     * @throws ArrayIndexOutOfBoundsException если указанные индексы выходят за границы этого списка.
     * @throws IllegalArgumentException       если fromIndex > toIndex.
     */

    @Override
    public void sort(int fromIndex, int toIndex, Comparator<? super E> comparator) {
        if (fromIndex < 0 || toIndex >= size) {
            throw new IllegalArgumentException("The specified indexes go beyond the boundaries of this list");
        }
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex there can't be more toIndex");
        }
        if (fromIndex == toIndex || elements.length <= 1) {
            return;
        }
        E[] sortArr = (E[]) elements;
        quickSort(sortArr, fromIndex, toIndex, comparator);
    }

    /**
     * Сортирует указанный диапазон этого списка объектов в порядке возрастания
     * в соответствии с естественным порядком его элементов.
     * Если fromIndex>=toIndex, диапазон для сортировки пуст.
     * Все элементы в этом диапазоне должны реализовывать интерфейс Comparable.
     * Более того, все элементы в этом диапазоне должны быть взаимно сопоставимы
     * (то есть e1.compareTo(e2) не должно вызывать исключение ClassCastException
     * для любых элементов e1 и e2 в массиве).
     * <p>
     * Для сортировки используется алгоритм быстрой сортировки.
     *
     * @param fromIndex начальный индекс диапазона сортировки(включительно).
     * @param toIndex   конечный индекс диапазона сортировки(включительно).
     * @throws ClassCastException             если массив содержит элементы,
     *                                        которые не являются взаимно сопоставимыми (например, строки и целые числа).
     * @throws ArrayIndexOutOfBoundsException если указанные индексы выходят за границы этого списка.
     * @throws IllegalArgumentException       если fromIndex > toIndex.
     */
    @Override
    public void sort(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex >= size) {
            throw new ArrayIndexOutOfBoundsException("The specified indexes go beyond the boundaries of this list");
        }
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex there can't be more toIndex");
        }
        if (fromIndex == toIndex || elements.length <= 1) {
            return;
        }
        E[] sortArr = (E[]) elements;
        quickSort(sortArr, fromIndex, toIndex);
    }

    private void quickSort(E[] sortArr, int low, int high) {

        //выбираем опорный элемент
        int middle = random.nextInt(low, high + 1);
        E border = sortArr[middle];

        //разделияем на подмассивы и меняем местами
        int i = low, j = high;
        while (i <= j) {
            while (((Comparable<E>) sortArr[i]).compareTo(border) < 0) i++;
            while (((Comparable<E>) sortArr[j]).compareTo(border) > 0) j--;
            if (i <= j) {
                E swap = sortArr[i];
                sortArr[i] = sortArr[j];
                sortArr[j] = swap;
                i++;
                j--;
            }
        }

        //рекурсия для сортировки левой и правой части
        if (low < j) quickSort(sortArr, low, j);
        if (high > i) quickSort(sortArr, i, high);
    }

    private void quickSort(E[] sortArr, int low, int high, Comparator<? super E> comparator) {

        //выбираем опорный элемент
        int middle = random.nextInt(low, high + 1);
        E border = sortArr[middle];

        //разделияем на подмассивы и меняем местами
        int i = low, j = high;
        while (i <= j) {
            while (comparator.compare(sortArr[i], border) < 0) i++;
            while (comparator.compare(sortArr[j], border) > 0) j--;
            if (i <= j) {
                E swap = sortArr[i];
                sortArr[i] = sortArr[j];
                sortArr[j] = swap;
                i++;
                j--;
            }
        }

        //рекурсия для сортировки левой и правой части
        if (low < j) quickSort(sortArr, low, j, comparator);
        if (high > i) quickSort(sortArr, i, high, comparator);
    }

    /**
     * Сравнивает указанный объект с этим списком на предмет равенства.
     * Возвращает true тогда и только тогда, когда указанный объект также является списком,
     * оба списка имеют одинаковый размер и все соответствующие пары элементов в двух списках равны.
     * (Два элемента e1 и e2 равны, если Objects.equals(e1, e2).)
     * Другими словами, два списка считаются равными, если они содержат одни и те же элементы в одном и том же порядке.
     *
     * @param o объект для сравнения
     * @return true, если переданный объект и этот список равны.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyArrayList<?> that = (MyArrayList<?>) o;
        return size == that.size && Arrays.equals(elements, that.elements);
    }

    /**
     * Возвращает значение хэш-кода для этого списка.
     *
     * @return значение хэш-кода для этого списка.
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(elements);
        return result;
    }

    /**
     * Возвращает строковое представление этой коллекции.
     * Строковое представление состоит из списка элементов коллекции в том порядке, в котором они хранятся в списке,
     * заключенного в фигурные скобки («{}»). Соседние элементы разделяются символами ", " (запятая и пробел).
     * Элементы преобразуются в строки, как с помощью StringBuilder.
     *
     * @return строковое представление этой коллекции.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ ");
        for (int i = 0; i < size; i++) {
            if (i < size - 1) {
                stringBuilder.append(elements[i]).append(", ");
            } else {
                stringBuilder.append(elements[i]);
            }
        }
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}