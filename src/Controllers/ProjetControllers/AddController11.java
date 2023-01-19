package Controllers.ProjetControllers;

import BddPackage.CoutOperation;
import BddPackage.ProjetOperation;
import Models.Cout;
import Models.Programme;
import Models.Projet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddController11 implements Initializable {

    @FXML
    TextField   tfsite, tfCF, tfNbLogts, tfCoutRea, tfCoutEtu, tfCoutVrd,tfDate;
    @FXML
    TextArea tfProg, tfNom;

    private Programme programmeSelected;
    private final ProjetOperation operation = new ProjetOperation();
    private final CoutOperation coutOperation = new CoutOperation();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void InitAdd(Programme programme) {
        tfProg.setText(programme.getNomProgramme());

        this.programmeSelected = programme;
    }

    @FXML
    private void ActionAnnulerAdd() {
        ((Stage)tfProg.getScene().getWindow()).close();
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

            Projet projet = new Projet();
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

    private boolean insert(Projet projet) {

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
