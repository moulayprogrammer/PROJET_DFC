package Controllers.SuiviConventionControllers;

import BddPackage.AvnentOperation;
import BddPackage.ConnectBD;
import BddPackage.FactureOperation;
import BddPackage.OrderPaimentOperation;
import Controllers.ConventionControllers.SelectOrganismeController;
import Controllers.SuiviEtbControllers.DetailController;
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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class MainController implements Initializable {

    @FXML
    TextField tfEtb,tfRecherche;
    @FXML
    DatePicker dpFrom,dpTo;
    @FXML
    TableView<List<StringProperty>> table;
    @FXML
    TableColumn<List<StringProperty>,String> idProj,idProg,ProjetNom,ConvFNum,MontantInitR,AvnSupR,AvnDemR,totalApR,totalEngR,reliquatApR
            ,totalPaiementR,reliquatEngR,tauxR,MontantInitE,AvnSupE,AvnDemE,totalApE,totalEngE,reliquatApE,totalPaiementE,reliquatEngE,tauxE
            ,MontantInitV,AvnSupV,AvnDemV,totalApV,totalEngV,reliquatApV,totalPaiementV,reliquatEngV,tauxV,totalAp,totalEng,reliquatAp
            ,totalPaiement,reliquatEng,taux;



    private final ObservableList<List<StringProperty>> dataTable = FXCollections.observableArrayList();
    private final ConnectBD connectBD = new ConnectBD();
    private final AvnentOperation avnentOperation = new AvnentOperation();
    private final FactureOperation factureOperation = new FactureOperation();
    private final OrderPaimentOperation orderPaimentOperation = new OrderPaimentOperation();
    private Connection conn;
    private Organisme organisme;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn = connectBD.connect();
        organisme = new Organisme();

//        idConv.setCellValueFactory(data -> data.getValue().get(0));

//        refresh();
    }

    private void refresh(){

        try {
            dataTable.clear();
            String query = " SELECT `PROJET`.`ID` , `PROJET`.`NOM` , `PROJET`.`NUMERO_CF` ,  `MAR_CON_BC`.`ID` , `MAR_CON_BC`.`ID_PROJET` " +
                    ", `MAR_CON_BC`.`NOM` ,  `MAR_CON_BC`.`ID_ORGANISME` , `MAR_CON_BC`.`TTC` " +
                    "FROM `PROJET` , `MAR_CON_BC` WHERE `MAR_CON_BC`.`ARCHIVE` = 0 AND `PROJET`.`ID` = `MAR_CON_BC`.`ID_PROJET` " +
                    "ORDER BY `MAR_CON_BC`.`ID_ORGANISME`  ASC";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            ChargeTableWithoutDate(resultSet);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionDate(){
        try {
            LocalDate dateFrom = dpFrom.getValue();
            LocalDate dateTo = dpTo.getValue();

            if (dateFrom != null && dateTo != null) {

                if (tfEtb.getText().isEmpty()) {
                    dataTable.clear();
                    String query = " SELECT `PROJET`.`ID` , `PROJET`.`NOM` , `PROJET`.`NUMERO_CF` ,  `MAR_CON_BC`.`ID` , `MAR_CON_BC`.`ID_PROJET` " +
                            ", `MAR_CON_BC`.`NOM` ,  `MAR_CON_BC`.`ID_ORGANISME` , `MAR_CON_BC`.`TTC` " +
                            "FROM `PROJET` , `MAR_CON_BC` WHERE `MAR_CON_BC`.`ARCHIVE` = 0 AND  `PROJET`.`ID` = `MAR_CON_BC`.`ID_PROJET` " +
                            "ORDER BY `MAR_CON_BC`.`ID_ORGANISME`  ASC";
                    PreparedStatement preparedStmt = conn.prepareStatement(query);
                    ResultSet resultSet = preparedStmt.executeQuery();
                    ChargeTableWithDate(dateFrom, dateTo, resultSet);

                }else {
                    ChargeTableWithEtbAndDate(dateFrom, dateTo);
                }

            }else {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("Attention ");
                alertWarning.setContentText("svp choiser les date");
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("d'accord");
                alertWarning.showAndWait();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionRefreshDate(){
        dpFrom.setValue(null);
        dpTo.setValue(null);
        if (tfEtb.getText().isEmpty()) refresh();
        else refreshByOrganisme();
    }

    @FXML
    private void ActionOrganisme(){
        try {
            tfEtb.clear();
            this.organisme = new Organisme();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/SuiviEtbViews/SelectOrganismeView.fxml"));
            DialogPane temp = loader.load();
            SelectOrganismeController controller = loader.getController();
            controller.Init(this.organisme);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(temp);
            dialog.resizableProperty().setValue(false);
            dialog.showAndWait();

            if (null != this.organisme.getNif()) {
                tfEtb.setText(this.organisme.getRaisonSocial());
                refreshByOrganisme();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshByOrganisme() {
        try {
            LocalDate dateFrom = dpFrom.getValue();
            LocalDate dateTo = dpTo.getValue();

            if (this.organisme.getNif() != null) {

                if (dateFrom == null && dateTo == null) {

                    dataTable.clear();

                    String query = " SELECT `PROJET`.`ID` , `PROJET`.`NOM` , `PROJET`.`NUMERO_CF` ,  `MAR_CON_BC`.`ID` , `MAR_CON_BC`.`ID_PROJET` " +
                            ", `MAR_CON_BC`.`NOM` ,  `MAR_CON_BC`.`ID_ORGANISME` , `MAR_CON_BC`.`TTC` ,`MAR_CON_BC`.`ARCHIVE` " +
                            "FROM `PROJET` , `MAR_CON_BC` WHERE `MAR_CON_BC`.`ARCHIVE` = 0 AND `MAR_CON_BC`.`ID_ORGANISME` = ? " +
                            "AND `PROJET`.`ID` = `MAR_CON_BC`.`ID_PROJET`  ";

                    PreparedStatement preparedStmt = conn.prepareStatement(query);
                    preparedStmt.setInt(1,this.organisme.getId());
                    ResultSet resultSet = preparedStmt.executeQuery();
                    ChargeTableWithoutDate(resultSet);

                }else {

                    ChargeTableWithEtbAndDate(dateFrom, dateTo);
                }

            }else {

                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("Attention ");
                alertWarning.setContentText("svp choiser un ETB");
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("d'accord");
                alertWarning.showAndWait();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void ActionRefreshOrganisme(){
        tfEtb.clear();
        this.organisme = new Organisme();
        LocalDate dateFrom = dpFrom.getValue();
        LocalDate dateTo = dpTo.getValue();
        if (dateFrom != null && dateTo != null) {
            ActionDate();
        }else {
            refresh();
        }
    }

    @FXML
    private void ActionRecherche(){
        // filtrer les données
        ObservableList<List<StringProperty>> dataProgramme = table.getItems();
        FilteredList<List<StringProperty>> filteredData = new FilteredList<>(dataProgramme, e -> true);
        String txtRecherche = tfRecherche.getText().trim();

        filteredData.setPredicate((Predicate<? super List<StringProperty>>) stringProperties -> {
            if (txtRecherche.isEmpty()) {
                //loadDataInTable();
                return true;
            } else if (stringProperties.get(1).toString().contains(txtRecherche)) {
                return true;
            } else if (stringProperties.get(2).toString().contains(txtRecherche)) {
                return true;
            }  else return stringProperties.get(3).toString().contains(txtRecherche);
        });

        SortedList<List<StringProperty>> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);
    }

    @FXML
    private void ActionClearRecherche(){
        tfRecherche.clear();
        if (!tfEtb.getText().isEmpty()) refreshByOrganisme();
        else if (dpFrom.getValue() != null && dpTo.getValue() != null) ActionDate();
        else refresh();
    }

    @FXML
    private void SelectDetail(){
        List<StringProperty> data = table.getSelectionModel().getSelectedItem();
        try {
            if (data != null){

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/SuiviEtbViews/DetailView.fxml"));
                BorderPane temp = loader.load();
                DetailController controller = loader.getController();
                controller.Init(data);
                Stage stage = new Stage();
                stage.setScene(new Scene(temp));
                stage.resizableProperty().setValue(false);
                stage.show();


            }else {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setHeaderText("Attention ");
                alertWarning.setContentText("svp choiser un marché");
                Button okButton = (Button) alertWarning.getDialogPane().lookupButton(ButtonType.OK);
                okButton.setText("d'accord");
                alertWarning.showAndWait();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void ChargeTableWithEtbAndDate(LocalDate dateFrom, LocalDate dateTo) throws SQLException {
        dataTable.clear();

        String query = " SELECT `PROJET`.`ID` , `PROJET`.`NOM` , `PROJET`.`NUMERO_CF` ,  `MAR_CON_BC`.`ID` , `MAR_CON_BC`.`ID_PROJET` " +
                ", `MAR_CON_BC`.`NOM` ,  `MAR_CON_BC`.`ID_ORGANISME` , `MAR_CON_BC`.`TTC` ,`MAR_CON_BC`.`ARCHIVE` " +
                "FROM `PROJET` , `MAR_CON_BC` WHERE `MAR_CON_BC`.`ARCHIVE` = 0 AND `MAR_CON_BC`.`ID_ORGANISME` = ?  " +
                "AND `PROJET`.`ID` = `MAR_CON_BC`.`ID_PROJET`  ";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1,this.organisme.getId());
        ResultSet resultSet = preparedStmt.executeQuery();
        ChargeTableWithDate(dateFrom, dateTo, resultSet);
    }

    private void ChargeTableWithoutDate(ResultSet resultSet) throws SQLException {

        while (resultSet.next()){

            String projet = resultSet.getString("PROJET.NOM");
            String CF = resultSet.getString("PROJET.NUMERO_CF");
            int idMar = resultSet.getInt("MAR_CON_BC.ID");
            String marche = resultSet.getString("MAR_CON_BC.NOM");
            int idOrg = resultSet.getInt("MAR_CON_BC.ID_ORGANISME");
            double CoutMar = resultSet.getDouble("MAR_CON_BC.TTC");

            double AvnSup = avnentOperation.getSum(idMar, "SUPLEMENTAIRE") ;
            double AvnDem = avnentOperation.getSum(idMar, "DEMENITIVE") ;

            List<Facture> factures = factureOperation.getAllByConvention(new MarConBc(idMar));

            AtomicReference<Double> totSitTR  = new AtomicReference<>((double) 0);
            AtomicReference<Double> totRG = new AtomicReference<>((double) 0);
            AtomicReference<Double> totPR = new AtomicReference<>((double) 0);
            AtomicReference<Double> totPaye = new AtomicReference<>((double) 0);

            factures.forEach(facture -> {
                OrderePaiment orderePaiment = orderPaimentOperation.getByFacture(facture.getId());


                if (orderePaiment.getNumero() != null) {
                    totSitTR.updateAndGet(v -> (double) (v + facture.getMontant()));
                    totRG.updateAndGet(v -> (double) (v + orderePaiment.getRetuneGarante()));
                    totPR.updateAndGet(v -> (double) (v + orderePaiment.getPenaliteRotarde()));
                    totPaye.updateAndGet(v -> (double) (v + orderePaiment.getMontant()));
                }

            });
            List<StringProperty> data = ChargeData(projet,CF,idMar,marche,idOrg,CoutMar,AvnSup,AvnDem,totSitTR,totRG,totPR,totPaye);
            dataTable.add(data);
        }
        table.setItems(dataTable);
    }

    private void ChargeTableWithDate(LocalDate dateFrom, LocalDate dateTo, ResultSet resultSet) throws SQLException {
        while (resultSet.next()){

            String projet = resultSet.getString("PROJET.NOM");
            String CF = resultSet.getString("PROJET.NUMERO_CF");
            int idMar = resultSet.getInt("MAR_CON_BC.ID");
            String marche = resultSet.getString("MAR_CON_BC.NOM");
            int idOrg = resultSet.getInt("MAR_CON_BC.ID_ORGANISME");
            double CoutMar = resultSet.getDouble("MAR_CON_BC.TTC");

            double AvnSup = avnentOperation.getSum(idMar, "SUPLEMENTAIRE") ;
            double AvnDem = avnentOperation.getSum(idMar, "DEMENITIVE") ;

            List<Facture> factures = factureOperation.getAllByConvention(new MarConBc(idMar));

            AtomicReference<Double> totSitTR  = new AtomicReference<>((double) 0);
            AtomicReference<Double> totRG = new AtomicReference<>((double) 0);
            AtomicReference<Double> totPR = new AtomicReference<>((double) 0);
            AtomicReference<Double> totPaye = new AtomicReference<>((double) 0);

            factures.forEach(facture -> {
                OrderePaiment orderePaiment = orderPaimentOperation.getByFactureAndDate(facture.getId(),dateFrom,dateTo);


                if (orderePaiment.getNumero() != null) {
                    totSitTR.updateAndGet(v -> (double) (v + facture.getMontant()));
                    totRG.updateAndGet(v -> (double) (v + orderePaiment.getRetuneGarante()));
                    totPR.updateAndGet(v -> (double) (v + orderePaiment.getPenaliteRotarde()));
                    totPaye.updateAndGet(v -> (double) (v + orderePaiment.getMontant()));
                }

            });
            List<StringProperty> data = ChargeData(projet,CF,idMar,marche,idOrg,CoutMar,AvnSup,AvnDem,totSitTR,totRG,totPR,totPaye);
            dataTable.add(data);
        }
        table.setItems(dataTable);
    }

    private List<StringProperty> ChargeData(String projet,String CF,int idMar,String marche,int idOrg,
                                            double CoutMar, double avnSup, double avnDem, AtomicReference<Double> totSitTR, AtomicReference<Double> totRG,
                                            AtomicReference<Double> totPR, AtomicReference<Double> totPaye) {

        List<StringProperty> data = new ArrayList<>();
        try {

            double CoutEng = CoutMar + avnDem + avnSup;
            double TotCons = totPaye.get() + totPR.get();
            double Ecart = CoutEng - totPaye.get();
            int Taux = (int) ((TotCons * 100) / CoutEng);


            data.add(0, new SimpleStringProperty(String.valueOf(idMar)));
            data.add(1, new SimpleStringProperty(String.valueOf(idOrg)));
            data.add(2, new SimpleStringProperty(projet));
            data.add(3, new SimpleStringProperty(CF));
            data.add(4, new SimpleStringProperty(marche));
            data.add(5, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", CoutMar)));
            data.add(6, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", avnSup)));
            data.add(7, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", avnDem)));
            data.add(8, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", CoutEng)));
            data.add(9, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", totSitTR.get())));
            data.add(10, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", totRG.get())));
            data.add(11, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", totPR.get())));
            data.add(12, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", totPaye.get())));
            data.add(13, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", TotCons)));
            data.add(14, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", Ecart)));
            data.add(15, new SimpleStringProperty(Taux + " %"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;

    }
}
