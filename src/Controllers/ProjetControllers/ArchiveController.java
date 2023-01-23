package Controllers.ProjetControllers;

import BddPackage.AvnentCoutOperation;
import BddPackage.CoutOperation;
import BddPackage.ProjectOperation;
import Models.AvnentCout;
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

public class ArchiveController implements Initializable {

    @FXML
    TextField tfRecherche;

    @FXML
    TableView<ProjetTable> tvProjet;
    @FXML
    TableColumn<ProjetTable,Integer> idColumn,nbLogtsCuColumn;
    @FXML
    TableColumn<ProjetTable, String> NomColumn,siteColumn,cfColumn,dateColumn;
    @FXML
    TableColumn<ProjetTable,Double> coutRColumn,coutEColumn,coutVColumn,avnantColumn;

    private final ProjectOperation operation = new ProjectOperation();
    private final CoutOperation coutOperation = new CoutOperation();
    private final AvnentCoutOperation avnentCoutOperation = new AvnentCoutOperation();
    private final ObservableList<ProjetTable> pTableData = FXCollections.observableArrayList();
    private final ArrayList<ProjetTable> projetTables = new ArrayList<>();
    private ArrayList<Project> projets = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        NomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        siteColumn.setCellValueFactory(new PropertyValueFactory<>("site"));
        cfColumn.setCellValueFactory(new PropertyValueFactory<>("cf"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        nbLogtsCuColumn.setCellValueFactory(new PropertyValueFactory<>("nbLogts"));
        coutRColumn.setCellValueFactory(new PropertyValueFactory<>("coutR"));
        coutEColumn.setCellValueFactory(new PropertyValueFactory<>("coutE"));
        coutVColumn.setCellValueFactory(new PropertyValueFactory<>("coutV"));

        refresh();
    }

    @FXML
    private void ActionAnnuler(){
        ((Stage)tfRecherche.getScene().getWindow()).close();
    }

    @FXML
    private void tableClick(MouseEvent mouseEvent) {
        if ( mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) ){

            ActionDeleteFromArchive();
        }
    }
    @FXML
    private void ActionDeleteFromArchive(){
        ProjetTable projetArchived = tvProjet.getSelectionModel().getSelectedItem();

        if (projetArchived != null){
            try {

                Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirmation.setHeaderText("Confirmer désarchivage");
                alertConfirmation.setContentText("Êtes-vous sûr de vouloir désarchiver le projet intitulé : " + projetArchived.getNom() );
                Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("D'accord");

                Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
                cancel.setText("Annuler");

                alertConfirmation.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.CANCEL) {
                        alertConfirmation.close();
                    } else if (response == ButtonType.OK) {
                        operation.DeleteFromArchive(new Project(projetArchived.getId()));

                        ActionAnnuler();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("Veuillez choisir le projet pour désarchiver");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void ActionSearch(){
        ObservableList<ProjetTable> dataProgramme = tvProjet.getItems();
        FilteredList<ProjetTable> filteredData = new FilteredList<>(dataProgramme, e -> true);
        String txtRecherche = tfRecherche.getText().trim();

        filteredData.setPredicate((Predicate<? super ProjetTable>) projetTable -> {
            if (txtRecherche.isEmpty()) {
                //loadDataInTable();
                return true;
            } else if (projetTable.getNom().contains(txtRecherche)) {
                return true;
            } else if (projetTable.getSite().contains(txtRecherche)) {
                return true;
            } else if (projetTable.getCf().contains(txtRecherche)) {
                return true;
            } else return projetTable.getDate().contains(txtRecherche);
        });

        SortedList<ProjetTable> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(tvProjet.comparatorProperty());
        tvProjet.setItems(sortedList);
    }

    @FXML
    private void ActionRefresh(){
        tfRecherche.clear();
        refresh();
    }

    private void refresh(){
        projets = operation.getAllArchive();
        chargeTable();
    }
    private void chargeTable(){
        projetTables.clear();
        projets.forEach(projet -> {

            Cout coutR = coutOperation.getCoutByProjet(projet.getId(), "REALISATIONr");
            Cout coutE = coutOperation.getCoutByProjet(projet.getId(), "ETUDE");
            Cout coutV = coutOperation.getCoutByProjet(projet.getId(), "VRD");

            ArrayList<AvnentCout> avnentCoutsR = avnentCoutOperation.getAll(coutR.getId());
            ArrayList<AvnentCout> avnentCoutsE = avnentCoutOperation.getAll(coutE.getId());
            ArrayList<AvnentCout> avnentCoutsV = avnentCoutOperation.getAll(coutV.getId());

            double totR;
            double totE;
            double totV;

            totR = avnentCoutsR.stream().mapToDouble(AvnentCout::getMontant).sum();
            totE = avnentCoutsE.stream().mapToDouble(AvnentCout::getMontant).sum();
            totV = avnentCoutsV.stream().mapToDouble(AvnentCout::getMontant).sum();

            ProjetTable pt = new ProjetTable();
            pt.setId(projet.getId());
            pt.setNom(projet.getNom());
            pt.setSite(projet.getSite());
            pt.setCf(projet.getNumeroCF());
            pt.setDate(projet.getDateInsription());
            pt.setNbLogts(projet.getNomberLogts());
            pt.setCoutR(String.format(Locale.FRANCE, "%,.2f", coutR.getMontant()));
            pt.setCoutE(String.format(Locale.FRANCE, "%,.2f", coutE.getMontant()));
            pt.setCoutV(String.format(Locale.FRANCE, "%,.2f", coutV.getMontant()));

            projetTables.add(pt);
        });
        pTableData.setAll(projetTables);
        tvProjet.setItems(pTableData);
    }
}
