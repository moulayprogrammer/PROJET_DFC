package Controllers.FactureControllers;

import BddPackage.FactureOperation;
import BddPackage.MarConBcOperation;
import BddPackage.OdsArretOperation;
import BddPackage.OdsRepriseOperation;
import Models.Facture;
import Models.MarConBc;
import Models.OdsArret;
import Models.OdsReprise;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpdateController implements Initializable {

    @FXML
    TextField tfNumero,tfMontant,tfOrganisme;
    @FXML
    DatePicker dpDate;
    @FXML
    ComboBox<String> cbConvention;
    @FXML
    Label lbPeriod;

    private MarConBc marSelected;
    private final FactureOperation operation = new FactureOperation();
    private final MarConBcOperation marConBcOperation = new MarConBcOperation();
    private final OdsArretOperation odsArretOperation = new OdsArretOperation();
    private final OdsRepriseOperation odsRepriseOperation = new OdsRepriseOperation();

    private final ObservableList<String> dataComboMar = FXCollections.observableArrayList();
    private ArrayList<MarConBc> marConBcs = new ArrayList<>();
    private Facture facture;
    private boolean dateL = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.marSelected = new MarConBc();
        refreshComboConv();
    }

    public void Init(Facture facture) {
        this.facture = facture;

        tfNumero.setText(this.facture.getNumero());
        tfMontant.setText(String.format( "%.2f", this.facture.getMontant()));
//        tfMontant.setText(this.facture.getMontant()+"");
        dpDate.setValue(this.facture.getDate());

        this.marSelected = marConBcOperation.get(facture.getIdMarConBc());
        cbConvention.getSelectionModel().select(this.marSelected.getNumero() + "  " + this.marSelected.getNom());
        tfOrganisme.setText(this.marSelected.getNomOrganisme());
        calcPeriod();
    }

    public void refreshComboConv(){
        try {
            dataComboMar.clear();
            marConBcs = marConBcOperation.getAll();
            for (MarConBc marConBc : marConBcs) {
                dataComboMar.add(marConBc.getNumero() + "  " + marConBc.getNom());
            }
            cbConvention.setItems(dataComboMar);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionSelectComboConv(){
        try {
            int index = cbConvention.getSelectionModel().getSelectedIndex();
            if (index != -1) this.marSelected = marConBcs.get(index);
            tfOrganisme.setText(marSelected.getNomOrganisme());
            if (dpDate.getValue() != null) calcPeriod();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionSelectConv() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/FactureViews/SelectConventionView.fxml"));
            DialogPane temp = loader.load();
            SelectConventionController controller = loader.getController();
            controller.Init(this.marSelected);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.resizableProperty().setValue(false);
            dialog.initOwner(this.tfOrganisme.getScene().getWindow());
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
            closeButton.setVisible(false);
            dialog.showAndWait();

            if (this.marSelected.getNom() != null) {
                cbConvention.getSelectionModel().select(this.marSelected.getNumero() + "  " + this.marSelected.getNom());
                if (dpDate.getValue() != null) calcPeriod();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void calcPeriod(){
        try {

            LocalDate dateLimiter = this.marSelected.getOds();
            LocalDate dateSit = dpDate.getValue();
            if (dateLimiter.isBefore(dateSit)) {
                if (this.marSelected.getTypeDuree().equals("JOURS"))
                    dateLimiter = dateLimiter.plusDays(this.marSelected.getDuree());
                else dateLimiter = dateLimiter.plusMonths(this.marSelected.getDuree());

                ArrayList<OdsArret> arrets = odsArretOperation.getAllByConvention(this.marSelected.getId());
                ArrayList<OdsReprise> reprises = odsRepriseOperation.getAllByConvention(this.marSelected.getId());

                int days = 0;
                if (arrets.size() != 0) {
                    for (int i = 0; i < arrets.size(); i++) {
                        OdsReprise reprise = reprises.get(i);
                        if (reprise.getId() > 0)
                            days += ChronoUnit.DAYS.between(arrets.get(i).getDate(), reprise.getDate());
                    }
                }
                dateLimiter = dateLimiter.plusDays(days);

                long defDate = ChronoUnit.DAYS.between(dateSit, dateLimiter);
                if (defDate > 0 ) {
                    lbPeriod.setText("Reste " + defDate + " jour");
                    lbPeriod.setTextFill(Color.GREEN);
                }else {
                    lbPeriod.setText("hors délai par " + (-1*defDate) + " jour");
                    lbPeriod.setTextFill(Color.RED);
                }

                dateL = true;

            } else {
                lbPeriod.setText("la date est erroné");
                dateL = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionUpdate(){

        String numero = tfNumero.getText().trim();
        LocalDate date = dpDate.getValue();
        String montant = tfMontant.getText().trim();

        if (!numero.isEmpty() && !date.toString().isEmpty()  && !montant.isEmpty() && dateL){

            Facture facture = new Facture();
            facture.setIdMarConBc(this.marSelected.getId());
            facture.setNumero(numero);
            facture.setDate(date);
            facture.setMontant(Double.parseDouble(montant));

            boolean upd = update(facture,this.facture);
            if (upd) ActionAnnuler();
            else {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("Attention ");
                alertWarning.setContentText("un erreur inconnue");
                alertWarning.initOwner(this.tfOrganisme.getScene().getWindow());
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("d'accord");
                alertWarning.showAndWait();
            }

        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("Veuillez remplir les champs vides");
            alertWarning.initOwner(this.tfOrganisme.getScene().getWindow());
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void ActionAnnuler(){
        ((Stage)tfNumero.getScene().getWindow()).close();
    }

    private boolean update(Facture facture , Facture factureOld) {

        boolean update = false;
        try {
            update = operation.update(facture,factureOld);
            return update;
        }catch (Exception e){
            e.printStackTrace();
            return update;
        }
    }

}
