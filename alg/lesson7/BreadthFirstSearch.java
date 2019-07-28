package geekbrains.algoritms.lesson7;

import java.util.LinkedList;

public class BreadthFirstSearch {
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;
    private int source;
    private final int inf = Integer.MAX_VALUE;

    public BreadthFirstSearch(Graph g, int source) {
        if (source < 0 || source > g.vertexCount() - 1) {
            throw new IndexOutOfBoundsException("Wrong vertex number.");
        }
        this.source = source;
        edgeTo = new int[g.vertexCount()];
        marked = new boolean[g.vertexCount()];
        distTo = new int[g.vertexCount()];
        for (int i = 0; i < g.vertexCount(); i++) {
            distTo[i] = inf;
        }
        bfs(g, source);
    }

    private void bfs(Graph g, int source) {
        LinkedList<Integer> queue = new LinkedList<>();
        queue.addLast(source);
        marked[source] = true;
        distTo[source] = 0;

        while (!queue.isEmpty()) {
            int currentVertex = queue.removeFirst();
            for (int w :
                    g.adjList(currentVertex)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = currentVertex;
                    distTo[w] = distTo[currentVertex] + 1;
                    queue.addLast(w);
                }
            }
        }
    }

    public boolean hasPathTo(int dist) {
        if (dist < 0 || dist > marked.length - 1) {
            throw new IndexOutOfBoundsException("Wrong vertex number.");
        }
        return marked[dist];
    }

    public LinkedList<Integer> pathTo(int dist) {
        if (!hasPathTo(dist)) {
            return null;
        }

        LinkedList<Integer> stack = new LinkedList<>();

        int currentVertex = dist;
        while (currentVertex != source) {
            stack.push(currentVertex);
            currentVertex = edgeTo[currentVertex];
        }
        return stack;
    }

    public int distTo(int dist) {
        if (dist < 0 || dist > marked.length - 1) {
            throw new IndexOutOfBoundsException("Wrong vertex number.");
        }
        return distTo[dist];
    }

}
