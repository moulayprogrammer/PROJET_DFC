package Controllers.FactureControllers;

import BddPackage.FactureOperation;
import BddPackage.OrderPaymentOperation;
import Models.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class MainController implements Initializable {

    @FXML
    TextField tfConvention,tfOrganisme,tfRecherche;

    @FXML
    TableView<List<StringProperty>> tvFacture;
    @FXML
    TableColumn<List<StringProperty>,String> idColumn,idMarColumn,NumeroColumnn,DateColumnn,MontantColumn,OPidColumn,NumOrderColumn,dateOPColumn
            ,RGColumn,PRColumn,montantOPColumn;


    private MarConBc mar;
    private final ObservableList<List<StringProperty>> dataTable = FXCollections.observableArrayList();
    private final FactureOperation operation = new FactureOperation();
    private final OrderPaymentOperation paimentOperation = new OrderPaymentOperation();
    private ArrayList<Facture> factures;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idColumn.setCellValueFactory(data -> data.getValue().get(0));
        idMarColumn.setCellValueFactory(data -> data.getValue().get(1));
        NumeroColumnn.setCellValueFactory(data -> data.getValue().get(2));
        DateColumnn.setCellValueFactory(data -> data.getValue().get(3));
        MontantColumn.setCellValueFactory(data -> data.getValue().get(4));
        OPidColumn.setCellValueFactory(data -> data.getValue().get(5));
        NumOrderColumn.setCellValueFactory(data -> data.getValue().get(6));
        dateOPColumn.setCellValueFactory(data -> data.getValue().get(7));
        RGColumn.setCellValueFactory(data -> data.getValue().get(8));
        PRColumn.setCellValueFactory(data -> data.getValue().get(9));
        montantOPColumn.setCellValueFactory(data -> data.getValue().get(10));

        mar = new MarConBc();
        refresh();
    }

    @FXML
    private void ActionSelectConvention(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/FactureViews/SelectConventionView.fxml"));
            DialogPane temp = loader.load();
            SelectConventionController controller = loader.getController();
            controller.Init(this.mar);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.resizableProperty().setValue(false);
            dialog.initOwner(this.tfRecherche.getScene().getWindow());
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
            closeButton.setVisible(false);
            dialog.showAndWait();

            if (this.mar.getNumero() != null) {
                tfConvention.setText(this.mar.getNom());
                tfOrganisme.setText(this.mar.getNomOrganisme());
                refreshByConvention();
            }else refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionClearSelect(){
        tfConvention.clear();
        tfOrganisme.clear();
        mar = new MarConBc();
        refresh();
    }

    @FXML
    private void ActionAdd(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/FactureViews/AddView.fxml"));
            DialogPane temp = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.resizableProperty().setValue(false);
            dialog.initOwner(this.tfRecherche.getScene().getWindow());
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
            closeButton.setVisible(false);
            dialog.showAndWait();

            if(tfConvention.getText().isEmpty()) refresh();
            else refreshByConvention();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionAddOP(){
        List<StringProperty> selected = tvFacture.getSelectionModel().getSelectedItem();

        if (selected != null) {
            Facture facture = operation.get(Integer.parseInt(selected.get(0).getValue()));
            OrderePaiment op = paimentOperation.getByFacture(facture.getId());

            if (op.getId() <= 0 ) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/FactureViews/AddOpView.fxml"));
                    DialogPane temp = loader.load();
                    AddOpController controller = loader.getController();
                    controller.Init(facture);
                    Dialog<ButtonType> dialog = new Dialog<>();
                    dialog.setDialogPane(temp);
                    dialog.resizableProperty().setValue(false);
                    dialog.initOwner(this.tfRecherche.getScene().getWindow());
                    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                    Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
                    closeButton.setVisible(false);
                    dialog.showAndWait();

                    if (tfConvention.getText().isEmpty()) refresh();
                    else refreshByConvention();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("Attention ");
                alertWarning.setContentText("cette facture est déja payé");
                alertWarning.initOwner(this.tfRecherche.getScene().getWindow());
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("d'accord");
                alertWarning.showAndWait();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("svp sélectionner un facture");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }
    @FXML
    private void tableClick(MouseEvent mouseEvent) {
        if ( mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) ){

            ActionUpdate();
        }
    }
    @FXML
    private void ActionUpdate(){
        List<StringProperty> selected = tvFacture.getSelectionModel().getSelectedItem();

        if (selected != null) {
            Facture facture = operation.get(Integer.parseInt(selected.get(0).getValue()));

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/FactureViews/UpdateView.fxml"));
                DialogPane temp = loader.load();
                UpdateController controller = loader.getController();
                controller.Init(facture);
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(temp);
                dialog.resizableProperty().setValue(false);
                dialog.initOwner(this.tfRecherche.getScene().getWindow());
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
                closeButton.setVisible(false);
                dialog.showAndWait();

                if (tfConvention.getText().trim().isEmpty()) refresh();
                else refreshByConvention();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("svp sélectionner un facture");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void ActionUpdateOP(){
        List<StringProperty> selected = tvFacture.getSelectionModel().getSelectedItem();

        if (selected != null) {
            if(!selected.get(5).getValue().equals("0")) {
                Facture facture = operation.get(Integer.parseInt(selected.get(0).getValue()));
                OrderePaiment orderePaiment = paimentOperation.getByFacture(facture.getId());

                if (orderePaiment.getId() > 0) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/FactureViews/UpdateOpView.fxml"));
                        DialogPane temp = loader.load();
                        UpdateOpController controller = loader.getController();
                        controller.Init(facture, orderePaiment);
                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.setDialogPane(temp);
                        dialog.resizableProperty().setValue(false);
                        dialog.initOwner(this.tfRecherche.getScene().getWindow());
                        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
                        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
                        closeButton.setVisible(false);
                        dialog.showAndWait();

                        if (tfConvention.getText().isEmpty()) refresh();
                        else refreshByConvention();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                    alertWarning.setHeaderText("Attention ");
                    alertWarning.setContentText("cette facture n'est pas payé");
                    alertWarning.initOwner(this.tfRecherche.getScene().getWindow());
                    Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                    okButton.setText("d'accord");
                    alertWarning.showAndWait();
                }
            }else {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("Attention ");
                alertWarning.setContentText("svp sélectionner un facture payé");
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("d'accord");
                alertWarning.showAndWait();
            }
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("svp sélectionner un facture");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }


    @FXML
    private void ActionAddToArchive(){
        List<StringProperty> selected = tvFacture.getSelectionModel().getSelectedItem();

        if (selected != null) {
            Facture facture = operation.get(Integer.parseInt(selected.get(0).getValue()));

            Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirmation.setHeaderText("Confirmer l'archivation");
            alertConfirmation.setContentText("Êtes-vous sûr de vouloir archiver la facture ");
            Button okButton = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("D'accord");

            Button cancel = (Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
            cancel.setText("Annuler");

            alertConfirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.CANCEL) {
                    alertConfirmation.close();
                } else if (response == ButtonType.OK) {
                    if (operation.addToArchive(facture)){
                        if (tfConvention.getText().trim().isEmpty()) refresh();
                        else refreshByConvention();
                    }
                }
            });

        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("svp sélectionner une facture pour archiver");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void ActionListArchive(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/FactureViews/ArchiveView.fxml"));
            DialogPane temp = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.resizableProperty().setValue(false);
            dialog.initOwner(this.tfRecherche.getScene().getWindow());
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
            closeButton.setVisible(false);
            dialog.showAndWait();

            if (tfConvention.getText().isEmpty()) refresh();
            else refreshByConvention();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ActionSearch() {
        // filtrer les données
        ObservableList<List<StringProperty>> dataFacture = tvFacture.getItems();
        FilteredList<List<StringProperty>> filteredData = new FilteredList<>(dataFacture, e -> true);
        String txtRecherche = tfRecherche.getText().trim();

        filteredData.setPredicate((Predicate<? super List<StringProperty>>) facture -> {
            if (txtRecherche.isEmpty()) {
                //loadDataInTable();
                return true;
            } else if (facture.get(2).toString().contains(txtRecherche)) {
                return true;
            } else if (facture.get(3).toString().contains(txtRecherche)) {
                return true;
            }else if (facture.get(4).toString().contains(txtRecherche)) {
                return true;
            }else if (facture.get(5).toString().contains(txtRecherche)) {
                return true;
            } else return facture.get(6).toString().contains(txtRecherche);
        });

        SortedList<List<StringProperty>> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(tvFacture.comparatorProperty());
        tvFacture.setItems(sortedList);

    }

    @FXML
    private void ActionRefresh(){
        tfRecherche.clear();
        if (tfConvention.getText().trim().isEmpty()) refresh();
        else refreshByConvention();
    }

    private void refresh(){
        factures = operation.getAll();
        chargeTable();
    }

    private void refreshByConvention(){
        factures = operation.getAllByConvention(mar);
        chargeTable();
    }

    private void chargeTable(){
        try {
            dataTable.clear();

            factures.forEach(facture -> {

                List<StringProperty> data = new ArrayList<>();

                OrderePaiment orderePaiment = paimentOperation.getByFacture(facture.getId());

                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                data.add(0, new SimpleStringProperty(String.valueOf(facture.getId())));
                data.add(1, new SimpleStringProperty(String.valueOf(facture.getIdMarConBc())));
                data.add(2, new SimpleStringProperty(facture.getNumero()));
                data.add(3, new SimpleStringProperty(facture.getDate().format(dateFormatter)));
                data.add(4, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", facture.getMontant())));

                if (orderePaiment.getNumero() != null){
                    data.add(5, new SimpleStringProperty(String.valueOf(orderePaiment.getId())));
                    data.add(6, new SimpleStringProperty(orderePaiment.getNumero()));
                    data.add(7, new SimpleStringProperty(orderePaiment.getDate().format(dateFormatter)));
                    data.add(8, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", orderePaiment.getRetuneGarante())));
                    data.add(9, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", orderePaiment.getPenaliteRotarde())));
                    data.add(10, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", orderePaiment.getMontant())));
                }else {
                    data.add(5, new SimpleStringProperty(""));
                    data.add(6, new SimpleStringProperty(""));
                    data.add(7, new SimpleStringProperty(""));
                    data.add(8, new SimpleStringProperty(""));
                    data.add(9, new SimpleStringProperty(""));
                    data.add(10, new SimpleStringProperty(""));
                }

                dataTable.add(data);
            });
            tvFacture.setItems(dataTable);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
