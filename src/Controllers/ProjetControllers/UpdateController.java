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

public class UpdateController implements Initializable {
    @FXML
    TextField  tfsite, tfCF, tfNbLogts, tfCoutRea, tfCoutEtu, tfCoutVrd,tfDate;
    @FXML
    TextArea tfNom;
    @FXML
    ComboBox<String> cbProgramme;


    private Project projet;
    private Programme programmeSelected;
    private final ProjectOperation operation = new ProjectOperation();
    private final ProgrammeOperation programmeOperation = new ProgrammeOperation();
    private final CoutOperation coutOperation = new CoutOperation();

    private final ObservableList<String> dataComboP = FXCollections.observableArrayList();
    private ArrayList<Programme> programmes = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
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


    public void InitUpdate(String idPr){

        this.projet = operation.get(Integer.valueOf(idPr));
        this.programmeSelected = programmeOperation.get(this.projet.getIdProgramme());

        tfNom.setText(projet.getNom());
        tfsite.setText(projet.getSite());
        tfCF.setText(projet.getNumeroCF());
        tfDate.setText(projet.getDateInsription());
        tfNbLogts.setText(projet.getNomberLogts()+"");
        double cR = coutOperation.getCoutByProjet(projet.getId(), "REALISATION").getMontant();
        tfCoutRea.setText(String.format( "%.2f", cR));
        double cE = coutOperation.getCoutByProjet(projet.getId(), "ETUDE").getMontant();
        tfCoutEtu.setText(String.format( "%.2f", cE));
        double cV = coutOperation.getCoutByProjet(projet.getId(), "VRD").getMontant();
        tfCoutVrd.setText(String.format( "%.2f", cV));

        cbProgramme.getSelectionModel().select(this.programmeSelected.getCode() + "  " + this.programmeSelected.getNomProgramme());

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
    private void ActionSaveUpdate(){
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
            projet.setIdProgramme(this.programmeSelected.getId());
            projet.setNom(nom);
            projet.setSite(site);
            projet.setNumeroCF(cf);
            projet.setDateInsription(date);
            projet.setNomberLogts(Integer.parseInt(nbLogts));

            boolean upd = update(projet,this.projet);

            Cout coutRR = coutOperation.getCoutByProjet(this.projet.getId(), "REALISATION");
            Cout coutEE = coutOperation.getCoutByProjet(this.projet.getId(), "ETUDE");
            Cout coutVV = coutOperation.getCoutByProjet(this.projet.getId(), "VRD");

            coutRR.setMontant(Double.parseDouble(coutR)); update(coutRR,coutRR);
            coutEE.setMontant(Double.parseDouble(coutE)); update(coutEE,coutEE);
            coutVV.setMontant(Double.parseDouble(coutVR)); update(coutVV,coutVV);

            if (upd) ActionAnnulerUpdate();
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

    private boolean update(Project projet, Project projetOld) {

        boolean upd = false;
        try {
            upd = operation.update(projet,projetOld);
            return upd;
        }catch (Exception e){
            e.printStackTrace();
            return upd;
        }
    }

    private void update(Cout cout,Cout coutOld){
        try {
            coutOperation.update(cout,coutOld);
        }catch (Exception e){
            e.printStackTrace();

        }
    }

    @FXML
    private void ActionAnnulerUpdate(){
        ((Stage) tfNom.getScene().getWindow()).close();
    }


}
