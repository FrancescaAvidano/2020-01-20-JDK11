package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	private ArtsmiaDAO dao;
	private SimpleWeightedGraph<Integer, DefaultWeightedEdge> grafo; 
	public List<Coppia> listaCoppie;// = new ArrayList();
	private List<Integer> best;
	
	public Model() {
		this.dao = new ArtsmiaDAO();
	}
	
	public boolean grafoContiene(Integer id) {
		if(this.grafo.containsVertex(id))
			return true;
		return false;
	}
	
	public List<String> getRuoli() {
		return this.dao.getRuoli();
	}
	
	public void creaGrafo(String ruolo) {
		this.grafo = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		listaCoppie = this.dao.getCoppia(ruolo);
		for(Coppia c : listaCoppie) {
			if(!this.grafo.containsVertex(c.getA1()))
				this.grafo.addVertex(c.getA1());
			if(!this.grafo.containsVertex(c.getA2()))
				this.grafo.addVertex(c.getA2());
			
			if(this.grafo.getEdge(c.getA1(), c.getA2()) == null) 
				Graphs.addEdgeWithVertices(this.grafo, c.getA1(), c.getA2(), c.getPeso());
		}
		System.out.println(String.format("Creato grafo con %d vertici e %d archi.", this.grafo.vertexSet().size(), this.grafo.edgeSet().size()));
	}
	
	public List<Coppia> getCoppie(){
		return this.listaCoppie;
	}
	
	public List<Integer> trovaPercorso(Integer sorgente){
		List<Integer> parziale = new ArrayList<>();
		this.best = new ArrayList<Integer>();
		parziale.add(sorgente);
		trovaRicorsivo(parziale, -1); //gli passo un peso di -1 che sicuramente non c'è nel grafo
		return this.best;
	}

	private void trovaRicorsivo(List<Integer> parziale, int peso) {
		
		Integer ultimo = parziale.get(parziale.size() -1);
		//ottengo i vicini
		List<Integer> vicini = Graphs.neighborListOf(this.grafo, ultimo);
		for (Integer vicino : vicini) {
			if(!parziale.contains(vicino) && peso == -1) {
				parziale.add(vicino);
				trovaRicorsivo(parziale, (int) this.grafo.getEdgeWeight(this.grafo.getEdge(ultimo, vicino)));
				parziale.remove(vicino);
			}else {
				if(!parziale.contains(vicino) && this.grafo.getEdgeWeight(this.grafo.getEdge(ultimo, vicino)) == peso) {
					parziale.add(vicino);
					trovaRicorsivo(parziale,peso);
					parziale.remove(vicino);
				}
			}
		}	
		if(parziale.size()>best.size()) {
			this.best = new ArrayList<>(parziale);
		}
	}
}
