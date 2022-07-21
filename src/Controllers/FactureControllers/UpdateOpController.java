package Controllers.FactureControllers;

import BddPackage.OrderPaymentOperation;
import Models.Facture;
import Models.OrderePaiment;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UpdateOpController implements Initializable {

    @FXML
    TextField tfFacture,tfMontantFacture,tfPR,tfRG,tfMontantPaye,tfNumeroOrdre;
    @FXML
    DatePicker dpDate;
    @FXML
    CheckBox checkRG;

    private Facture facture;
    private OrderePaiment orderePaiment;
    private final OrderPaymentOperation operation = new OrderPaymentOperation();

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


    public void Init(Facture facture ,OrderePaiment orderePaiment) {
        this.facture = facture;
        this.orderePaiment = orderePaiment;

        montantFact = facture.getMontant();
        montantRG = orderePaiment.getRetuneGarante();
        montantPaye = orderePaiment.getMontant();


        tfFacture.setText(facture.getNumero());
        tfMontantFacture.setText(String.format( "%.2f", montantFact));
        tfRG.setText(String.format( "%.2f", montantRG));
        tfPR.setText(String.format( "%.2f", orderePaiment.getPenaliteRotarde()));
        tfMontantPaye.setText(String.format( "%.2f", montantPaye));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dpDate.setValue(orderePaiment.getDate());
        tfNumeroOrdre.setText(orderePaiment.getNumero());

        if (montantRG != 0){
            checkRG.setSelected(Boolean.TRUE);
            tfRG.setText(String.format( "%.2f", montantRG));
            checkRG();
        }

    }

    @FXML
    private void checkRG(){
        if (!checkRG.isSelected()) {
            tfRG.setText("0.0");
            montantPaye = montantFact;
            checkPR();
        }else {
            montantRG = (montantFact * 5) / 100;
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
    private void ActionUpdate(){

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


            boolean upd = update(orderePaiment);
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
        ((Stage)tfFacture.getScene().getWindow()).close();
    }

    private boolean update(OrderePaiment orderePaiment) {

        boolean update = false;
        try {
            update = operation.update(orderePaiment,this.orderePaiment);
            return update;
        }catch (Exception e){
            e.printStackTrace();
            return update;
        }
    }

}
