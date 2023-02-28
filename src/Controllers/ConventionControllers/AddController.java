package Controllers.ConventionControllers;

import BddPackage.MarConBcOperation;
import BddPackage.OrganismOperation;
import BddPackage.ProjectOperation;
import Models.MarConBc;
import Models.Organism;
import Models.Project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddController implements Initializable {

    @FXML
    TextField tfDuree,tfNumero,tfNbLogts,tfCoutHT,tfCoutTVA,tfCoutTTC,tfCompteNumero,tfCompteBank,tfCompteAgence;
    @FXML
    TextArea taNom,taObject;
    @FXML
    ComboBox<String> cbRebrique,cbType,cbProject,cbDuree,cbOrganism;
    @FXML
    DatePicker dpDate,dpDateOds;

    private Project projectSelected;
    private Organism organism;
    private final MarConBcOperation operation = new MarConBcOperation();
    private final ProjectOperation projectOperation = new ProjectOperation();
    private final OrganismOperation organismOperation = new OrganismOperation();


    private final ObservableList<String> dataComboP = FXCollections.observableArrayList();
    private final ObservableList<String> dataComboOrg = FXCollections.observableArrayList();
    private ArrayList<Project> projects = new ArrayList<>();
    private ArrayList<Organism> organisms = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.organism = new Organism();
        this.projectSelected = new Project();

        cbRebrique.getItems().addAll("REALISATION","ETUDE","VRD","ETUDE ET REALISATION","ETUDE ET REALISATION VRD");
        cbType.getItems().addAll("MARCHE","CONVENTION","BON DE COMMANDE");
        cbDuree.getItems().addAll("JOURS","MOIS");
        cbDuree.getSelectionModel().select(0);
        refreshComboProjects();
        refreshComboOrganism();

        tfCoutHT.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                tfCoutHT.setText("0");
                newValue = "0";
            }
            if (!tfCoutTVA.getText().isEmpty()) {
                double tva = Double.parseDouble(tfCoutTVA.getText().trim());
                double ht = Double.parseDouble(newValue);
                double ttc = ((ht*tva)/100) + ht;
                tfCoutTTC.setText(String.format("%.2f",ttc));
            }
        });

        tfCoutTVA.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!tfCoutHT.getText().isEmpty()) {
                double tva = Double.parseDouble(newValue);
                double ht = Double.parseDouble(tfCoutHT.getText().trim());
                double ttc = ((ht*tva)/100) + ht;
                tfCoutTTC.setText(String.format("%.2f",ttc));
            }
        });
    }

    private void refreshComboProjects() {
        try {
            dataComboP.clear();
            projects = projectOperation.getAll();
            for (Project p : projects) {
                dataComboP.add(p.getNom());
            }
            cbProject.setItems(dataComboP);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void refreshComboOrganism() {
        try {
            dataComboOrg.clear();
            organisms = organismOperation.getAll();
            for (Organism org : organisms) {
                dataComboOrg.add(org.getRaisonSocial());
            }
            cbOrganism.setItems(dataComboOrg);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionSelectComboProject(){
        try {
            int index = cbProject.getSelectionModel().getSelectedIndex();
            if (index != -1) this.projectSelected = projects.get(index);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionSelectComboOrganism(){
        try {
            int index = cbOrganism.getSelectionModel().getSelectedIndex();
            if (index != -1) this.organism = organisms.get(index);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionSelectProject() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ConventionViews/SelectProjetView.fxml"));
            DialogPane temp = loader.load();
            SelectProjetController controller = loader.getController();
            controller.Init(this.projectSelected);
            javafx.scene.control.Dialog<ButtonType> dialog = new javafx.scene.control.Dialog<>();
            dialog.initOwner(this.taNom.getScene().getWindow());
            dialog.setDialogPane(temp);
            dialog.resizableProperty().setValue(false);
            dialog.initOwner(this.taNom.getScene().getWindow());
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
            closeButton.setVisible(false);
            dialog.showAndWait();

            if (this.projectSelected.getNom() != null)
                cbProject.getSelectionModel().select(this.projectSelected.getNom());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionSelectOrganism(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ConventionViews/SelectOrganismView.fxml"));
            DialogPane temp = loader.load();
            SelectOrganismController controller = loader.getController();
            controller.Init(this.organism);
            javafx.scene.control.Dialog<ButtonType> dialog = new javafx.scene.control.Dialog<>();
            dialog.setDialogPane(temp);
            dialog.resizableProperty().setValue(false);
            dialog.initOwner(this.taNom.getScene().getWindow());
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
            closeButton.setVisible(false);
            dialog.showAndWait();

            if (this.organism.getRaisonSocial() != null)
                cbOrganism.getSelectionModel().select(this.organism.getRaisonSocial());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionInsert(){

        String numero = tfNumero.getText().trim();
        String nom = taNom.getText().trim();
        String object = taObject.getText().trim();
        String rebrique = cbRebrique.getSelectionModel().getSelectedItem();
        String type = cbType.getSelectionModel().getSelectedItem();
        String nbLogts = tfNbLogts.getText().trim();
        LocalDate date = dpDate.getValue();
        String ht = tfCoutHT.getText().trim();
        String tva = tfCoutTVA.getText().trim();
        String ttc = tfCoutTTC.getText().trim();
        String numeroCompte = tfCompteNumero.getText().trim();
        String bankCompte = tfCompteBank.getText().trim();
        String agenceCompte = tfCompteAgence.getText().trim();
        String duree = tfDuree.getText().trim();
        LocalDate ods = dpDateOds.getValue();

        if (!nom.isEmpty()  && !numero.isEmpty() && !type.isEmpty() && !nbLogts.isEmpty() && date != null
                && !ht.isEmpty() && !tva.isEmpty() && !ttc.isEmpty() && !numeroCompte.isEmpty() && !bankCompte.isEmpty()
                && !agenceCompte.isEmpty() && !duree.isEmpty() && ods != null && !object.isEmpty() && !rebrique.isEmpty()){

            MarConBc mar = new MarConBc();
            mar.setIdProjet(this.projectSelected.getId());
            mar.setIdOrganisme(this.organism.getId());
            mar.setNom(nom);
            mar.setObject(object);
            mar.setNumero(numero);
            mar.setType(type);
            mar.setRebrique(rebrique);
            mar.setNumbreLogts(Integer.parseInt(nbLogts));
            mar.setDate(date);
            mar.setHt(Double.parseDouble(ht));
            mar.setTva(Double.parseDouble(tva));
            mar.setTtc(Double.parseDouble(ttc));
            mar.setCompteNumero(numeroCompte);
            mar.setCompteBank(bankCompte);
            mar.setCompteAgence(agenceCompte);
            mar.setTypeDuree(cbDuree.getSelectionModel().getSelectedItem());
            mar.setDuree(Integer.parseInt(duree));
            mar.setOds(ods);

            boolean ins = insert(mar);
            if (ins) ActionAnnuler();
            else {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("Attention ");
                alertWarning.setContentText("un erreur inconnue");
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("d'accord");
                alertWarning.showAndWait();
            }

        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("Veuillez remplir les champs vides");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void ActionAnnuler(){
        ((Stage) taNom.getScene().getWindow()).close();
    }

    private boolean insert(MarConBc marConBc) {

        boolean insert = false;
        try {
            insert = operation.insert(marConBc);
            return insert;
        }catch (Exception e){
            e.printStackTrace();
            return insert;
        }
    }

}
