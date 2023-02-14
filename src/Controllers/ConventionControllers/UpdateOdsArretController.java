package Controllers.ConventionControllers;

import BddPackage.OdsArretOperation;
import Models.OdsArret;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UpdateOdsArretController implements Initializable {


    @FXML
    TextField tfNumero;
    @FXML
    TextArea taRaison;
    @FXML
    DatePicker dpDate;

    private OdsArret odsArret;
    private final OdsArretOperation operation = new OdsArretOperation();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void Init(OdsArret odsArret) {

        tfNumero.setText(odsArret.getNumber());
        dpDate.setValue(odsArret.getDate());
        taRaison.setText(odsArret.getRaison());

        this.odsArret = odsArret;
    }

    @FXML
    private void ActionUpdate(){

        try {

            String number = tfNumero.getText().trim();
            LocalDate date = dpDate.getValue();
            String raison = taRaison.getText().trim();


            if ( date != null && !number.isEmpty() ){

                OdsArret odsArret = new OdsArret();
                odsArret.setNumber(number);
                odsArret.setDate(date);
                odsArret.setRaison(raison);

                boolean ins = update(odsArret);
                if (ins) ActionAnnuler();

            }else {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("Attention ");
                alertWarning.setContentText("Veuillez remplir les champs vides");
                alertWarning.initOwner(this.tfNumero.getScene().getWindow());
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("d'accord");
                alertWarning.showAndWait();
            }

        }catch (Exception e){
            e.printStackTrace();
            Alert alertWarning = new Alert(Alert.AlertType.ERROR);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("un erreur inconnue");
            alertWarning.initOwner(this.tfNumero.getScene().getWindow());
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    private boolean update(OdsArret odsArret){
        boolean ins = false;
        try {
            ins = operation.update(odsArret,this.odsArret);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ins;
    }

    @FXML
    private void ActionAnnuler(){
        ((Stage)tfNumero.getScene().getWindow()).close();
    }
}
