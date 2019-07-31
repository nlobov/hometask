package geekbrains.algoritms.lesson8;

/* Домашнее задание курса Алгоритмы по уроку 8 "Хеш-таблицы"
 * dated Jul 31, 2019
 *  1. Создать программу, реализующую метод цепочек.
 */

import java.util.LinkedList;

public class algHomeTask8 {
    public static void main(String[] args) {
        System.out.println("Домашнее задание 8");

        MyChainingHashMapHome <Integer, String> hashMapNew = new MyChainingHashMapHome<>(5);
        hashMapNew.put(2,"Цифра 123");

        for (int i = 0; i < 50; i++) {
            hashMapNew.put((int)(Math.random()*100),"Цифра "+i);
        }

        System.out.println(hashMapNew.get(2));
        System.out.println(hashMapNew);
    }

}

class MyChainingHashMapHome <Key, Value> {
    private int M ;
    private int size = 0;

    private LinkedList<Node>[] st;

    public MyChainingHashMapHome(int M) {
        this.M = M;
        st = new LinkedList[M];
        for (int i = 0; i < st.length; i++) {
            st[i] = new LinkedList<>();
        }
    }

    private class Node {
        private Key key;
        private Value value;

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }


    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("null key");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value can not be null.");
        }
        int i = hash(key);
        for (Node node : st[i]) {
            if (key.equals(node.key)) {
                node.value = value;
                return;
            }
        }
        st[i].addLast(new Node(key, value));
        size++;
    }

    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("null key");
        }
        int i = hash(key);
        for (Node node : st[i]) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < M; i++) {
            for (Node node : st[i]) {
                s += node.key + " ";
            }
            s += "\n";
        }

        return "{\n" +
                s +
                '}';
    }
}


