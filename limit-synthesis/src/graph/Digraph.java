package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Digraph<T> {
	ArrayList<WeightedEdge<T>> adjacencyList;
	
	public Digraph() {
		adjacencyList = new ArrayList<>();
	}
	
	public void addEdge(WeightedEdge<T> newEdge) {
		adjacencyList.add(newEdge);
	}
	
	public ArrayList<WeightedEdge<T>> getAllOutEdgesForVertex(T vertex) {
		ArrayList<WeightedEdge<T>> temp = new ArrayList<>();
		
		for (WeightedEdge<T> edge : adjacencyList) {
			if (edge.start.equals(vertex)) 
				temp.add(edge);
		}
		
		return temp;
	}
	
	public ArrayList<T> getAllVertices() {
		ArrayList<T> temp = new ArrayList<>();
		
		for (int i = 0; i < adjacencyList.size(); i++) {
			if (!temp.contains(adjacencyList.get(i).start))
				temp.add(adjacencyList.get(i).start);
		}
		
		return temp;
	}
	
	public String toString() {
		String temp = "{\n";
		
		ArrayList<T> vertices = getAllVertices();
		//Collections.sort((List<T>) vertices);
		for (T vertex : vertices) {
			temp += vertex.toString() + " : " + getAllOutEdgesForVertex(vertex) + "\n";
		}
		
		return temp + "}";
	}
	
	public static void testDigraph() {
		Digraph<String> test = new Digraph<>();
		
		test.addEdge(new WeightedEdge<String>("A", "B", 10));
		
		test.addEdge(new WeightedEdge<String>("A", "C", 3));
		
		test.addEdge(new WeightedEdge<String>("C", "B", 5));
		
		test.addEdge(new WeightedEdge<String>("B", "A", 5));
		
		System.out.println(test.toString());
	}
}