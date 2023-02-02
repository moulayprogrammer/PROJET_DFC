package Controllers.ConventionControllers;

import BddPackage.OdsRepriseOperation;
import Models.MarConBc;
import Models.OdsReprise;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddOdsRepriseController implements Initializable {


    @FXML
    TextField tfNumero;
    @FXML
    DatePicker dpDate;

    private MarConBc marConBc;
    private final OdsRepriseOperation operation = new OdsRepriseOperation();


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

            if ( date != null && !number.isEmpty() ){

                OdsReprise odsReprise = new OdsReprise();
                odsReprise.setIdConvention(this.marConBc.getId());
                odsReprise.setNumber(number);
                odsReprise.setDate(date);

                boolean ins = insert(odsReprise);
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

    private boolean insert(OdsReprise odsReprise){
        boolean ins = false;
        try {
            ins = operation.insert(odsReprise);
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
