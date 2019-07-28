package geekbrains.algoritms.lesson7;

public class Program {
    public static void main(String[] args) {
        System.out.println("Занятие 7");
        Graph graph1 = new Graph(6);

        graph1.addEdge(1, 3);
        graph1.addEdge(1, 2);
        graph1.addEdge(2, 4);
        graph1.addEdge(3, 2);
        graph1.addEdge(3, 5);
        graph1.addEdge(4, 5);
        graph1.addEdge(1, 0);
        graph1.addEdge(4, 0);
        graph1.addEdge(1, 5);

        System.out.println("Список смежности для вершины 5:" + graph1.adjList(5));

        Graph graph2 = new Graph(13);

        graph2.addEdge(0, 6);
        graph2.addEdge(0, 1);
        graph2.addEdge(0, 2);
        graph2.addEdge(5, 3);
        graph2.addEdge(5, 4);
        graph2.addEdge(5, 0);
        graph2.addEdge(4, 6);
        graph2.addEdge(4, 3);

        graph2.addEdge(7, 8);

        graph2.addEdge(9, 10);
        graph2.addEdge(9, 11);
        graph2.addEdge(9, 12);
        graph2.addEdge(11, 12);
        System.out.println("Список смежности для вершины 5 графа graph2:" + graph2.adjList(5));

        DepthFirstPaths dfs = new DepthFirstPaths(graph2, 0);
        System.out.println(dfs.hasPathTo(5));
        if (dfs.hasPathTo(5)) {
            System.out.println(dfs.pathTo(5));
        }
        System.out.println(dfs.hasPathTo(9));

        Graph graph3 = new Graph(13);

        graph3.addEdge(0, 2);
        graph3.addEdge(0, 1);
        graph3.addEdge(1, 2);
        graph3.addEdge(3, 5);
        graph3.addEdge(3, 4);
        graph3.addEdge(3, 2);
        graph3.addEdge(4, 2);
        graph3.addEdge(5, 0);

        BreadthFirstSearch bfs = new BreadthFirstSearch(graph3, 0);
        System.out.println(bfs.hasPathTo(3));
        if (bfs.hasPathTo(3)) {
            System.out.println(bfs.pathTo(3));
            System.out.println(bfs.distTo(3));
        }



    }
}
