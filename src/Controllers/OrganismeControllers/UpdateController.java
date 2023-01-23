package Controllers.OrganismeControllers;

import BddPackage.OrganismOperation;
import Models.Organism;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateController implements Initializable {

    @FXML
    TextField tfRaisonSocail,tfAddress,tfTel,tfRC,tfNIF;

    private final OrganismOperation operation = new OrganismOperation();
    private Organism organisme;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void Init(Organism organismeUpdate) {
        this.organisme = organismeUpdate;

        tfRaisonSocail.setText(organismeUpdate.getRaisonSocial());
        tfAddress.setText(organismeUpdate.getAdresse());
        tfTel.setText(organismeUpdate.getTel());
        tfRC.setText(organismeUpdate.getRc());
        tfNIF.setText(organismeUpdate.getNif());
    }

    @FXML
    private void ActionUpdate(){

        String raisonSocail = tfRaisonSocail.getText().trim();
        String adress = tfAddress.getText().trim();
        String tel = tfTel.getText().trim();
        String rc = tfRC.getText().trim();
        String nif = tfNIF.getText().trim();

        if (!raisonSocail.isEmpty() && !adress.isEmpty() && !tel.isEmpty() && ! rc.isEmpty() && !nif.isEmpty()){

            Organism organisme = new Organism();
            organisme.setRaisonSocial(raisonSocail);
            organisme.setAdresse(adress);
            organisme.setTel(tel);
            organisme.setRc(rc);
            organisme.setNif(nif);

            boolean ins = update(organisme,this.organisme);
            if (ins) ActionAnnulledUpdate();
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
    private void ActionAnnulledUpdate(){
        ((Stage)tfAddress.getScene().getWindow()).close();
    }

    private boolean update(Organism organisme , Organism organismeOld) {
        boolean insert = false;
        try {
            insert = operation.update(organisme,organismeOld);
            return insert;
        }catch (Exception e){
            e.printStackTrace();
            return insert;
        }
    }



}
