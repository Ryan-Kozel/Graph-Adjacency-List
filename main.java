public class main {
    //Ryan Kozel, ryan.kozel@sjsu.edu, 017039009
    public static void main(String[] args) {
        String border = "----------------------------------------------------------------";
        String test = "1,2;1,3;2,4;2,5;3,6;3,7;4,8;5,8;6,9;7,9;8,10;9,10";


        //Graph to String Method
        Graph a = Graph.readFromString(test);
        System.out.println(border);
        System.out.println("Graph to String: " + a.writeToString());

        System.out.println(border);

        //BFS on Graph
        Graph b = a.BFS(a.vertices.arr.get(0).get(0));
        System.out.print("BFS on Graph: ");
        for(int i = 0; i < b.vertices.arr.size(); i++){
            System.out.print(b.vertices.arr.get(i).get(0).id + " ");
        }

        System.out.println("\n" + border);

        //Iterative DFS on graph
        Graph c = Graph.readFromString(test);
        Graph c1 = c.itDFS();
        System.out.print("Iterative DFS on Graph: ");
        for(int i = 0; i < c1.vertices.arr.size(); i++){
            System.out.print(c1.vertices.arr.get(i).get(0).id + " ");
        }

        System.out.println("\n" + border);

        //Recursive DFS on graph
        Graph d = Graph.readFromString(test);
        Graph d1 = d.recDFS();
        System.out.print("Recursive DFS on graph: ");
        for(int i = 0; i < d1.vertices.arr.size(); i++){
            System.out.print(d1.vertices.arr.get(i).get(0).id + " ");
        }
        System.out.println();

        System.out.println(border);

        //Topological Sort of graph
        Graph e = Graph.readFromString(test);
        System.out.print("Topo sort of graph: ");
        for(int n: e.topSort()){
            System.out.print(n + " ");
        }
        System.out.print("\n" + border);
    }
}
