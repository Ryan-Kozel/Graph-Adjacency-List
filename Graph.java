import java.util.*;

public class Graph {
	//Ryan Kozel, ryan.kozel@sjsu.edu, 017039009
	public VertexList vertices;
	public int time;

	public Graph(){
		vertices = new VertexList();
	}

	public static Graph readFromString(String input) {
		Graph g = new Graph();
		//Split string into pairs that show which nodes points to which
		//Example: after splitting, "1,3" shows that 1 points to 3
		String[] edges = input.split(";");
		for(int i = 0; i < edges.length; i++) {
			//Split pairs into single vertex values
			String[] parts = edges[i].split(",");
			//
			if(parts.length == 2){
				int v1 = Integer.parseInt(parts[0]); //parse first id value (source vertex)
				int v2 = Integer.parseInt(parts[1]); //parse second id value (destination vertex)
				//Since we know v1 points to v2, we can call addEdge(v1, v2)
				//The graph does not have these vertices, but they will be added with an appropriate edge
				g.addEdge(v1, v2);
			}else if(parts.length == 1){
				//This vertex points to no other vertexes, so add it to the graph
				int v = Integer.parseInt(parts[0]);
				g.addVertex(new Vertex(v));
			}
		}
		return g;
	}

	public void addEdge(int i, int j) {
		//Create new vertices if they do not already exist
		Vertex v1 = vertices.findOrMake(i);
		Vertex v2 = vertices.findOrMake(j);

		//Add second vertex to LinkedList
		LinkedList<Vertex> l = new LinkedList<Vertex>();
		l.add(v2);
		v2.inDegree = v2.inDegree + 1; //increment in-degree
		//Add LinkedList to the list of adjacent vertices for vertex with id i
		v1.adj.arr.add(l);
		v1.outDegree = v1.outDegree + 1; //increment out-degree
	}

	public void addVertex(Vertex v){
		//Initialize LinkedList of Vertices for adjacency list (vertices)
		LinkedList<Vertex> l = new LinkedList<Vertex>();
		//Add Vertex to end of LinkedList
		l.add(v);
		//Add Linked List to next index of ArrayList
		vertices.arr.add(l);

	}
	
	public String writeToString() {
		StringBuilder sb = new StringBuilder();
		ArrayList<LinkedList<Vertex>> adjList = vertices.arr;
		for(int i = 0; i < adjList.size(); i++){
			//Get the first vertex of each LinkedList which will represent each vertex in the graph
			Vertex v = adjList.get(i).get(0);

			if(v.adj.arr.size() > 0){
				sb.append(v.id); //append this vertex value to StringBuilder
				sb.append(":");
				for(int j = 0; j < v.adj.arr.size(); j++){
					//append the
					sb.append(v.adj.arr.get(j).get(0).id);

					//print "," between each child of v except the last one
					if(j < v.adj.arr.size() - 1){
						sb.append(",");
					}
				}
				sb.append(";");
			}
		}
		//remove final ";"
		sb.delete(sb.length()-1, sb.length());
		return sb.toString();
	}

	public Graph BFS(Vertex s){
		Graph g = new Graph();

		//Set first node to be visited
		s.color = 'g';
		s.dist = 0;
		s.pred = null;
		//Add starting vertex to queue
		Queue<Vertex> q = new LinkedList<Vertex>();
		q.add(s);

		while(!q.isEmpty()){
			//While the queue has vertexes in it, add current vertex to graph and remove from queue
			Vertex u = q.remove();
			g.addVertex(u);
			//Go through the adjacency list for the vertex u
			VertexList uAdj = u.adj;
			for(int i = 0; i < uAdj.arr.size(); i++){
				Vertex v = uAdj.arr.get(i).get(0);
				//If the next node in the adjacency list for u has not been visited
				//mark it visited, update distance and its predecessor
				//then add that vertex to the queue
				if(v.color == 'w'){
					v.color = 'g';
					v.dist = u.dist + 1;
					v.pred = u;
					q.add(v);
				}
				//Once done with u's adjacency list, mark it finished
				u.color = 'b';
			}
		}
		return g;
	}

	public Graph recDFS(){
		Graph g = new Graph();
		time = 0;

		for(int i = 0; i < vertices.arr.size(); i++){
			//If the current node has not been visited, look at its neighbors
			if(vertices.arr.get(i).get(0).color == 'w'){
				DFSVisit(vertices.arr.get(i).get(0) , g);
			}
		}
		return g;
	}

	private void DFSVisit(Vertex u, Graph g){
		//Mark current node as visited
		u.color = 'g';
		time = time + 1;
		u.dist = time;
		g.addVertex(u); //add current node to graph

		for(int i = 0; i < u.adj.arr.size(); i++){
			Vertex v = u.adj.arr.get(i).get(0);
			//If its adjacent nodes are not visited, recursively look at that node's neighbors
			if(v.color == 'w'){
				v.pred = u;
				DFSVisit(v, g);
			}
			u.color = 'b';
			time = time + 1;
			u.f = time;
		}
	}

	public Graph itDFS(){
		Graph g = new Graph();

		Vertex r = vertices.arr.get(0).get(0);
		//always start with the first node in adjacency list and mark it visited
		r.color = 'g';
		r.dist = 0;
		r.pred = null;
		//Initialize stack s and push the first vertex on to it
		Stack<Vertex> s = new Stack<Vertex>();
		s.push(r);

		while(!s.isEmpty()){
			//While the stack is not empty, pop the top vertex and get its adj list
			Vertex u = s.pop();
			VertexList adjList = u.adj;

			for(int i = 0; i < adjList.arr.size(); i++){
				Vertex v = adjList.arr.get(i).get(0);
				//For each vertex in u's adj list, mark it visted and push it on to the stack
				if(v.color == 'w'){
					v.pred = u;
					v.color = 'g';
					v.dist = u.dist + 1;
					s.push(v);
				}
			}
			//After each vertex in u's adj list is visted,
			//mark u finished and it to the graph
			u.color = 'b';
			g.addVertex(u);
		}
		return g;
	}

	public ArrayList<Integer> topSort(){
		ArrayList<Integer> A = new ArrayList<Integer>();
		Queue<Vertex> q = new LinkedList<Vertex>();

		Map<Vertex, Integer> inDeg = new HashMap<Vertex, Integer>();
		for(int i = 0; i < vertices.arr.size(); i++) {
			//Fill map of In-degree with each vertex with a value of 0
			Vertex curr = vertices.arr.get(i).get(0);
			inDeg.put(curr, curr.inDegree);
		}

		//Following Kahn's Algorithm:

		//If a vertex has In-degree of 0, add to queue
		for (Vertex v: inDeg.keySet()) {
			if(v.inDegree == 0){
				q.add(v);
			}
		}

		while(!q.isEmpty()){
			//While queue is not empty, grab first vertex and add it to our array
			Vertex v = q.poll();
			A.add(v.id);

			for(int i = 0; i < v.adj.arr.size(); i++){
				//Check each neighbor of vertex v, decrement its in-degree
				//and update our map with new in-degree values
				Vertex neighbor = v.adj.arr.get(i).get(0);
				neighbor.inDegree = neighbor.inDegree - 1;
				inDeg.put(neighbor, neighbor.inDegree);

				//if a neighbor now has 0 in-degree, add it to queue
				if(neighbor.inDegree == 0){
					q.add(neighbor);
				}
			}
		}
		//If we found 1 or more vertexes with 0 in-degree, return our array
		if(A.size() > 0){
			return A;
		} else{
			//Otherwise, it is a cycle, so return null
			return null;
		}
	}

}
