package geekbrains.algoritms.lesson7;

/* Домашнее задание курса Алгоритмы по уроку 7 "Графы"
 * dated Jul 28, 2019
 *  1. Реализовать программу, в которой задается граф из 10 вершин. Задать ребра и найти кратчайший путь с помощью поиска в ширину.
 */

public class algHomeTask7 {

    public static void main(String[] args) {
        System.out.println("Домашнее задание 7");
        Graph graph4 = new Graph(10);

        graph4.addEdge(0, 2);
        graph4.addEdge(0, 5);
        graph4.addEdge(2, 3);
        graph4.addEdge(3, 4);
        graph4.addEdge(4, 5);
        graph4.addEdge(4, 7);
        graph4.addEdge(5, 6);
        graph4.addEdge(6, 7);
        graph4.addEdge(6, 8);
        graph4.addEdge(8, 9);
        graph4.addEdge(9, 6);

        BreadthFirstSearch bfs = new BreadthFirstSearch(graph4, 0);
        System.out.println(bfs.hasPathTo(9));
        if (bfs.hasPathTo(9)) {
            System.out.println(bfs.pathTo(9));
            System.out.println(bfs.distTo(9));
        }
    }
}
