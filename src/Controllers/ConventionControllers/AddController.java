package Controllers.ConventionControllers;

import BddPackage.MarConBcOperation;
import Models.MarConBc;
import Models.ModelesTabels.ProjetTable;
import Models.Organisme;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddController implements Initializable {

    @FXML
    TextField tfProjet,tfOrganisme,tfNumero,tfNbLogts,tfCoutHT,tfCoutTVA,tfCoutTTC,tfCompteNumero,tfCompteBank,tfCompteAgence,tfNom;
    @FXML
    ComboBox<String> cbType;
    @FXML
    DatePicker dpDate;

    private ProjetTable projetTable;
    private Organisme organisme;
    private final MarConBcOperation operation = new MarConBcOperation();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.organisme = new Organisme();

        cbType.getItems().addAll("REALISATION","ETUDE","VRD");
    }

    public void Init(ProjetTable projetTable) {
        try {
            this.projetTable = projetTable;

            tfProjet.setText(this.projetTable.getNom());

            tfCoutHT.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.isEmpty()) {
                    tfCoutHT.setText("0");
                    newValue = "0";
                }
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
        String numeroCompte = tfCompteNumero.getText().trim();
        String bankCompte = tfCompteBank.getText().trim();
        String agenceCompte = tfCompteAgence.getText().trim();

        if (!nom.isEmpty()  && !numero.isEmpty() && !type.isEmpty() && !nbLogts.isEmpty() && !date.isEmpty()
                && !ht.isEmpty() && !tva.isEmpty() && !ttc.isEmpty() && !numeroCompte.isEmpty() && !bankCompte.isEmpty() && !agenceCompte.isEmpty()){

            MarConBc mar = new MarConBc();
            mar.setIdProjet(this.projetTable.getId());
            mar.setIdOrganisme(this.organisme.getId());
            mar.setNom(nom);
            mar.setNumero(numero);
            mar.setType(type);
            mar.setNumbreLogts(Integer.parseInt(nbLogts));
            mar.setDate(date);
            mar.setHt(Double.parseDouble(ht));
            mar.setTva(Double.parseDouble(tva));
            mar.setTtc(Double.parseDouble(ttc));
            mar.setCompteNumero(numeroCompte);
            mar.setCompteBank(bankCompte);
            mar.setCompteAgence(agenceCompte);

            boolean ins = insert(mar);
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
        ((Stage)tfProjet.getScene().getWindow()).close();
    }

    private boolean insert(MarConBc marConBc) {

        boolean insert = false;
        try {
            insert = operation.insert(marConBc);
            return insert;
        }catch (Exception e){
            e.printStackTrace();
            return insert;
        }
    }

}
