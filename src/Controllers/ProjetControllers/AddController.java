package Controllers.ProjetControllers;

import BddPackage.CoutOperation;
import BddPackage.ProgrammeOperation;
import BddPackage.ProjectOperation;
import Models.Cout;
import Models.Programme;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddController implements Initializable {

    @FXML
    TextField   tfsite, tfCF, tfNbLogts, tfCoutRea, tfCoutEtu, tfCoutVrd,tfDate;
    @FXML
    TextArea  tfNom;
    @FXML
    ComboBox<String> cbProgramme;

    private Programme programmeSelected;
    private final ProjectOperation operation = new ProjectOperation();
    private final ProgrammeOperation programmeOperation = new ProgrammeOperation();
    private final CoutOperation coutOperation = new CoutOperation();

    private final ObservableList<String> dataComboP = FXCollections.observableArrayList();
    private ArrayList<Programme> programmes = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        programmeSelected = new Programme();
        refreshComboProgramme();
    }

    public void refreshComboProgramme(){
        try {
            dataComboP.clear();
            programmes = programmeOperation.getAll();
            for (Programme p : programmes) {
                dataComboP.add(p.getCode() + "  " + p.getNomProgramme());
            }
            cbProgramme.setItems(dataComboP);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionSelectComboProgramme(){
        try {
            int index = cbProgramme.getSelectionModel().getSelectedIndex();
            if (index != -1) this.programmeSelected = programmes.get(index);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionSelectProgramme() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ProjectViews/SelectProgrammeView.fxml"));
            DialogPane temp = loader.load();
            SelectProgrammeController controller = loader.getController();
            controller.InitListProgramme(programmeSelected);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.resizableProperty().setValue(false);
            dialog.initOwner(this.tfNom.getScene().getWindow());
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
            closeButton.setVisible(false);
            dialog.showAndWait();

            if (this.programmeSelected.getNomProgramme() != null)
                cbProgramme.getSelectionModel().select(this.programmeSelected.getCode() + "  " + this.programmeSelected.getNomProgramme());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionAnnulerAdd() {
        ((Stage)tfNom.getScene().getWindow()).close();
    }

    @FXML
    private void ActionInsert() {

        String nom = tfNom.getText().trim();
        String site = tfsite.getText().trim();
        String cf = tfCF.getText().trim();
        String nbLogts = tfNbLogts.getText().trim();
        String coutR = tfCoutRea.getText().trim();
        String coutE = tfCoutEtu.getText().trim();
        String coutVR = tfCoutVrd.getText().trim();
        String date = tfDate.getText();
        
        if (!site.isEmpty() && !nom.isEmpty() && !cf.isEmpty() && !nbLogts.isEmpty() && !date.isEmpty()) {

            Project projet = new Project();
            projet.setIdProgramme(programmeSelected.getId());
            projet.setNom(nom);
            projet.setSite(site);
            projet.setNumeroCF(cf);
            projet.setDateInsription(date);
            projet.setNomberLogts(Integer.parseInt(nbLogts));

            boolean ins = insert(projet);
            if (ins) {
                int idProject = operation.getLastAddId();

                if (!coutR.isEmpty()) {
                    addCout(idProject,"REALISATION",Double.parseDouble(coutR));
                } else {
                    addCout(idProject,"REALISATION",0.0);
                }

                if (!coutE.isEmpty()) {
                    addCout(idProject,"ETUDE",Double.parseDouble(coutE));
                } else {
                    addCout(idProject,"ETUDE",0.0);
                }

                if (!coutVR.isEmpty()) {
                    addCout(idProject,"VRD",Double.parseDouble(coutVR));
                } else {
                    addCout(idProject,"VRD",0.0);
                }
            }

            if (ins) ActionAnnulerAdd();
            else {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("Attention ");
                alertWarning.setContentText("un erreur inconnue");
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("d'accord");
                alertWarning.showAndWait();
            }
        } else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("Veuillez remplir les champs vides");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    private boolean insert(Project projet) {

        boolean insert = false;
        try {
            insert = operation.insert(projet);
            return insert;
        }catch (Exception e){
            e.printStackTrace();
            return insert;
        }
    }

    private void addCout(int idPr,String type,Double montant){
        Cout cout = new Cout();
        cout.setIdProjet(idPr);
        cout.setType(type);
        cout.setMontant(montant);
        insert(cout);
    }

    private void insert(Cout cout){
        try {
            coutOperation.insert(cout);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
