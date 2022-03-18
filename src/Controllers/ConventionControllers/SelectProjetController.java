package Controllers.ConventionControllers;

import BddPackage.CoutOperation;
import BddPackage.ProjetOperation;
import Models.Cout;
import Models.ModelesTabels.ProjetTable;
import Models.Projet;
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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class SelectProjetController implements Initializable {

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

    private final ProjetOperation operation = new ProjetOperation();
    private final CoutOperation coutOperation = new CoutOperation();
    private final ObservableList<ProjetTable> pTableData = FXCollections.observableArrayList();
    private final ArrayList<ProjetTable> projetTables = new ArrayList<>();
    private ArrayList<Projet> projets = new ArrayList<>();
    private ProjetTable projetTable;

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

    public void Init(ProjetTable projetTable){
        this.projetTable = projetTable;
    }

    @FXML
    private void ActionAnnuler(){
        ((Stage)tfRecherche.getScene().getWindow()).close();
    }

    @FXML
    private void ActionSelectProjet(){
        ProjetTable projetSelected = tvProjet.getSelectionModel().getSelectedItem();

        if (projetSelected != null){
            try {

                this.projetTable.setId(projetSelected.getId());
                this.projetTable.setNom(projetSelected.getNom());
                this.projetTable.setSite(projetSelected.getSite());
                this.projetTable.setCf(projetSelected.getCf());
                this.projetTable.setDate(projetSelected.getDate());

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
        projets = operation.getAll();
        chargeTable();
    }
    private void chargeTable(){
        projetTables.clear();
        projets.forEach(projet -> {

            Cout coutR = coutOperation.getCoutByProjet(projet.getId(), "réalisation");
            Cout coutE = coutOperation.getCoutByProjet(projet.getId(), "étud");
            Cout coutV = coutOperation.getCoutByProjet(projet.getId(), "VRD");


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
