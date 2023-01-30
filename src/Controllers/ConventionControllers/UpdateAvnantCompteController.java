package Controllers.ConventionControllers;

import BddPackage.AvnentCompteOperation;
import BddPackage.AvnentOperation;
import Models.AvnentCompteMarConBc;
import Models.AvnentMarConBc;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UpdateAvnantCompteController implements Initializable {

    @FXML
    TextField tfNumero,tfCompteNumero,tfCompteBank,tfCompteAgence;
    @FXML
    DatePicker dpAvenant;

    private AvnentCompteMarConBc avnentMarConBc;
    private final AvnentCompteOperation operation = new AvnentCompteOperation();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void Init(AvnentCompteMarConBc avnentMarConBc){

        this.avnentMarConBc = avnentMarConBc;

        tfNumero.setText(avnentMarConBc.getNumero());
        dpAvenant.setValue(avnentMarConBc.getDate());
        tfCompteNumero.setText(avnentMarConBc.getCompteNumero());
        tfCompteBank.setText(avnentMarConBc.getCompteBank());
        tfCompteAgence.setText(avnentMarConBc.getCompteAgence());
    }

    @FXML
    private void ActionUpdate(){
        try {

            String numeroAv = tfNumero.getText().trim();
            LocalDate dateAv = dpAvenant.getValue();
            String compteNumero = tfCompteNumero.getText().trim();
            String compteBank = tfCompteBank.getText().trim();
            String compteAgence = tfCompteAgence.getText().trim();

            if ( !numeroAv.isEmpty() && dateAv != null && !compteNumero.isEmpty() && !compteBank.isEmpty() && !compteAgence.isEmpty() ){

                AvnentCompteMarConBc compteMarConBc = new AvnentCompteMarConBc();

                compteMarConBc.setNumero(numeroAv);
                compteMarConBc.setDate(dateAv);
                compteMarConBc.setCompteNumero(compteNumero);
                compteMarConBc.setCompteBank(compteBank);
                compteMarConBc.setCompteAgence(compteAgence);

                boolean ins = update(compteMarConBc,this.avnentMarConBc);
                if (ins) ActionAnnuler();

            }else {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("Attention ");
                alertWarning.setContentText("Veuillez remplir les champs vides");
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("d'accord");
                alertWarning.showAndWait();
            }

        }catch (Exception e){
            e.printStackTrace();
            Alert alertWarning = new Alert(Alert.AlertType.ERROR);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("un erreur inconnue");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    private boolean update(AvnentCompteMarConBc avnentCout, AvnentCompteMarConBc avnentCoutOld) {
        boolean ins = false;
        try {
            ins = operation.update(avnentCout,avnentCoutOld);
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
