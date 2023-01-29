package Controllers.ConventionControllers;

import BddPackage.AvnentCompteOperation;
import BddPackage.AvnentOperation;
import Models.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddAvnantController implements Initializable {


    @FXML
    TextField tfAvnantMontant,tfAvnantNumero,tfAvnantCompteNumero,tfAvnantCompteBank,tfAvnantCompteAgence;
    @FXML
    TextArea tfConvention,tfConventionCompte;
    @FXML
    ComboBox<String> cbAvnantType;
    @FXML
    DatePicker dpAvnant,dpAvenantCompte;
    @FXML
    TabPane tabPane;


    private MarConBc marConBc;
    private final AvnentOperation operation = new AvnentOperation();
    private final AvnentCompteOperation avnentCompteMarConBc = new AvnentCompteOperation();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void Init(MarConBc marConBc) {

        this.marConBc = marConBc;

        tfConvention.setText(marConBc.getNom());
        cbAvnantType.getItems().addAll("SUPLEMENTAIRE","DEMENITIVE");
        cbAvnantType.getSelectionModel().select(0);
        dpAvnant.setValue(LocalDate.now());

        tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            switch (newTab.getId()){
                case "tabMontant":
                    tfConvention.setText(marConBc.getNom());
                    cbAvnantType.getItems().addAll("SUPLEMENTAIRE","DEMENITIVE");
                    break;
                case "tabCompte":
                    tfConventionCompte.setText(marConBc.getNom());
                    break;
            }
        });

    }

    @FXML
    private void ActionAdd(){

        String tabId = tabPane.getSelectionModel().getSelectedItem().getId();
        switch (tabId){
            case "tabMontant":
                try {

                    LocalDate dateAv = dpAvnant.getValue();
                    String typeAv = cbAvnantType.getSelectionModel().getSelectedItem();
                    double montantAv = Double.parseDouble(tfAvnantMontant.getText().trim());

                    if ( dateAv != null && !typeAv.isEmpty() ){

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
                break;
            case "tabCompte":
                try {

                    String numeroAv = tfAvnantNumero.getText().trim();
                    LocalDate dateAv = dpAvenantCompte.getValue();
                    String compteNumero = tfAvnantCompteNumero.getText().trim();
                    String compteBank = tfAvnantCompteBank.getText().trim();
                    String compteAgence = tfAvnantCompteAgence.getText().trim();

                    if ( !numeroAv.isEmpty() && dateAv != null && !compteNumero.isEmpty() && !compteBank.isEmpty() && !compteAgence.isEmpty() ){

                        AvnentCompteMarConBc compteMarConBc = new AvnentCompteMarConBc();

                        compteMarConBc.setNumero(numeroAv);
                        compteMarConBc.setIdMarConBc(this.marConBc.getId());
                        compteMarConBc.setDate(dateAv);
                        compteMarConBc.setCompteNumero(compteNumero);
                        compteMarConBc.setCompteBank(compteBank);
                        compteMarConBc.setCompteAgence(compteAgence);

                        boolean ins = insert(compteMarConBc);
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
                break;
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

    private boolean insert(AvnentCompteMarConBc avnentCout){
        boolean ins = false;
        try {
            ins = avnentCompteMarConBc.insert(avnentCout);
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
