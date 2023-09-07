package ru.sanctio.dataStructures.myList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MyArrayListTest {
    private MyList<Integer> list;

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
    }

    @Test
    void elementShouldBeAddedToTheEnd() {
        for (int i = 0; i < 10; i++) {
            list.add(i);
            assertEquals(i, list.get(list.size() - 1));
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
    void shouldThrowAnIndexOutOfBoundsExceptionWhenIndexLessThanZero() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, 5));
    }

    @Test
    void shouldThrowAnIndexOutOfBoundsExceptionWhenIndexMoreSize() {
        int size = list.size();

        assertThrows(IndexOutOfBoundsException.class, () -> list.add(size, 5));
    }

    @Test
    void shouldBeGetElementByIndex() {
        assertEquals(2, list.get(4));
    }

    @Test
    void ShouldBeReturnFirstOccurrenceSpecifiedElement() {
        Integer value = 2;
        assertEquals(2, list.get(value));
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
    void shouldBeDeletedTheFirstOccurrenceOfTheSpecifiedElement() {
        assertTrue(list.remove(2));
    }

    @Test
    void sizeShouldDecreaseWhenElementIsDeleted() {
        int size = list.size();
        list.remove(2);

        assertEquals(size - 1, list.size());
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
    void shouldBeReplaceTheElementInTheSpecifiedPosition() {
        list.set(0, 5);

        assertEquals(5, list.get(0));
    }

    @Test
    void shouldBeReturnTheSizeOfTheList() {
        assertEquals(8, list.size());
    }

    @Test
    void shouldBeReturnTrueIfListIsEmpty() {
        list.clear();

        assertTrue(list.isEmpty());
    }

    @Test
    void shouldBeReturnFalseIfListIsNotEmpty() {
        assertFalse(list.isEmpty());
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
}