package Controllers.ProjetControllers;

import BddPackage.ProgrammeOperation;
import Models.Programme;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class SelectProgrammeController implements Initializable {

    @FXML
    TextField tfRecherche;
    @FXML
    TableView<Programme> programmeTable;
    @FXML
    TableColumn<Programme,String> codeColumn,NomColumn,cdColumn,dateColumn;
    @FXML
    TableColumn<Programme,Integer> nbLogtsColumn,idColumn;

    private final ObservableList<Programme> dataTable = FXCollections.observableArrayList();
    private final ProgrammeOperation operation = new ProgrammeOperation();
    private Programme programmeSelected;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        codeColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        NomColumn.setCellValueFactory(new PropertyValueFactory<>("nomProgramme"));
        nbLogtsColumn.setCellValueFactory(new PropertyValueFactory<>("nombreLogts"));
        cdColumn.setCellValueFactory(new PropertyValueFactory<>("numeroCD"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateInscription"));

        refresh();
    }

    public void InitListProgramme(Programme ps) {

        this.programmeSelected = ps;
    }

    @FXML
    private void tableClick(MouseEvent mouseEvent) {
        if ( mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) ){

            ActionSelectProgramme();
        }
    }
    @FXML
    private void ActionSelectProgramme(){
        Programme programmeSelect = programmeTable.getSelectionModel().getSelectedItem();
        if (programmeSelect != null){
            try {
                this.programmeSelected.setId(programmeSelect.getId());
                this.programmeSelected.setCode(programmeSelect.getCode());
                this.programmeSelected.setNomProgramme(programmeSelect.getNomProgramme());
                this.programmeSelected.setDateInscription(programmeSelect.getDateInscription());
                this.programmeSelected.setNumeroCD(programmeSelect.getNumeroCD());
                this.programmeSelected.setNombreLogts(programmeSelect.getNombreLogts());

                ActionAnnuler();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("svp sélectionner un programme");
            alertWarning.initOwner(this.tfRecherche.getScene().getWindow());
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
    private void ActionRefresh(){
        clearRecherche();
        refresh();
    }

    private void clearRecherche(){
        tfRecherche.clear();
    }

    private void refresh(){
        ArrayList<Programme> programmes = operation.getAll();
        dataTable.setAll(programmes);
        programmeTable.setItems(dataTable);
    }

    @FXML
    void ActionSearch() {
        // filtrer les données
        ObservableList<Programme> dataProgramme = programmeTable.getItems();
        FilteredList<Programme> filteredData = new FilteredList<>(dataProgramme, e -> true);
        String txtRecherche = tfRecherche.getText().trim();

        filteredData.setPredicate((Predicate<? super Programme>) programme -> {
            if (txtRecherche.isEmpty()) {
                //loadDataInTable();
                return true;
            } else if (programme.getCode().contains(txtRecherche)) {
                return true;
            } else if (programme.getNomProgramme().contains(txtRecherche)) {
                return true;
            } else return programme.getNumeroCD().contains(txtRecherche);
        });

        SortedList<Programme> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(programmeTable.comparatorProperty());
        programmeTable.setItems(sortedList);

    }
}
