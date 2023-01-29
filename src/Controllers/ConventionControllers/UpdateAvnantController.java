package Controllers.ConventionControllers;

import BddPackage.AvnentCoutOperation;
import BddPackage.AvnentOperation;
import Models.AvnentCout;
import Models.AvnentMarConBc;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UpdateAvnantController implements Initializable {

    @FXML
    TextField tfAvnantMontant;
    @FXML
    ComboBox<String> cbAvnantType;
    @FXML
    DatePicker dpAvnant;

    private AvnentMarConBc avnentMarConBc;
    private final AvnentOperation operation = new AvnentOperation();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbAvnantType.getItems().addAll("SUPLEMENTAIRE","DEMENITIVE");
    }

    public void Init(AvnentMarConBc avnentMarConBc){

        this.avnentMarConBc = avnentMarConBc;

        tfAvnantMontant.setText(String.format("%.2f",avnentMarConBc.getMontant()));

        cbAvnantType.getSelectionModel().select(avnentMarConBc.getType());
        dpAvnant.setValue(avnentMarConBc.getDate());
    }

    @FXML
    private void ActionUpdate(){
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate dateAv = dpAvnant.getValue();
            String typeAv = cbAvnantType.getSelectionModel().getSelectedItem();
            double montantAv = Double.parseDouble(tfAvnantMontant.getText().trim());

            if (dateAv != null && !typeAv.isEmpty() ){

                if (typeAv.equals("DEMENITIVE") && montantAv > 0 ) montantAv = montantAv * -1;
                if (typeAv.equals("SUPLEMENTAIRE") && montantAv < 0 ) montantAv = montantAv * -1;

                AvnentMarConBc avnentCout = new AvnentMarConBc();
                avnentCout.setType(typeAv);
                avnentCout.setDate(dateAv);
                avnentCout.setMontant(montantAv);

                boolean ins = update(avnentCout,this.avnentMarConBc);
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

    private boolean update(AvnentMarConBc avnentCout, AvnentMarConBc avnentCoutOld) {
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
        ((Stage)tfAvnantMontant.getScene().getWindow()).close();
    }
}
