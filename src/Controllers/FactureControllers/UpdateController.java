package Controllers.FactureControllers;

import BddPackage.FactureOperation;
import Models.Facture;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UpdateController implements Initializable {

    @FXML
    TextField tfNumero,tfMontant;
    @FXML
    DatePicker dpDate;

    private final FactureOperation operation = new FactureOperation();
    private Facture facture;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void Init(Facture facture) {
        this.facture = facture;

        tfNumero.setText(this.facture.getNumero());
        tfMontant.setText(this.facture.getMontant()+"");
        dpDate.setValue(this.facture.getDate());
    }


    @FXML
    private void ActionInsert(){

        String numero = tfNumero.getText().trim();
        LocalDate date = dpDate.getValue();
        String montant = tfMontant.getText().trim();

        if (!numero.isEmpty() && !date.toString().isEmpty()  && !montant.isEmpty()){

            Facture facture = new Facture();
            facture.setNumero(numero);
            facture.setDate(date);
            facture.setMontant(Double.parseDouble(montant));

            boolean upd = update(facture,this.facture);
            if (upd) ActionAnnuler();
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
    private void ActionAnnuler(){
        ((Stage)tfNumero.getScene().getWindow()).close();
    }

    private boolean update(Facture facture , Facture factureOld) {

        boolean update = false;
        try {
            update = operation.update(facture,factureOld);
            return update;
        }catch (Exception e){
            e.printStackTrace();
            return update;
        }
    }

}
