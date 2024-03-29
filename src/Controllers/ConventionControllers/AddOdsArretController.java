package Controllers.ConventionControllers;

import BddPackage.OdsArretOperation;
import Models.MarConBc;
import Models.OdsArret;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddOdsArretController implements Initializable {


    @FXML
    TextField tfNumero;
    @FXML
    TextArea taRaison;
    @FXML
    DatePicker dpDate;

    private MarConBc marConBc;
    private final OdsArretOperation operation = new OdsArretOperation();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void Init(MarConBc marConBc) {

        this.marConBc = marConBc;

    }

    @FXML
    private void ActionAdd(){

        try {

            String number = tfNumero.getText().trim();
            LocalDate date = dpDate.getValue();
            String raison = taRaison.getText().trim();


            if ( date != null && !number.isEmpty() ){

                OdsArret odsArret = new OdsArret();
                odsArret.setIdConvention(this.marConBc.getId());
                odsArret.setNumber(number);
                odsArret.setDate(date);
                odsArret.setRaison(raison);

                boolean ins = insert(odsArret);
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

    private boolean insert(OdsArret odsArret){
        boolean ins = false;
        try {
            ins = operation.insert(odsArret);
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
