package Controllers.SuiviEtbControllers;

import BddPackage.*;
import Models.Facture;
import Models.MarConBc;
import Models.OrderePaiment;
import Models.Organisme;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class DetailController implements Initializable {

    @FXML
    TextField tfProjet,tfCF,tfEtb,tfMarche,tfMontantMarche, tfTotalAvnSup,tfTotAvnDem,tfMontantEng,tfRecherche,tfTotSitTr,tfTotRg
            ,tfTotPr,tfTotPaye,tfTotConsomation,tfEcart,tfTaux;
    @FXML
    DatePicker dpDateFrom,dpDateTo;

    @FXML
    TableView<List<StringProperty>> table;
    @FXML
    TableColumn<List<StringProperty>,String> tcNumSitTR,tcDateSitTr,tcMontantSitTr,tcRg,tcPr,tcNumOrdP,tcDateOrPai,tcMontantPaye;

    private final OrganismeOperation organismeOperation = new OrganismeOperation();
    private final FactureOperation factureOperation = new FactureOperation();
    private final OrderPaymentOperation orderPaymentOperation = new OrderPaymentOperation();
    private int IdMar;

    private final ObservableList<List<StringProperty>> dataTable = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tcNumSitTR.setCellValueFactory(data -> data.getValue().get(0));
        tcDateSitTr.setCellValueFactory(data -> data.getValue().get(1));
        tcMontantSitTr.setCellValueFactory(data -> data.getValue().get(2));
        tcRg.setCellValueFactory(data -> data.getValue().get(3));
        tcPr.setCellValueFactory(data -> data.getValue().get(4));
        tcNumOrdP.setCellValueFactory(data -> data.getValue().get(5));
        tcDateOrPai.setCellValueFactory(data -> data.getValue().get(6));
        tcMontantPaye.setCellValueFactory(data -> data.getValue().get(7));
    }

    public void Init(List<StringProperty> data){
        tfProjet.setText(data.get(2).getValue());
        tfCF.setText(data.get(3).getValue());
        tfMarche.setText(data.get(4).getValue());
        tfMontantMarche.setText(data.get(5).getValue());
        tfTotalAvnSup.setText(data.get(6).getValue());
        tfTotAvnDem.setText(data.get(7).getValue());
        tfMontantEng.setText(data.get(8).getValue());
        tfTotConsomation.setText(data.get(13).getValue());
        tfEcart.setText(data.get(14).getValue());
        tfTaux.setText(data.get(15).getValue());


        this.IdMar = Integer.parseInt(data.get(0).getValue());
        int idOrg = Integer.parseInt(data.get(1).getValue());

        Organisme organisme = organismeOperation.get(idOrg);
        tfEtb.setText(organisme.getRaisonSocial());

        refresh();
    }

    @FXML
    private void ActionDate(){
        LocalDate dateFrom = dpDateFrom.getValue();
        LocalDate dateTo = dpDateTo.getValue();

        if (dateFrom != null && dateTo != null){
            refreshByDate(dateFrom,dateTo);
        }else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setHeaderText("Attention ");
            alertWarning.setContentText("svp choiser les date");
            Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText("d'accord");
            alertWarning.showAndWait();
        }
    }

    @FXML
    private void ActionRefreshDate(){
        dpDateFrom.setValue(null);
        dpDateTo.setValue(null);
        refresh();
    }

    @FXML void ActionSearch(){
        // filtrer les données
        ObservableList<List<StringProperty>> dataProgramme = table.getItems();
        FilteredList<List<StringProperty>> filteredData = new FilteredList<>(dataProgramme, e -> true);
        String txtRecherche = tfRecherche.getText().trim();

        filteredData.setPredicate((Predicate<? super List<StringProperty>>) stringProperties -> {
            if (txtRecherche.isEmpty()) {
                //loadDataInTable();
                return true;
            } else if (stringProperties.get(0).toString().contains(txtRecherche)) {
                return true;
            } else if (stringProperties.get(1).toString().contains(txtRecherche)) {
                return true;
            }else if (stringProperties.get(5).toString().contains(txtRecherche)) {
                return true;
            } else return stringProperties.get(6).toString().contains(txtRecherche);
        });

        SortedList<List<StringProperty>> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);
    }

    @FXML
    private void ActionClearRecherche(){
        tfRecherche.clear();
        if (dpDateFrom.getValue() != null && dpDateTo.getValue() != null) ActionDate();
        else refresh();
    }

    @FXML
    private void ListAvenantSup(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/SuiviEtbViews/ListAvnantView.fxml"));
            DialogPane temp = loader.load();
            ListAvnantController controller = loader.getController();
            controller.Init(this.IdMar,"SUPLEMENTAIRE");
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.resizableProperty().setValue(false);
            dialog.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ListAvenantDem(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/SuiviEtbViews/ListAvnantView.fxml"));
            DialogPane temp = loader.load();
            ListAvnantController controller = loader.getController();
            controller.Init(this.IdMar,"DEMENITIVE");
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.resizableProperty().setValue(false);
            dialog.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refresh(){

        dataTable.clear();

        List<Facture> factures = factureOperation.getAllByConvention(new MarConBc(this.IdMar));
        AtomicReference<Double> totSitTR  = new AtomicReference<>((double) 0);
        AtomicReference<Double> totRG = new AtomicReference<>((double) 0);
        AtomicReference<Double> totPR = new AtomicReference<>((double) 0);
        AtomicReference<Double> totPaye = new AtomicReference<>((double) 0);
        factures.forEach(facture -> {
            OrderePaiment orderePaiment = orderPaymentOperation.getByFacture(facture.getId());


            if (orderePaiment.getNumero() != null) {
                List<StringProperty> data = chargeData(facture, orderePaiment);

                totSitTR.updateAndGet(v ->  (v + facture.getMontant()));
                totRG.updateAndGet(v -> (v + orderePaiment.getRetuneGarante()));
                totPR.updateAndGet(v ->  (v + orderePaiment.getPenaliteRotarde()));
                totPaye.updateAndGet(v ->  (v + orderePaiment.getMontant()));
                dataTable.add(data);
            }
        });
        setTotales(totSitTR,totRG,totPR,totPaye);
        table.setItems(dataTable);
    }



    private void refreshByDate(LocalDate dateFrom, LocalDate dateTo){
        dataTable.clear();

        List<Facture> factures = factureOperation.getAllByConvention(new MarConBc(this.IdMar));
        AtomicReference<Double> totSitTR  = new AtomicReference<>((double) 0);
        AtomicReference<Double> totRG = new AtomicReference<>((double) 0);
        AtomicReference<Double> totPR = new AtomicReference<>((double) 0);
        AtomicReference<Double> totPaye = new AtomicReference<>((double) 0);
        factures.forEach(facture -> {
            OrderePaiment orderePaiment = orderPaymentOperation.getByFactureAndDate(facture.getId(),dateFrom,dateTo);


            if (orderePaiment.getNumero() != null) {
                List<StringProperty> data = chargeData(facture, orderePaiment);

                totSitTR.updateAndGet(v -> (v + facture.getMontant()));
                totRG.updateAndGet(v -> (v + orderePaiment.getRetuneGarante()));
                totPR.updateAndGet(v -> (v + orderePaiment.getPenaliteRotarde()));
                totPaye.updateAndGet(v -> (v + orderePaiment.getMontant()));
                dataTable.add(data);
            }
        });
        setTotales(totSitTR,totRG,totPR,totPaye);
        table.setItems(dataTable);
    }

    private void setTotales(AtomicReference<Double> totSitTR, AtomicReference<Double> totRG,
                            AtomicReference<Double> totPR, AtomicReference<Double> totPaye) {

        tfTotSitTr.setText(String.format(Locale.FRANCE,"%,.2f",totSitTR.get()));
        tfTotRg.setText(String.format(Locale.FRANCE,"%,.2f",totRG.get()));
        tfTotPr.setText(String.format(Locale.FRANCE,"%,.2f",totPR.get()));
        tfTotPaye.setText(String.format(Locale.FRANCE,"%,.2f",totPaye.get()));
    }

    private List<StringProperty> chargeData(Facture facture,OrderePaiment orderePaiment){
        List<StringProperty> data = new ArrayList<>();
        try {

            data.add(0, new SimpleStringProperty(facture.getNumero()));
            data.add(1, new SimpleStringProperty(facture.getDate().toString()));
            data.add(2, new SimpleStringProperty(String.format(Locale.FRANCE,"%,.2f",facture.getMontant())));
            data.add(3, new SimpleStringProperty(String.format(Locale.FRANCE,"%,.2f",orderePaiment.getRetuneGarante())));
            data.add(4, new SimpleStringProperty(String.format(Locale.FRANCE,"%,.2f",orderePaiment.getPenaliteRotarde())));
            data.add(5, new SimpleStringProperty(orderePaiment.getNumero()));
            data.add(6, new SimpleStringProperty(orderePaiment.getDate().toString()));
            data.add(7, new SimpleStringProperty(String.format(Locale.FRANCE,"%,.2f",orderePaiment.getMontant())));
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

}
