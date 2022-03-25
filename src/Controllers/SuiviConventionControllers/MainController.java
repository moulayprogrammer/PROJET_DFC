package Controllers.SuiviConventionControllers;

import BddPackage.*;
import Controllers.ConventionControllers.SelectOrganismeController;
import Controllers.SuiviEtbControllers.DetailController;
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
            ,totalPaiement,reliquatEng,taux,realiseColumn,etudeColumn,vrdColumn;



    private final ObservableList<List<StringProperty>> dataTable = FXCollections.observableArrayList();
    private final ProjetOperation projetOperation = new ProjetOperation();
    private final CoutOperation coutOperation = new CoutOperation();
    private final AvnentCoutOperation avnentCoutOperation = new AvnentCoutOperation();
    private final MarConBcOperation marConBcOperation = new MarConBcOperation();

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

        idProj.setCellValueFactory(data -> data.getValue().get(0));
        idProg.setCellValueFactory(data -> data.getValue().get(1));
        ProjetNom.setCellValueFactory(data -> data.getValue().get(2));
        ConvFNum.setCellValueFactory(data -> data.getValue().get(3));
        totalAp.setCellValueFactory(data -> data.getValue().get(4));
        totalEng.setCellValueFactory(data -> data.getValue().get(5));
        reliquatAp.setCellValueFactory(data -> data.getValue().get(6));
        totalPaiement.setCellValueFactory(data -> data.getValue().get(7));
        reliquatEng.setCellValueFactory(data -> data.getValue().get(8));
        taux.setCellValueFactory(data -> data.getValue().get(9));

        realiseColumn.setCellValueFactory(data -> data.getValue().get(10));

        MontantInitR.setCellValueFactory(data -> data.getValue().get(0));
        AvnSupR.setCellValueFactory(data -> data.getValue().get(1));
        AvnDemR.setCellValueFactory(data -> data.getValue().get(2));
        totalApR.setCellValueFactory(data -> data.getValue().get(3));
        totalEngR.setCellValueFactory(data -> data.getValue().get(4));
        reliquatApR.setCellValueFactory(data -> data.getValue().get(5));
        totalPaiementR.setCellValueFactory(data -> data.getValue().get(6));
        reliquatEngR.setCellValueFactory(data -> data.getValue().get(7));
        tauxR.setCellValueFactory(data -> data.getValue().get(8));

        etudeColumn.setCellValueFactory(data -> data.getValue().get(11));

        MontantInitE.setCellValueFactory(data -> data.getValue().get(0));
        AvnSupE.setCellValueFactory(data -> data.getValue().get(1));
        AvnDemE.setCellValueFactory(data -> data.getValue().get(2));
        totalApE.setCellValueFactory(data -> data.getValue().get(3));
        totalEngE.setCellValueFactory(data -> data.getValue().get(4));
        reliquatApE.setCellValueFactory(data -> data.getValue().get(5));
        totalPaiementE.setCellValueFactory(data -> data.getValue().get(6));
        reliquatEngE.setCellValueFactory(data -> data.getValue().get(7));
        tauxE.setCellValueFactory(data -> data.getValue().get(8));

        vrdColumn.setCellValueFactory(data -> data.getValue().get(12));

        MontantInitV.setCellValueFactory(data -> data.getValue().get(0));
        AvnSupV.setCellValueFactory(data -> data.getValue().get(1));
        AvnDemV.setCellValueFactory(data -> data.getValue().get(2));
        totalApV.setCellValueFactory(data -> data.getValue().get(3));
        totalEngV.setCellValueFactory(data -> data.getValue().get(4));
        reliquatApV.setCellValueFactory(data -> data.getValue().get(5));
        totalPaiementV.setCellValueFactory(data -> data.getValue().get(6));
        reliquatEngV.setCellValueFactory(data -> data.getValue().get(7));
        tauxV.setCellValueFactory(data -> data.getValue().get(8));


        refresh();
    }

    private void refresh(){

        try {
            dataTable.clear();

            List<Projet> projets = projetOperation.getAll();
            projets.forEach(projet -> {

                Cout coutR = coutOperation.getCoutByProjet(projet.getId(), "REALISATION");
                Cout coutE = coutOperation.getCoutByProjet(projet.getId(), "ETUDE");
                Cout coutV = coutOperation.getCoutByProjet(projet.getId(), "VRD");

                ArrayList<AvnentCout> avnentCoutsRSup = avnentCoutOperation.getAllByType(coutR.getId(),"SUPLEMENTAIRE");
                ArrayList<AvnentCout> avnentCoutsRDem = avnentCoutOperation.getAllByType(coutR.getId(),"DEMENITIVE");
                ArrayList<AvnentCout> avnentCoutsESup = avnentCoutOperation.getAllByType(coutE.getId(),"SUPLEMENTAIRE");
                ArrayList<AvnentCout> avnentCoutsEDem = avnentCoutOperation.getAllByType(coutE.getId(),"DEMENITIVE");
                ArrayList<AvnentCout> avnentCoutsVSup = avnentCoutOperation.getAllByType(coutV.getId(),"SUPLEMENTAIRE");
                ArrayList<AvnentCout> avnentCoutsVDem = avnentCoutOperation.getAllByType(coutV.getId(),"DEMENITIVE");

                double totAvnRS = avnentCoutsRSup.stream().mapToDouble(AvnentCout::getMontant).sum();
                double totAvnRD = avnentCoutsRDem.stream().mapToDouble(AvnentCout::getMontant).sum();
                double totAvnES = avnentCoutsESup.stream().mapToDouble(AvnentCout::getMontant).sum();
                double totAvnED = avnentCoutsEDem.stream().mapToDouble(AvnentCout::getMontant).sum();
                double totAvnVS = avnentCoutsVSup.stream().mapToDouble(AvnentCout::getMontant).sum();
                double totAvnVD = avnentCoutsVDem.stream().mapToDouble(AvnentCout::getMontant).sum();

                List<MarConBc> marConBcList = marConBcOperation.getAllByProjet(projet.getId());
                AtomicReference<Double> totEngMarcheR  = new AtomicReference<>((double) 0);
                AtomicReference<Double> totEngMarcheE  = new AtomicReference<>((double) 0);
                AtomicReference<Double> totEngMarcheV  = new AtomicReference<>((double) 0);
                AtomicReference<Double> totPayeR = new AtomicReference<>((double) 0);
                AtomicReference<Double> totPayeE = new AtomicReference<>((double) 0);
                AtomicReference<Double> totPayeV = new AtomicReference<>((double) 0);

                marConBcList.forEach(marConBc -> {

                    switch (marConBc.getType()) {
                        case "REALISATION":
                            double AvnSupR = avnentOperation.getSum(marConBc.getId(), "SUPLEMENTAIRE");
                            double AvnDemR = avnentOperation.getSum(marConBc.getId(), "DEMENITIVE");
                            double coutMarR = marConBc.getTtc();

                            double coutEngR = AvnSupR + AvnDemR + coutMarR;
                            totEngMarcheR.updateAndGet(v -> (double) (v + coutEngR));

                            List<Facture> facturesR = factureOperation.getAllByConvention(new MarConBc(marConBc.getId()));
                            facturesR.forEach(facture -> {
                                OrderePaiment orderePaiment = orderPaimentOperation.getByFacture(facture.getId());
                                if (orderePaiment.getNumero() != null) {
                                    totPayeR.updateAndGet(v -> (double) (v + orderePaiment.getMontant()));
                                }

                            });
                            break;
                        case "ETUDE":
                            double AvnSupE = avnentOperation.getSum(marConBc.getId(), "SUPLEMENTAIRE");
                            double AvnDemE = avnentOperation.getSum(marConBc.getId(), "DEMENITIVE");
                            double coutMarE = marConBc.getTtc();

                            double coutEngE = AvnSupE + AvnDemE + coutMarE;
                            totEngMarcheE.updateAndGet(v -> (double) (v + coutEngE));

                            List<Facture> facturesE = factureOperation.getAllByConvention(new MarConBc(marConBc.getId()));
                            facturesE.forEach(facture -> {
                                OrderePaiment orderePaiment = orderPaimentOperation.getByFacture(facture.getId());
                                if (orderePaiment.getNumero() != null) {
                                    totPayeE.updateAndGet(v -> (double) (v + orderePaiment.getMontant()));
                                }

                            });
                            break;
                        case "VRD":
                            double AvnSupV = avnentOperation.getSum(marConBc.getId(), "SUPLEMENTAIRE");
                            double AvnDemV = avnentOperation.getSum(marConBc.getId(), "DEMENITIVE");
                            double coutMarV = marConBc.getTtc();

                            double coutEngV = AvnSupV + AvnDemV + coutMarV;
                            totEngMarcheV.updateAndGet(v -> (double) (v + coutEngV));

                            List<Facture> facturesV = factureOperation.getAllByConvention(new MarConBc(marConBc.getId()));
                            facturesV.forEach(facture -> {
                                OrderePaiment orderePaiment = orderPaimentOperation.getByFacture(facture.getId());
                                if (orderePaiment.getNumero() != null) {
                                    totPayeV.updateAndGet(v -> (double) (v + orderePaiment.getMontant()));
                                }

                            });
                            break;
                    }
                });
                List<StringProperty> list = ChargeData(projet.getIdProgramme(),projet.getId(),projet.getNom(),projet.getNumeroCF(),coutR.getMontant(),coutE.getMontant(),coutV.getMontant(),
                        totAvnRS,totAvnRD,totAvnES,totAvnED,totAvnVS,totAvnVD,totEngMarcheR,totEngMarcheE,totEngMarcheV,totPayeR,totPayeE,totPayeV) ;
                dataTable.add(list);
            });
            table.setItems(dataTable);

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

/*
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
*/
    }

    private void ChargeTableWithDate(LocalDate dateFrom, LocalDate dateTo, ResultSet resultSet) throws SQLException {
/*
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
*/
    }

    private List<StringProperty> ChargeData(int idProg,int idProjet, String projet,String CF,double coutR,double coutE,double coutV,
                                            double totAvnRS, double totAvnRD, double totAvnES, double totAvnED, double totAvnVS, double totAvnVD,
                                            AtomicReference<Double> totEngMarcheR, AtomicReference<Double> totEngMarcheE, AtomicReference<Double> totEngMarcheV,
                                            AtomicReference<Double> totPayeR, AtomicReference<Double> totPayeE, AtomicReference<Double> totPayeV) {

        List<StringProperty> data = new ArrayList<>();
        try {

            double totalAp = coutR + coutE + coutV + totAvnRS + totAvnRD + totAvnES + totAvnED + totAvnVS + totAvnVD;
            double totalEng = totEngMarcheR.get() + totEngMarcheE.get() + totEngMarcheV.get();
            double reliquatAp = totalAp - totalEng;
            double totalPaiement = totPayeR.get() + totPayeE.get() + totPayeV.get();
            double reliquatEng = totalEng - totalPaiement;
            int taux = (int) ((totalPaiement * 100) / totalEng);


            data.add(0, new SimpleStringProperty(String.valueOf(idProg)));
            data.add(1, new SimpleStringProperty(String.valueOf(idProjet)));
            data.add(2, new SimpleStringProperty(projet));
            data.add(3, new SimpleStringProperty(CF));
            data.add(4, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", totalAp)));
            data.add(5, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", totalEng)));
            data.add(6, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", reliquatAp)));
            data.add(7, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", totalPaiement)));
            data.add(8, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", reliquatEng)));
            data.add(9, new SimpleStringProperty(taux + " %"));


/*            data.add(10, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", totRG.get())));
            data.add(11, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", totPR.get())));
            data.add(12, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", totPaye.get())));
            data.add(13, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", TotCons)));
            data.add(14, new SimpleStringProperty(String.format(Locale.FRANCE, "%,.2f", Ecart)));
            data.add(15, new SimpleStringProperty(Taux + " %"));*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;

    }
}
