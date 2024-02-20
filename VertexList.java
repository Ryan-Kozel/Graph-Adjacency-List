import java.util.ArrayList;
import java.util.LinkedList;

public class VertexList {
	//Ryan Kozel, ryan.kozel@sjsu.edu, 017039009
	ArrayList<LinkedList<Vertex>> arr;

	public VertexList(){
		arr = new ArrayList<LinkedList<Vertex>>();
	}

	public Vertex findOrMake(int i) {
		//Traverse the adjacency list and look for a node with id i
		for(int k = 0; k < arr.size(); k++){
			if(arr.get(k).get(0).id == i){
				return arr.get(k).get(0);
			}
		}
		//If we do not find a node in the adjacency list with id i, add it to the end
		//Follows exact same method as addVertex in Graph Class
		LinkedList<Vertex> l = new LinkedList<Vertex>();
		Vertex v = new Vertex(i);
		l.add(v);
		arr.add(l);
		return v;
	}

}
