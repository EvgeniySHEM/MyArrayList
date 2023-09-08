package ru.sanctio.dataStructures.myList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MyArrayListTest {
    private MyList<Integer> list;
    private MyArrayList<Object> objectMyArrayList;

    @BeforeEach
    void createNewList() {
        list = new MyArrayList<>();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(2);
        list.add(8);
        list.add(5);
        list.add(4);

        objectMyArrayList = new MyArrayList<>();
    }

    @Test
    void elementShouldBeAddedToTheEnd() {
        list.add(5);

        assertEquals(5, list.get(list.size() - 1));
    }

    @Test
    void elementsShouldBeAddedToTheEnd() {
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
    }

    @Test
    void capacityShouldBeIncreaseByAdd() {
        for (int i = 0; i < 1000; i++) {
            Object[] elements = (Object[]) ReflectionTestUtils.getField(list, "elements");
            int capacity = elements.length;
            if(list.size() == capacity) {
                list.add(i);
                Object[] elements2 = (Object[]) ReflectionTestUtils.getField(list, "elements");
                capacity = capacity + (capacity >> 1);
                assertEquals(capacity, elements2.length);
                continue;
            }
            list.add(i);
        }
    }

    @Test
    void shouldThrowAnArrayIndexOutOfBoundsExceptionWhenTheSizeIsLargerIntegerMaxValue() {
        ReflectionTestUtils.setField(list, "size", Integer.MAX_VALUE);
        String message = "Capacity there can't be more Integer.MAX_VALUE";

        ArrayIndexOutOfBoundsException exception =
                assertThrows(ArrayIndexOutOfBoundsException.class, () -> list.add(1));
        assertEquals(message, exception.getMessage());
    }

    @Test
    void elementShouldBeAddedToTheSpecifiedIndex() {
        list.add(0, 15);

        assertEquals(15, list.get(0));
    }

    @Test
    void itemsShouldBeAddedToTheSpecifiedIndex() {
        MyArrayList<Object> objectMyArrayList = new MyArrayList<>();
        for (int i = 0; i < 1000; i++) {
            objectMyArrayList.add(0, new Object());
        }
    }

    @Test
    void elementsShouldBeAddedToTheSpecifiedIndex() {
        for (int i = 0; i < 10000; i++) {
            objectMyArrayList.add(0, new Object());
        }
    }

    @Test
    void shouldThrowAnIndexOutOfBoundsExceptionWhenIndexLessThanZero() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, 5));
    }

    @Test
    void shouldThrowAnIndexOutOfBoundsExceptionWhenIndexMoreSize() {
        int size = list.size();

        assertThrows(IndexOutOfBoundsException.class, () -> list.add(size + 1, 5));
    }

    @Test
    void shouldBeGetElementByIndex() {
        assertEquals(2, list.get(4));
    }

    @Test
    void shouldBeGetElementsByIndex() {
        for (int i = 0; i < 10000; i++) {
            objectMyArrayList.add(new Object());
            objectMyArrayList.get(i);
        }
        for (int i = 9999; i >= 0; i--) {
            objectMyArrayList.get(i);
        }
    }

    @Test
    void ShouldBeReturnFirstOccurrenceSpecifiedElement() {
        Integer value = 2;

        assertEquals(2, list.get(value));
    }

    @Test
    void ShouldBeReturnFirstOccurrenceSpecifiedElements() {
        for (int i = 0; i < 10000; i++) {
            list.add(i);
            assertEquals(Integer.valueOf(i), list.get(Integer.valueOf(i)));
        }
    }

    @Test
    void ShouldBeReturnNullIfThereIsNoElement() {
        Integer value = 3;
        assertNull(list.get(value));
    }

    @Test
    void shouldThrowAnNullPointerExceptionWhenSpecifiedElementIsNull() {
        assertThrows(NullPointerException.class, () -> list.get(null));
    }

    @Test
    void shouldThrowAnIndexOutOfBoundsExceptionWhenIndexEqualOrMoreSize() {
        int size = list.size();

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(size));
    }

    @Test
    void shouldBeDeletedTheFirstOccurrenceOfTheSpecifiedElement() {
        assertTrue(list.remove(2));
    }

    @Test
    void shouldBeDeletedTheFirstOccurrenceOfTheSpecifiedElements() {
        for (int i = 9; i < 10000; i++) {
            list.add(i);
        }

        for (int i = 9; i < 10000; i++) {
            assertTrue(list.remove(Integer.valueOf(i)));
        }
    }

    @Test
    void shouldBeDeletedTheFirstOccurrenceOfTheNull() {
        for (int i = 0; i < 10000; i++) {
            objectMyArrayList.add(null);
        }

        for (int i = 0; i < 10000; i++) {
            assertTrue(objectMyArrayList.remove(null));
        }

        assertEquals(0, objectMyArrayList.size());
    }

    @Test
    void sizeShouldDecreaseWhenElementIsDeleted() {
        int size = list.size();
        list.remove(2);

        assertEquals(size - 1, list.size());
    }

    @Test
    void sizeShouldDecreaseWhenElementsIsDeleted() {
        for (int i = 0; i < 10000; i++) {
            objectMyArrayList.add(i);
        }

        for (int i = 0; i < 10000; i++) {
            int size = objectMyArrayList.size();
            objectMyArrayList.remove(i);
            assertEquals(size - 1, objectMyArrayList.size());
        }
    }

    @Test
    void shouldBeReturnFalseIfMissingOfTheSpecifiedElement() {
        assertFalse(list.remove(15));
    }

    @Test
    void allElementsShouldBeRemoved() {
        list.clear();

        assertEquals(0, list.size());
    }

    @Test
    void shouldBeReplaceElementsInTheSpecifiedPosition() {
        for (int i = 0; i < 10000; i++) {
            objectMyArrayList.add(i);
        }

        for (int i = 0; i < 10000; i++) {
            assertEquals(i, objectMyArrayList.set(i, i + 2));
            assertEquals(i + 2, objectMyArrayList.get(i));
        }
    }

    @Test
    void shouldBeReplaceTheElementInTheSpecifiedPosition() {
        list.set(0, 5);

        assertEquals(5, list.get(0));
    }

    @Test
    void shouldThrowAnIndexOutOfBoundsExceptionWhenSetIndexEqualOrMoreSize() {
        int size = list.size();

        assertThrows(IndexOutOfBoundsException.class, () -> list.set(size, 4));
    }

    @Test
    void shouldThrowAnIndexOutOfBoundsExceptionWhenSetIndexLessZero() {

        assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, 4));
    }

    @Test
    void shouldBeReturnTheSizeOfTheList() {
        for (int i = 0; i < 10000; i++) {
            assertEquals(i, objectMyArrayList.size());
            objectMyArrayList.add(i);
        }
    }

    @Test
    void shouldBeReturnTrueIfListIsEmptyAfterCleaning() {
        list.clear();

        assertTrue(list.isEmpty());
    }

    @Test
    void shouldBeReturnTrueIfListIsEmptyByInitialCapacityEqualZero() {
        MyList<Object> myList = new MyArrayList<>(0);

        assertTrue(myList.isEmpty());
    }

    @Test
    void shouldBeReturnFalseIfListIsNotEmpty() {
        for (int i = 0; i < 10000; i++) {
            objectMyArrayList.add(i);
            assertFalse(objectMyArrayList.isEmpty());
        }
    }

    @Test
    void listShouldBeSortedByNonDecreasing() {
        MyList<Integer> list2 = new MyArrayList<>();
        list2.add(1);
        list2.add(1);
        list2.add(1);
        list2.add(1);
        list2.add(2);
        list2.add(4);
        list2.add(5);
        list2.add(8);
        list.sort(0, list.size() - 1);

        assertEquals(list2, list);
    }

    @Test
    void listsShouldBeSortedByNonDecreasing() {
        MyList<Integer> list2 = new MyArrayList<>();
        for (int i = 1000; i >= -100; i--) {
            list2.add(i);
            list2.sort(0, list2.size() - 1);
        }

        MyList<Integer> list1 = new MyArrayList<>();
        for (int i = -100; i <= 1000; i++) {
            list1.add(i);
        }

        assertEquals(list1, list2);
    }

    @Test
    void listShouldBeSortedBySpecifiedComparator() {
        MyList<Integer> list2 = new MyArrayList<>();
        list2.add(8);
        list2.add(5);
        list2.add(4);
        list2.add(2);
        list2.add(1);
        list2.add(1);
        list2.add(1);
        list2.add(1);
        list.sort(0, list.size() - 1, (e1, e2) -> e2.compareTo(e1));

        assertEquals(list2, list);
    }

    @Test
    void listsShouldBeSortedBySpecifiedComparator() {
        MyList<Integer> list2 = new MyArrayList<>();
        for (int i = -100; i < 1000; i++) {
            list2.add(i);
            list2.sort(0, list2.size() - 1, (e1, e2) -> e2.compareTo(e1));
        }

        MyList<Integer> list1 = new MyArrayList<>();
        for (int i = 999; i >= -100; i--) {
            list1.add(i);
        }

        assertEquals(list1, list2);
    }
}