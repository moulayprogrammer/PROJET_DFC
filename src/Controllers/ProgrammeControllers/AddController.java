package Controllers.ProgrammeControllers;

import BddPackage.ProgrammeOperation;
import Models.Programme;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddController implements Initializable {

    @FXML
    TextField tfCode,tfCD,tfNbLogts,tfDate;
    @FXML
    TextArea tfNom;
    @FXML
    Button btnInsert;

    private final ProgrammeOperation operation = new ProgrammeOperation();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void ActionAnnulledAdd(){
        closeDialog(btnInsert);
    }

    @FXML
    void ActionInsert(ActionEvent event) {

        String code = tfCode.getText().trim();
        String nom = tfNom.getText().trim();
        String cd = tfCD.getText().trim();
        String nbLogts = tfNbLogts.getText().trim();
        String  date = tfDate.getText().trim();

        if (!code.isEmpty() && !nom.isEmpty() && !cd.isEmpty() && ! nbLogts.isEmpty() && !date.isEmpty()){

            Programme programme = new Programme();
            programme.setCode(code);
            programme.setNomProgramme(nom);
            programme.setNombreLogts(Integer.parseInt(nbLogts));
            programme.setNumeroCD(cd);
            programme.setDateInscription(date);

            boolean ins = insert(programme);
            if (ins) closeDialog(btnInsert);
            else {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("Attention ");
                alertWarning.setContentText("un erreur inconnue");
                alertWarning.initOwner(this.btnInsert.getScene().getWindow());
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("d'accord");
                alertWarning.showAndWait();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("Veuillez remplir les champs vides");
            alertWarning.initOwner(this.btnInsert.getScene().getWindow());
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    private boolean insert(Programme programme) {
        boolean insert = false;
        try {
            insert = operation.insert(programme);
            return insert;
        }catch (Exception e){
            e.printStackTrace();
            return insert;
        }
    }


    private void closeDialog(Button btn) {
        ((Stage)btn.getScene().getWindow()).close();
    }
}
