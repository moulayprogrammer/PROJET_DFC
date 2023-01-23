package Controllers.SuiviEtbControllers;

import BddPackage.OrganismOperation;
import Models.Organism;
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

public class SelectOrganismeController implements Initializable {

    @FXML
    TextField tfRecherche;
    @FXML
    TableView<Organism> tvOrganisme;
    @FXML
    TableColumn<Organism,String> resonSocialColumn,adressColumn,telColumn,rcColumn,nifColumn;
    @FXML
    TableColumn<Organism, Integer> idColumn;

    private final ObservableList<Organism> dataTable = FXCollections.observableArrayList();
    private final OrganismOperation operation = new OrganismOperation();
    private Organism organisme;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        resonSocialColumn.setCellValueFactory(new PropertyValueFactory<>("raisonSocial"));
        adressColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("tel"));
        rcColumn.setCellValueFactory(new PropertyValueFactory<>("rc"));
        nifColumn.setCellValueFactory(new PropertyValueFactory<>("nif"));

        refresh();
    }

    public void Init(Organism organisme){
        this.organisme = organisme;
    }

    @FXML
    private void ActionSelectOrganisme(){
        Organism organismeSelected =  tvOrganisme.getSelectionModel().getSelectedItem();

        if (organismeSelected != null){
            try {
                this.organisme.setId(organismeSelected.getId());
                this.organisme.setRaisonSocial(organismeSelected.getRaisonSocial());
                this.organisme.setAdresse(organismeSelected.getAdresse());
                this.organisme.setTel(organismeSelected.getTel());
                this.organisme.setRc(organismeSelected.getRc());
                this.organisme.setNif(organismeSelected.getNif());

                ActionAnnuler();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("Veuillez choisir un organisme");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }


    @FXML
    private void ActionAnnuler(){
        ((Stage)tfRecherche.getScene().getWindow()).close();
    }

    @FXML
    void ActionSearch() {
        // filtrer les données
        ObservableList<Organism> dataOrganisme = tvOrganisme.getItems();
        FilteredList<Organism> filteredData = new FilteredList<>(dataOrganisme, e -> true);
        String txtRecherche = tfRecherche.getText().trim();

        filteredData.setPredicate((Predicate<? super Organism>) organisme -> {
            if (txtRecherche.isEmpty()) {
                //loadDataInTable();
                return true;
            } else if (organisme.getRaisonSocial().contains(txtRecherche)) {
                return true;
            } else if (organisme.getAdresse().contains(txtRecherche)) {
                return true;
            } else if (organisme.getTel().contains(txtRecherche)) {
                return true;
            } else if (organisme.getRc().contains(txtRecherche)) {
                return true;
            } else return organisme.getNif().contains(txtRecherche);
        });

        SortedList<Organism> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(tvOrganisme.comparatorProperty());
        tvOrganisme.setItems(sortedList);

    }

    @FXML
    private void ActionRefresh(){
        clearRecherche();
        refresh();
    }

    private void clearRecherche(){
        tfRecherche.clear();
    }

    private void refresh(){
        ArrayList<Organism> organismes = operation.getAll();
        dataTable.setAll(organismes);
        tvOrganisme.setItems(dataTable);
    }
}
