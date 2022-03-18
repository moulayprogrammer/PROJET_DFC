package Controllers.FactureControllers;

import BddPackage.OrderPaimentOperation;
import Models.Facture;
import Models.OrderePaiment;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class AddOpController implements Initializable {

    @FXML
    TextField tfFacture,tfMontantFacture,tfPR,tfRG,tfMontantPaye,tfNumeroOrdre;
    @FXML
    DatePicker dpDate;
    @FXML
    CheckBox checkRG;

    private Facture facture;
    private final OrderPaimentOperation operation = new OrderPaimentOperation();

    private double montantPaye,montantRG,montantFact;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfPR.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                checkRG();
            }else {
                tfPR.setText("0.0");
                checkRG();
            }
        });
    }

    public void Init(Facture facture) {
        this.facture = facture;

        tfFacture.setText(facture.getNumero());
        montantFact = facture.getMontant();
        tfMontantFacture.setText(String.format( "%.2f", montantFact));
        montantRG = ( montantFact * 5 ) / 100 ;
        montantPaye = montantFact - montantRG;
        tfMontantPaye.setText(String.format( "%.2f", montantPaye ));
        tfRG.setText(String.format("%.2f", montantRG ));
    }

    @FXML
    private void checkRG(){
        if (!checkRG.isSelected()) {
            tfRG.setText("0.0");
            montantPaye = montantFact;
            checkPR();
        }else {
            tfRG.setText(String.format("%.2f",montantRG));
            montantPaye = montantFact - montantRG;
            checkPR();
        }
    }

    private void checkPR() {
        if (tfPR.getText().isEmpty()){
            tfMontantPaye.setText(String.format("%.2f",montantPaye));
        }else {
            double pr = Double.parseDouble(tfPR.getText().trim());
            montantPaye = montantPaye - pr;
            tfMontantPaye.setText(String.format("%.2f",montantPaye));
        }
    }

    @FXML
    private void ActionInsert(){

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String numero = tfNumeroOrdre.getText().trim();
        LocalDate date = dpDate.getValue();
        String pr = tfPR.getText().trim();


        if (!numero.isEmpty() && !date.toString().isEmpty() && !pr.isEmpty() ){

            OrderePaiment orderePaiment = new OrderePaiment();
            orderePaiment.setIdFacture(facture.getId());
            orderePaiment.setNumero(numero);
            orderePaiment.setDate(date);
            orderePaiment.setMontant(montantPaye);
            orderePaiment.setPenaliteRotarde(Double.parseDouble(pr));
            orderePaiment.setRetuneGarante(Double.parseDouble(tfRG.getText().trim()));

            boolean ins = insert(orderePaiment);
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
        ((Stage)tfFacture.getScene().getWindow()).close();
    }

    private boolean insert(OrderePaiment orderePaiment) {

        boolean insert = false;
        try {
            insert = operation.insert(orderePaiment);
            return insert;
        }catch (Exception e){
            e.printStackTrace();
            return insert;
        }
    }

}
