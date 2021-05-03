package it.polito.tdp.metroparis.model;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public abstract class TestModel {

	public static void main(String[] args) {
		/*Graph<String, DefaultEdge> grafo= new SimpleGraph<>(DefaultEdge.class);
		
		grafo.addVertex("UNO");
		grafo.addVertex("DUE");
		grafo.addVertex("TRE");
		
		grafo.addEdge("UNO", "TRE");
		grafo.addEdge("TRE", "DUE");
		
		System.out.println(grafo);*/
		
		Model m= new Model();
		m.creaGrafo();
		
		Fermata p=m.trovaFermata("La Fourche");
		if(p==null)
			System.out.println("fermata non trovata");
		else {
			List<Fermata> raggiungibili=m.fermateRaggiungibili(p);
			System.out.println(raggiungibili);
		}
	}

}
