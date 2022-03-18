package Controllers.ProjetControllers;

import BddPackage.AvnentCoutOperation;
import Models.AvnentCout;
import Models.Cout;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

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

    private AvnentCout avnentCout;
    private final AvnentCoutOperation operation = new AvnentCoutOperation();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbAvnantType.getItems().addAll("SUPLEMENTAIRE","DEMENITIVE");
    }

    public void Init(AvnentCout avnentCout){

        this.avnentCout = avnentCout;

        tfAvnantMontant.setText(avnentCout.getMontant()+"");
        cbAvnantType.getSelectionModel().select(avnentCout.getType());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dpAvnant.setValue(LocalDate.parse(avnentCout.getDate(),formatter));
    }

    @FXML
    private void ActionUpdate(){
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            String dateAv = dateFormatter.format(dpAvnant.getValue());
            String typeAv = cbAvnantType.getSelectionModel().getSelectedItem();
            double montantAv = Double.parseDouble(tfAvnantMontant.getText().trim());

            if (!dateAv.isEmpty() && !typeAv.isEmpty() ){

                if (typeAv.equals("DEMENITIVE") && montantAv > 0 ) montantAv = montantAv * -1;
                if (typeAv.equals("SUPLEMENTAIRE") && montantAv < 0 ) montantAv = montantAv * -1;

                AvnentCout avnentCout = new AvnentCout();
                avnentCout.setType(typeAv);
                avnentCout.setDate(dateAv);
                avnentCout.setMontant(montantAv);

                boolean ins = update(avnentCout,this.avnentCout);
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

    private boolean update(AvnentCout avnentCout, AvnentCout avnentCoutOld) {
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
