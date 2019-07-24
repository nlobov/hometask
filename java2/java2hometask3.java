package java2;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

/*
 * Домашнее задание курса java2 по уроку 3 "Коллекции"
 * dated Jul 24, 2019
 *
 * 1. Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся).
 * Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем). Посчитать сколько раз встречается каждое слово.
 *
 * 2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров.
 * В этот телефонный справочник с помощью метода add() можно добавлять записи. С помощью метода get() искать номер телефона по фамилии.
 * Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев),
 * тогда при запросе такой фамилии должны выводиться все телефоны.
 */

public class java2hometask3 {

    private final static String[] surnames = {"Сидоров","Петров","Иванов","Иванов","Иванов","Котов","Жуков","Жуков","Сидоров","Панкратов","Ветров"};

    public static void main(String[] args) {
//Набор слов
        TreeSet surnames2 = getWords(surnames);
        System.out.println("Вывод всех уникальных слов: "+surnames2);
        System.out.println("Вывод количества встречаемости слов: "+getCalcWords(surnames));
//Телефонный справочник
        PhoneBook testBook = new PhoneBook();
//Ввод данных(фамилии+телефон) в справочник из набора слов 1-го задания
        for (int i = 0; i < surnames.length ; i++) {
            testBook.add(surnames[i],"+7901"+(int)Math.round(10000000*Math.random()));
        }
//Вывод телефонов по фамилии
        for (int i = 0; i < surnames2.size(); i++) {
            System.out.println((String) surnames2.toArray()[i]+", все телефоны: "+ testBook.getPhone((String) surnames2.toArray()[i]));
        }

        try{
            System.out.println("Федоров, телефоны:" + testBook.getPhone("Федоров"));}
        catch (RuntimeException e){
            System.out.println("произошел RuntimeException: "+e.getMessage());
        }
    }

    private static TreeSet<String> getWords(String[] words){
        return new TreeSet<>(Arrays.asList(words));
    }

    private static HashMap<String, Integer> getCalcWords(String[] words){
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if(map.containsKey(word)){
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, 1);
            }
        }
        return map;
    }
}

class Person {
    public String surname;
    public String phone;

    public Person(String surname, String phone){
       this.surname = surname;
        this.phone = phone;
    }
}

class PhoneBook {

    private final HashMap<String, ArrayList<Person>> records = new HashMap<>();

    public void add(String surname, String phone){
        if(records.containsKey(surname)){
            ArrayList<Person> persons = records.get(surname);
            persons.add(new Person(surname, phone));
        } else {
            ArrayList<Person> persons = new ArrayList<>();
            persons.add(new Person(surname, phone));
            records.put(surname, persons);
        }
    }

    public ArrayList<String> getPhone(String surname){
        if(!records.containsKey(surname)) {
            throw new RuntimeException("Фамилии "+ surname +" нет в книге!");
        //return null;
        }
        ArrayList<Person> persons = records.get(surname);
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < persons.size(); i++) {
            result.add(persons.get(i).phone);
        }
        return result;
    }

}
