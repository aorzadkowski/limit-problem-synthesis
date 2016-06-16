package graph;

public class WeightedEdge<T> implements Comparable<T>{
	public T start;
	public T end;
	public int weight;
	
	public WeightedEdge(T start, T end, int weight) {
		this.start = start;
		this.end = end;
		this.weight = weight;
	}
	
	public void increment() {
		weight++;
	}
	
	public String toString() {
		return "{ " + start.toString() + ", " + end.toString() + ", " + weight + " }";
	}

	@Override
	public int compareTo(T o) {
		// TODO Auto-generated method stub
		return ((Comparable<T>) start).compareTo(((WeightedEdge<T>) o).start);
	}
}