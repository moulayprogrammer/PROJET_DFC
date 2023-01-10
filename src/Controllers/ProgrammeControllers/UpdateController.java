package Controllers.ProgrammeControllers;

import BddPackage.ProgrammeOperation;
import Models.Programme;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateController implements Initializable {

    @FXML
    TextField tfCode,tfCD,tfNbLogts,tfDate;
    @FXML
    TextArea tfNom;
    @FXML
    Button btnUpdate;

    private final ProgrammeOperation operation = new ProgrammeOperation();
    private Programme programmeUpdate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void InitUpdate(Programme upd){
        tfCode.setText(upd.getCode());
        tfNom.setText(upd.getNomProgramme());
        tfCD.setText(upd.getNumeroCD());
        tfNbLogts.setText(String.valueOf(upd.getNombreLogts()));
        tfDate.setText(String.valueOf(upd.getDateInscription()));

        this.programmeUpdate = upd;
    }

    @FXML
    private void ActionUpdateSave(){
        String code = tfCode.getText().trim();
        String nom = tfNom.getText().trim();
        String cd = tfCD.getText().trim();
        String nbLogts = tfNbLogts.getText().trim();
        String date = tfDate.getText().trim();

        if (!code.isEmpty() && !nom.isEmpty() && !cd.isEmpty() && ! nbLogts.isEmpty() && !date.isEmpty()){

            Programme programme = new Programme();
            programme.setCode(code);
            programme.setNomProgramme(nom);
            programme.setNombreLogts(Integer.parseInt(nbLogts));
            programme.setNumeroCD(cd);
            programme.setDateInscription(date);
            boolean upd = update(programme,programmeUpdate);
            if (upd) closeDialog(btnUpdate);
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

    private boolean update(Programme programmeNew,Programme programmeOld) {
        boolean upd = false;
        try {
            upd = operation.update(programmeNew,programmeOld);
            return upd;
        }catch (Exception e){
            e.printStackTrace();
            return upd;
        }
    }

    @FXML
    private void ActionAnnullerUpd(){
        closeDialog(btnUpdate);
    }

    private void closeDialog(Button btn) {
        ((Stage)btn.getScene().getWindow()).close();
    }

}
