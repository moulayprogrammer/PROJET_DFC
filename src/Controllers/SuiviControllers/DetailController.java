package Controllers.SuiviControllers;

import BddPackage.FactureOperation;
import BddPackage.OrderPaimentOperation;
import BddPackage.OrganismeOperation;
import Models.Facture;
import Models.MarConBc;
import Models.OrderePaiment;
import Models.Organisme;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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
    private final OrderPaimentOperation orderPaimentOperation = new OrderPaimentOperation();
    private Organisme organisme;
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
        tfTotSitTr.setText(data.get(9).getValue());
        tfTotRg.setText(data.get(10).getValue());
        tfTotPr.setText(data.get(11).getValue());
        tfTotPaye.setText(data.get(12).getValue());
        tfTotConsomation.setText(data.get(13).getValue());
        tfEcart.setText(data.get(14).getValue());
        tfTaux.setText(data.get(15).getValue());

        this.IdMar = Integer.parseInt(data.get(0).getValue());
        int idOrg = Integer.parseInt(data.get(1).getValue());

        this.organisme = organismeOperation.get(idOrg);
        tfEtb.setText(organisme.getRaisonSocial());

        refresh();
    }

    private void refresh(){

        dataTable.clear();

        List<Facture> factures = factureOperation.getAllByConvention(new MarConBc(this.IdMar));
        factures.forEach(facture -> {
            OrderePaiment orderePaiment = orderPaimentOperation.getByFacture(facture.getId());

            List<StringProperty> data = new ArrayList<>();
            data.add(0, new SimpleStringProperty(facture.getNumero()));
            data.add(1, new SimpleStringProperty(facture.getDate().toString()));
            data.add(2, new SimpleStringProperty(String.format(Locale.FRANCE,"%,.2f",facture.getMontant())));
            data.add(3, new SimpleStringProperty(String.format(Locale.FRANCE,"%,.2f",orderePaiment.getRetuneGarante())));
            data.add(4, new SimpleStringProperty(String.format(Locale.FRANCE,"%,.2f",orderePaiment.getPenaliteRotarde())));
            data.add(5, new SimpleStringProperty(orderePaiment.getNumero()));
            data.add(6, new SimpleStringProperty(orderePaiment.getDate().toString()));
            data.add(7, new SimpleStringProperty(String.format(Locale.FRANCE,"%,.2f",orderePaiment.getMontant())));

            dataTable.add(data);
        });
        table.setItems(dataTable);
    }

}
