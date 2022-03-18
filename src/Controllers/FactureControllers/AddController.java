package Controllers.FactureControllers;

import BddPackage.FactureOperation;
import Models.Facture;
import Models.MarConBc;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddController implements Initializable {

    @FXML
    TextField tfConvention,tfNumero,tfMontant,tfOrganisme;
    @FXML
    DatePicker dpDate;

    private MarConBc mar;
    private final FactureOperation operation = new FactureOperation();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void Init(MarConBc marConBc) {
        this.mar = marConBc;

        tfConvention.setText(this.mar.getNom() + " N°: " + this.mar.getNumero());
        tfOrganisme.setText(this.mar.getNomOrganisme());
    }


    @FXML
    private void ActionInsert(){

        String numero = tfNumero.getText().trim();
        LocalDate date = dpDate.getValue();
        String montant = tfMontant.getText().trim();

        if (!numero.isEmpty() && !date.toString().isEmpty()  && !montant.isEmpty()){

            Facture facture = new Facture();
            facture.setIdMarConBc(this.mar.getId());
            facture.setNumero(numero);
            facture.setDate(date);
            facture.setMontant(Double.parseDouble(montant));

            boolean ins = insert(facture);
            if (ins) ActionAnnuler();
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
        ((Stage)tfConvention.getScene().getWindow()).close();
    }

    private boolean insert(Facture facture) {

        boolean insert = false;
        try {
            insert = operation.insert(facture);
            return insert;
        }catch (Exception e){
            e.printStackTrace();
            return insert;
        }
    }

}
