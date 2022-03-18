package Controllers.OrganismeControllers;

import BddPackage.OrganismeOperation;
import Models.Organisme;
import Models.Programme;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddController implements Initializable {

    @FXML
    TextField tfRaisonSocail,tfAddress,tfTel,tfRC,tfNIF;

    private final OrganismeOperation operation = new OrganismeOperation();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void ActionInsert(){

        String raisonSocail = tfRaisonSocail.getText().trim();
        String adress = tfAddress.getText().trim();
        String tel = tfTel.getText().trim();
        String rc = tfRC.getText().trim();
        String nif = tfNIF.getText().trim();

        if (!raisonSocail.isEmpty() && !adress.isEmpty() && !tel.isEmpty() && ! rc.isEmpty() && !nif.isEmpty()){

            Organisme organisme = new Organisme();
            organisme.setRaisonSocial(raisonSocail);
            organisme.setAdresse(adress);
            organisme.setTel(tel);
            organisme.setRc(rc);
            organisme.setNif(nif);

            boolean ins = insert(organisme);
            if (ins) ActionAnnulledAdd();
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
    private void ActionAnnulledAdd(){
        ((Stage)tfAddress.getScene().getWindow()).close();
    }

    private boolean insert(Organisme organisme) {
        boolean insert = false;
        try {
            insert = operation.insert(organisme);
            return insert;
        }catch (Exception e){
            e.printStackTrace();
            return insert;
        }
    }


}
