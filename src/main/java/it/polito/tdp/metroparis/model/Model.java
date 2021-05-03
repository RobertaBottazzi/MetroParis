package it.polito.tdp.metroparis.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {
	
	Graph<Fermata, DefaultEdge> grafo;
	
	public void creaGrafo() {
		this.grafo= new SimpleGraph<>(DefaultEdge.class);
		
		MetroDAO dao= new MetroDAO();
		List<Fermata> fermate=dao.getAllFermate();
		//stessa cosa si può fare con la classe Graphs che contiene il metodo addAllVertices
		/*for(Fermata f: fermate) {
			this.grafo.addVertex(f);
		}*/
		Graphs.addAllVertices(this.grafo, fermate);
		
		//Aggiungiamo gli archi, considero tutte le coppie possibili di fermate e chiedo al dao se queste sono collegate, se lo sono aggiungo l'arco
		
		/*for(Fermata f1: this.grafo.vertexSet()) {
			for(Fermata f2:this.grafo.vertexSet()) {
				if(!f1.equals(f2) && dao.fermateCollegate(f1, f2))
					this.grafo.addEdge(f1, f2);
			}
		}*/
		
		//oppure estraggo da connessione l'elenco degli archi
		List<Connessione> connessioni= dao.getAllConnessioni(fermate);
		for(Connessione c: connessioni) {
			this.grafo.addEdge(c.getStazP(), c.getStazA());
		}
		
		System.out.format("Grafo creato con %d vertici e %d archi\n",this.grafo.vertexSet().size(), this.grafo.edgeSet().size()) ;
		
		//dato un arco prendo la sorgente e la destinazione dell'arco
		//in un grafo orientato sappiamo chi sono (e utilizzo il metodo outgoingEdges), ma se il grafo non è orientato dipende da come è stato creato il grafo
		//Fermata f = null;
		//da un vertice trovo gli archi adiacenti
		/*Set<DefaultEdge> archi = this.grafo.edgesOf(f);
		for(DefaultEdge e: archi) {
			/*uno di questi coincide con f e l'laltro è il vertice che mi interessa
			Fermata f1=this.grafo.getEdgeSource(e);
			Fermata f2=this.grafo.getEdgeTarget(e);
			if(f1.equals(f))
				//f2 è quello che mi serve
			else
				//f1 è quello che mi serve
			//oppure stessa cosa posso fare con:
			Fermata f1=Graphs.getOppositeVertex(this.grafo, e, f);
		}*/
		//oppure ancora
		//List<Fermata> fermateAdiacenti=Graphs.successorListOf(this.grafo, f);
		//se il grafo è orientato posso anche utilizzare questo metodo:
		//List<Fermata> fermatePrecedenti=Graphs.predecessorListOf(this.grafo,f);
		//con un grafo non orientato funzionano allo stesso modo
	}
	
	public List<Fermata> fermateRaggiungibili(Fermata partenza){
		//lo faccio con una visita in ampiezza
		BreadthFirstIterator<Fermata, DefaultEdge> bvf= new BreadthFirstIterator<>(this.grafo,partenza); //contiene tutti quanti i vertici adiacenti
		List<Fermata> result= new ArrayList<>();
		while(bvf.hasNext()) { //finchè è vero ci sono vertici ancora da scoprire
			Fermata f= bvf.next(); //prendo il vertice da scoprire
			result.add(f); //lo aggiungo alla lista
		}
		return result;
	}
	
	public Fermata trovaFermata(String nome) {
		for(Fermata f: this.grafo.vertexSet()) {
			if(f.getNome().equals(nome))
				return f;
		}
		return null;
	}
}
