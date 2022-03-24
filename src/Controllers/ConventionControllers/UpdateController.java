package Controllers.ConventionControllers;

import BddPackage.MarConBcOperation;
import Models.MarConBc;
import Models.Organisme;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class UpdateController implements Initializable {

    @FXML
    TextField tfOrganisme,tfNumero,tfNbLogts,tfCoutHT,tfCoutTVA,tfCoutTTC,tfNom;
    @FXML
    ComboBox<String> cbType;
    @FXML
    DatePicker dpDate;

    private Organisme organisme;
    private MarConBc marConBc;
    private final MarConBcOperation operation = new MarConBcOperation();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.organisme = new Organisme();

        cbType.getItems().addAll("REALISATION","ETUDE","VRD");
    }

    public void Init(MarConBc marConBc) {
        try {
            this.marConBc = marConBc;

            tfCoutHT.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!tfCoutTVA.getText().isEmpty()) {
                    double tva = Double.parseDouble(tfCoutTVA.getText().trim());
                    double ht = Double.parseDouble(newValue);
                    double ttc = ((ht*tva)/100) + ht;
                    tfCoutTTC.setText(String.format("%.2f",ttc));
                }
            });

            tfCoutTVA.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!tfCoutHT.getText().isEmpty()) {
                    double tva = Double.parseDouble(newValue);
                    double ht = Double.parseDouble(tfCoutHT.getText().trim());
                    double ttc = ((ht*tva)/100) + ht;
                    tfCoutTTC.setText(String.format("%.2f",ttc));
                }
            });

            tfNom.setText(marConBc.getNom());
            tfNumero.setText(marConBc.getNumero());
            cbType.getSelectionModel().select(marConBc.getType());
            tfNbLogts.setText(marConBc.getNumbreLogts()+"");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dpDate.setValue(LocalDate.parse(marConBc.getDate(),formatter));
            tfCoutHT.setText(String.format("%.2f",marConBc.getHt()));
            tfCoutTVA.setText(String.format("%.2f",marConBc.getTva()));
            tfCoutTTC.setText(String.format("%.2f",marConBc.getTtc()));
            tfOrganisme.setText(marConBc.getNomOrganisme());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    private void ActionSelectOrganisme(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ConventionViews/SelectOrganismeView.fxml"));
            DialogPane temp = loader.load();
            SelectOrganismeController controller = loader.getController();
            controller.Init(this.organisme);
            javafx.scene.control.Dialog<ButtonType> dialog = new javafx.scene.control.Dialog<>();
            dialog.setDialogPane(temp);
            dialog.resizableProperty().setValue(false);
            dialog.showAndWait();

            tfOrganisme.setText(this.organisme.getRaisonSocial());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionInsert(){

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String nom = tfNom.getText().trim();
        String numero = tfNumero.getText().trim();
        String type = cbType.getSelectionModel().getSelectedItem();
        String nbLogts = tfNbLogts.getText().trim();
        String date = dateFormatter.format(dpDate.getValue());
        String ht = tfCoutHT.getText().trim();
        String tva = tfCoutTVA.getText().trim();
        String ttc = tfCoutTTC.getText().trim();

        if (!nom.isEmpty()  && !numero.isEmpty() && !type.isEmpty() && !nbLogts.isEmpty() && !date.isEmpty()
                && !ht.isEmpty() && !tva.isEmpty() && !ttc.isEmpty()){

            MarConBc mar = new MarConBc();
            if (this.organisme.getId() != 0) mar.setIdOrganisme(this.organisme.getId());
            else mar.setIdOrganisme(this.marConBc.getIdOrganisme());
            mar.setNom(nom);
            mar.setNumero(numero);
            mar.setType(type);
            mar.setNumbreLogts(Integer.parseInt(nbLogts));
            mar.setDate(date);
            mar.setHt(Double.parseDouble(ht));
            mar.setTva(Double.parseDouble(tva));
            mar.setTtc(Double.parseDouble(ttc));

            boolean ins = update(mar);
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
        ((Stage)tfOrganisme.getScene().getWindow()).close();
    }

    private boolean update(MarConBc marConBc) {

        boolean upd;
        try {
            upd = operation.update(marConBc,this.marConBc);
            return upd;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
