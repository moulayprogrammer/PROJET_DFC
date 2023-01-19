package Controllers.ProjetControllers;

import BddPackage.AvnentCoutOperation;
import BddPackage.CoutOperation;
import Models.AvnentCout;
import Models.Cout;
import Models.ModelesTabels.ProjetTable;
import Models.Projet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddAvnantController implements Initializable {


    @FXML
    TextField tfAvnantMontant;
    @FXML
    TextArea tfProjet;
    @FXML
    ComboBox<String> cbAppliqueCout,cbAvnantType;
    @FXML
    DatePicker dpAvnant;


    private Projet projet;

    private final CoutOperation coutOperation = new CoutOperation();
    private final AvnentCoutOperation operation = new AvnentCoutOperation();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void Init(Projet projet) {
        this.projet = projet;

        tfProjet.setText(projet.getNom());
        cbAppliqueCout.getItems().addAll("COUT REALISATION","COUT ETUDE","COUT VRD");
        cbAvnantType.getItems().addAll("SUPLEMENTAIRE","DEMENITIVE");
    }

    @FXML
    private void ActionAdd(){

        try {
            String CoutApplique = cbAppliqueCout.getSelectionModel().getSelectedItem();
            LocalDate dateAv = dpAvnant.getValue();
            String typeAv = cbAvnantType.getSelectionModel().getSelectedItem();
            double montantAv = Double.parseDouble(tfAvnantMontant.getText().trim());

            if (!CoutApplique.isEmpty() && dateAv != null && !typeAv.isEmpty() ){
                Cout cout = new Cout();
                if (CoutApplique.equals("COUT REALISATION")) cout = coutOperation.getCoutByProjet(projet.getId(),"REALISATION");
                if (CoutApplique.equals("COUT ETUDE")) cout = coutOperation.getCoutByProjet(projet.getId(),"ETUDE");
                if (CoutApplique.equals("COUT VRD")) cout = coutOperation.getCoutByProjet(projet.getId(),"VRD");

                if (cout.getMontant() != 0.0) {
                    if (typeAv.equals("DEMENITIVE") && montantAv > 0) montantAv = montantAv * -1;

                    AvnentCout avnentCout = new AvnentCout();
                    avnentCout.setIdCout(cout.getId());
                    avnentCout.setType(typeAv);
                    avnentCout.setDate(dateAv);
                    avnentCout.setMontant(montantAv);

                    boolean ins = insert(avnentCout);
                    if (ins) ActionAnnuler();
                }else {
                    Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                    alertWarning.setHeaderText("Attention ");
                    alertWarning.setContentText("assurez-vous que le " + CoutApplique + " pas null");
                    alertWarning.initOwner(this.tfProjet.getScene().getWindow());
                    Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                    okButton.setText("d'accord");
                    alertWarning.showAndWait();
                }

            }else {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("Attention ");
                alertWarning.setContentText("Veuillez remplir les champs vides");
                alertWarning.initOwner(this.tfProjet.getScene().getWindow());
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("d'accord");
                alertWarning.showAndWait();
            }

        }catch (Exception e){
            e.printStackTrace();
            Alert alertWarning = new Alert(Alert.AlertType.ERROR);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("un erreur inconnue");
            alertWarning.initOwner(this.tfProjet.getScene().getWindow());
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    private boolean insert(AvnentCout avnentCout){
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
        ((Stage)tfProjet.getScene().getWindow()).close();
    }
}
