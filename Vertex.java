
public class Vertex {
	//Ryan Kozel, ryan.kozel@sjsu.edu, 017039009
	public int id;
	public char color;
	public Vertex pred;
	public int dist;
	public VertexList adj;
	public int f;
	public int inDegree;
	public int outDegree;

	public Vertex(int k){
		id = k;
		color = 'w';
		pred = null;
		dist = 0;
		adj = new VertexList();
		f = 0;
		inDegree = 0;
		outDegree = 0;
	}
}


