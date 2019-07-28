package alg;

/*
 * Домашнее задание курса Алгоритмы по уроку 6 "Деревья"
 * dated Jul 24, 2019
 *
  *  1. Создать и запустить программу для построения двоичного дерева.
  *  В цикле построить двадцать деревьев с глубиной в 6 уровней.
  *  Данные, которыми необходимо заполнить узлы деревьев, представляются в виде чисел типа int.
  *  Число, которое попадает в узел, должно генерироваться случайным образом в диапазоне от -100 до 100.
  *
  *  2. Проанализировать, какой процент созданных деревьев являются несбалансированными.
 */
public class algHomeTask6 {


    public static void main(String[] args) {
        System.out.println("Домашнее задание 6");

//        MyTreeMap <Character, Integer> treeMap = new MyTreeMap<>();
//        treeMap.put('S',1);
//        treeMap.put('E',2);
//        treeMap.put('A',3);
//        treeMap.put('R',4);
//        treeMap.put('C',5);
//        treeMap.put('H',6);
//        treeMap.put('X',7);
//        treeMap.put('M',8);

        int bal = 0;
        int disbal = 0;

        for (int i = 0; i < 100; i++){

            MyTreeMap<Integer, Integer> treeMapNew = new MyTreeMap<>();

            for (int j = 0; j < 64; j++) {
                int k = (int) (100 - Math.round(200*Math.random()));
                treeMapNew.put(k, k);
            }
            if (treeMapNew.MyIsBalanced()){
                bal++;
            } else {
                disbal++;
            }
//            System.out.print( bal + " balanced ");
//            System.out.println( disbal + " disbalansed");
        }
        System.out.println("Деревьев сбалансированных: " +bal);
        System.out.println("Деревьев несбалансированных: " + disbal);
        // посчитать процентное соотношение несбалансированных деревьев
        System.out.println("Несбалансировано: "+(double)disbal/(bal+disbal)*100 +" %");



    }


}
