package Controllers.ProjetControllers;

import BddPackage.CoutOperation;
import BddPackage.ProjetOperation;
import Models.Cout;
import Models.ModelesTabels.ProjetTable;
import Models.Projet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class UpdateController implements Initializable {
    @FXML
    TextField tfNom, tfsite, tfCF, tfNbLogts, tfCoutRea, tfCoutEtu, tfCoutVrd,tfDate;



    private Projet projet;
    private final ProjetOperation operation = new ProjetOperation();
    private final CoutOperation coutOperation = new CoutOperation();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void InitUpdate(String idPr){

        this.projet = operation.get(Integer.valueOf(idPr));

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

            Projet projet = new Projet();
            projet.setNom(nom);
            projet.setSite(site);
            projet.setNumeroCF(cf);
            projet.setDateInsription(date);
            projet.setNomberLogts(Integer.parseInt(nbLogts));

            boolean upd = update(projet,this.projet);

            Cout coutRR = coutOperation.getCoutByProjet(projet.getId(), "REALISATION");
            Cout coutEE = coutOperation.getCoutByProjet(projet.getId(), "ETUDE");
            Cout coutVV = coutOperation.getCoutByProjet(projet.getId(), "VRD");

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

    private boolean update(Projet projet,Projet projetOld) {

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
