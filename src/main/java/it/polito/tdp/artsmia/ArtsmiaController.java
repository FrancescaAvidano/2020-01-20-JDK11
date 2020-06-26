package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Coppia;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	List<Coppia> coppieDaStampare = this.model.getCoppie();
    	if(coppieDaStampare == null) {
    		txtResult.appendText("Creare prima il grafo!");
    		return;
    	}
    	for(Coppia c : coppieDaStampare) {
    		txtResult.appendText(String.format("%d, %d : %d\n", c.getA1(), c.getA2(), c.getPeso()));
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();

    	try {
    		Integer id_artista = Integer.parseInt(txtArtista.getText());
    		if(!this.model.grafoContiene(id_artista)) {
    			txtResult.appendText("L'artista non è nel grafo.");
        		return;
    		}
    		List<Integer> percorso = this.model.trovaPercorso(id_artista);
    		txtResult.appendText("Percorso più lungo: " + percorso.size() + "\n");
    		for (Integer v : percorso) {
    			txtResult.appendText(v+" ");
    		}
    	}catch (NumberFormatException e) {
    		txtResult.appendText("Inserire un id_artist nel formato corretto!");
    		return;
    	}
    	
    	
    	txtResult.appendText("Calcola percorso");
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String ruolo = boxRuolo.getValue();
    	
    	if(ruolo == null) {
    		txtResult.appendText("Scegliere un ruolo!");
    		return;
    	}
    	
    	this.model.creaGrafo(ruolo);
    	btnCalcolaPercorso.setDisable(false);
    	txtResult.appendText("Grafo creato con successo!\n");
    	
    }

    public void setModel(Model model) {
    	this.model = model;
    	btnCalcolaPercorso.setDisable(true);
    	this.boxRuolo.getItems().addAll(model.getRuoli());
    }

    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }
}
