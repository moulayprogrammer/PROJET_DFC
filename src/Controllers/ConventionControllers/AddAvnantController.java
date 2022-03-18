package Controllers.ConventionControllers;

import BddPackage.AvnentOperation;
import Models.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddAvnantController implements Initializable {


    @FXML
    TextField tfConvention,tfAvnantMontant;
    @FXML
    ComboBox<String> cbAvnantType;
    @FXML
    DatePicker dpAvnant;


    private MarConBc marConBc;
    private final AvnentOperation operation = new AvnentOperation();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void Init(MarConBc marConBc) {

        this.marConBc = marConBc;

        tfConvention.setText(marConBc.getType() + " N° " + marConBc.getNumero());
        cbAvnantType.getItems().addAll("SUPLEMENTAIRE","DEMENITIVE");
    }

    @FXML
    private void ActionAdd(){

        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            String dateAv = dateFormatter.format(dpAvnant.getValue());
            String typeAv = cbAvnantType.getSelectionModel().getSelectedItem();
            double montantAv = Double.parseDouble(tfAvnantMontant.getText().trim());

            if ( !dateAv.isEmpty() && !typeAv.isEmpty() ){

                if (typeAv.equals("DEMENITIVE") && montantAv > 0 ) montantAv = montantAv * -1;

                AvnentMarConBc avnentCout = new AvnentMarConBc();
                avnentCout.setType(typeAv);
                avnentCout.setIdMarConBc(marConBc.getId());
                avnentCout.setDate(dateAv);
                avnentCout.setMontant(montantAv);

                boolean ins = insert(avnentCout);
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

    private boolean insert(AvnentMarConBc avnentCout){
        boolean ins = false;
        try {
            ins = operation.insert(avnentCout);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ins;
    }

    @FXML
    private void ActionAnnuler(){
        ((Stage)tfConvention.getScene().getWindow()).close();
    }
}
