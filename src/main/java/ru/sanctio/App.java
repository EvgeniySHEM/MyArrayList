package ru.sanctio;

import ru.sanctio.dataStructures.myList.MyArrayList;
import ru.sanctio.dataStructures.myList.MyList;

public class App {

    public static void main(String[] args) {
        MyList<Integer> list = new MyArrayList<>(5);
        list.add(1);
        list.add(4);
        list.add(2);
        list.add(5);
        list.add(2);
        System.out.println(list);




    }
}
