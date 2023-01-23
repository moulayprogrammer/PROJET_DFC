package Controllers.ConventionControllers;

import BddPackage.CoutOperation;
import BddPackage.ProjectOperation;
import Models.Cout;
import Models.ModelesTabels.ProjetTable;
import Models.Project;
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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class SelectProjetController implements Initializable {

    @FXML
    TextField tfRecherche;

    @FXML
    TableView<Project> tvProjet;
    @FXML
    TableColumn<Project,Integer> idColumn,nbLogtsCuColumn;
    @FXML
    TableColumn<ProjetTable, String> NomColumn,siteColumn,cfColumn,dateColumn;

    private final ProjectOperation operation = new ProjectOperation();

    private final ObservableList<Project> pTableData = FXCollections.observableArrayList();
    private ArrayList<Project> projets = new ArrayList<>();
    private Project projectSelected;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        NomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        siteColumn.setCellValueFactory(new PropertyValueFactory<>("site"));
        cfColumn.setCellValueFactory(new PropertyValueFactory<>("numeroCF"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateInsription"));
        nbLogtsCuColumn.setCellValueFactory(new PropertyValueFactory<>("nomberLogts"));

        refresh();
    }

    public void Init(Project project){
        this.projectSelected = project;
    }

    @FXML
    private void ActionAnnuler(){
        ((Stage)tfRecherche.getScene().getWindow()).close();
    }
    @FXML
    private void tableClick(MouseEvent mouseEvent) {
        if ( mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) ){

            ActionSelectProjet();
        }
    }
    @FXML
    private void ActionSelectProjet(){
        Project project = tvProjet.getSelectionModel().getSelectedItem();

        if (project != null){
            try {

                this.projectSelected.setId(project.getId());
                this.projectSelected.setNom(project.getNom());
                this.projectSelected.setSite(project.getSite());
                this.projectSelected.setNumeroCF(project.getNumeroCF());
                this.projectSelected.setDateInsription(project.getDateInsription());

                ActionAnnuler();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("svp sélectionner un projet");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void ActionRefresh(){
        tfRecherche.clear();
        refresh();
    }

    private void refresh(){
        projets = operation.getAll();
        pTableData.setAll(projets);
        tvProjet.setItems(pTableData);
    }

    @FXML
    private void ActionSearch(){
        ObservableList<Project> dataProgramme = tvProjet.getItems();
        FilteredList<Project> filteredData = new FilteredList<>(dataProgramme, e -> true);
        String txtRecherche = tfRecherche.getText().trim();

        filteredData.setPredicate((Predicate<? super Project>) project -> {
            if (txtRecherche.isEmpty()) {
                refresh();
                return true;
            } else if (project.getNom().contains(txtRecherche)) {
                return true;
            } else if (project.getSite().contains(txtRecherche)) {
                return true;
            } else if (project.getNumeroCF().contains(txtRecherche)) {
                return true;
            } else return project.getDateInsription().contains(txtRecherche);
        });

        SortedList<Project> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(tvProjet.comparatorProperty());
        tvProjet.setItems(sortedList);
    }
}
