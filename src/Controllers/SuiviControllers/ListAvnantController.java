package Controllers.SuiviControllers;

import BddPackage.AvnentOperation;
import Models.AvnentMarConBc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ListAvnantController implements Initializable {

    @FXML
    TextField tfRecherche;
    @FXML
    TableView<AvnentMarConBc> tvAvnant;
    @FXML
    TableColumn<AvnentMarConBc,Integer> idColumn;
    @FXML
    TableColumn<AvnentMarConBc,String> dateColumn,MontantColumn,coutColumn,typeColumn;

    private int marConBc;
    private String type;
    private final AvnentOperation operation = new AvnentOperation();
    private final ObservableList<AvnentMarConBc> dataTable = FXCollections.observableArrayList();



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        MontantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

    }

    public void Init(int idMar ,String type){
        this.marConBc = idMar;
        this.type = type;

        refresh(type);
    }

    @FXML
    private void ActionSave(){

    }

    @FXML
    private void ActionAnnuler(){
        ((Stage)tvAvnant.getScene().getWindow()).close();
    }

    @FXML
    private void ActionSearch(){
        // filtrer les données
        ObservableList<AvnentMarConBc> dataAvnant = tvAvnant.getItems();
        FilteredList<AvnentMarConBc> filteredData = new FilteredList<>(dataAvnant, e -> true);
        String txtRecherche = tfRecherche.getText().trim();

        filteredData.setPredicate((Predicate<? super AvnentMarConBc>) avnentCout -> {
            if (txtRecherche.isEmpty()) {
                //loadDataInTable();
                return true;
            } else if (avnentCout.getDate().contains(txtRecherche)) {
                return true;
            } else if (avnentCout.getType().contains(txtRecherche)) {
                return true;
            } else return avnentCout.getType().contains(txtRecherche);
        });

        SortedList<AvnentMarConBc> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(tvAvnant.comparatorProperty());
        tvAvnant.setItems(sortedList);
    }

    @FXML
    private void ActionRefresh(){
        tfRecherche.clear();
        refresh(this.type);
    }


    private void refresh(String type){
        ArrayList<AvnentMarConBc> avnentCouts = operation.getAllByConvention(marConBc ,type);
        dataTable.setAll(avnentCouts);
        tvAvnant.setItems(dataTable);
    }

}
