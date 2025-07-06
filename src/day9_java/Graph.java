package day9_java;

class Graph {

    class Edge {
        int src;
        int dest;
    }
    int vertices;
    int edges;
    Edge[] edge;

    Graph(int vertices, int edges) {
        this.vertices = vertices;
        this.edges = edges;

        edge = new Edge[edges];
        for(int i = 0; i < edges; i++) {
            edge[i] = new Edge();
        }
    }

    public static void main(String[] args) {
        int noVertices = 5;
        int noEdges = 8;
        Graph first = new Graph(noVertices, noEdges);

        first.edge[0].src = 1;
        first.edge[0].dest = 2;
        first.edge[1].src = 1;
        first.edge[1].dest = 3;
        first.edge[2].src = 1;
        first.edge[2].dest = 4;
        first.edge[3].src = 2;
        first.edge[3].dest = 4;
        first.edge[4].src = 2;
        first.edge[4].dest = 5;
        first.edge[5].src = 3;
        first.edge[5].dest = 4;
        first.edge[6].src = 3;
        first.edge[6].dest = 5;
        first.edge[7].src = 4;
        first.edge[7].dest = 5;

        for(int i =0; i < noEdges; i++) {
            System.out.println(first.edge[i].src+ " - " + first.edge[i].dest);
        }
    }
}