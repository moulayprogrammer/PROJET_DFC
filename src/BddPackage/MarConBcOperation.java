package BddPackage;

import Models.MarConBc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MarConBcOperation extends BDD<MarConBc>{

    @Override
    public boolean insert(MarConBc o) {
        boolean ins = false;
        String query = "INSERT INTO `MAR_CON_BC`(`ID_PROJET`, `ID_ORGANISME`, `NOM`, `TYPE`, `NUMERO`," +
                " `REBRIQUE`, `OBJECT`, `HT`, `TVA`, `TTC`, `COMPTE_NUMERO`, `COMPTE_BANCK`, `COMPTE_AGENCE`, `NUMBER_LOGTS`," +
                " `DATE`, `TYPE_DUREE`, `DUREE`, `ODS`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getIdProjet());
            preparedStmt.setInt(2,o.getIdOrganisme());
            preparedStmt.setString(3,o.getNom());
            preparedStmt.setString(4,o.getType());
            preparedStmt.setString(5,o.getNumero());
            preparedStmt.setString(6,o.getRebrique());
            preparedStmt.setString(7,o.getObject());
            preparedStmt.setDouble(8,o.getHt());
            preparedStmt.setDouble(9,o.getTva());
            preparedStmt.setDouble(10,o.getTtc());
            preparedStmt.setString(11,o.getCompteNumero());
            preparedStmt.setString(12,o.getCompteBank());
            preparedStmt.setString(13,o.getCompteAgence());
            preparedStmt.setInt(14,o.getNumbreLogts());
            preparedStmt.setDate(15, Date.valueOf(o.getDate()));
            preparedStmt.setString(16, o.getTypeDuree());
            preparedStmt.setInt(17, o.getDuree());
            preparedStmt.setDate(18, Date.valueOf(o.getOds()));
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(MarConBc o, MarConBc o2) {
        boolean upd = false;
        String query = "UPDATE `mar_con_bc` SET `ID_PROJET`= ?,`ID_ORGANISME`= ?,`NOM`= ?,`TYPE`= ?, `REBRIQUE`= ?," +
                " `OBJECT`= ?," +
                " `NUMERO`= ? ,`HT`= ?,`TVA`= ?,`TTC`= ?,`COMPTE_NUMERO`= ?,`COMPTE_BANCK`= ?,`COMPTE_AGENCE`= ?," +
                " `NUMBER_LOGTS`= ?,`DATE`= ?,`TYPE_DUREE`= ?,`DUREE`= ?,`ODS`= ? WHERE `ID` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getIdProjet());
            preparedStmt.setInt(2,o.getIdOrganisme());
            preparedStmt.setString(3,o.getNom());
            preparedStmt.setString(4,o.getType());
            preparedStmt.setString(5,o.getRebrique());
            preparedStmt.setString(6,o.getObject());
            preparedStmt.setString(7,o.getNumero());
            preparedStmt.setDouble(8,o.getHt());
            preparedStmt.setDouble(9,o.getTva());
            preparedStmt.setDouble(10,o.getTtc());
            preparedStmt.setString(11,o.getCompteNumero());
            preparedStmt.setString(12,o.getCompteBank());
            preparedStmt.setString(13,o.getCompteAgence());
            preparedStmt.setInt(14,o.getNumbreLogts());
            preparedStmt.setDate(15,Date.valueOf(o.getDate()));
            preparedStmt.setString(16,o.getTypeDuree());
            preparedStmt.setInt(17,o.getDuree());
            preparedStmt.setDate(18,Date.valueOf(o.getOds()));
            preparedStmt.setInt(19,o2.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(MarConBc o) {
        boolean del = false;
        String query = "DELETE FROM `MAR_CON_BC` WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getId());
            int delete = preparedStmt.executeUpdate();
            if(delete != -1) del = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return del;
    }

    @Override
    public boolean isExist(MarConBc o) {
        return false;
    }

    @Override
    public ArrayList<MarConBc> getAll() {
        ArrayList<MarConBc> list = new ArrayList<>();
        String query = "SELECT `MAR_CON_BC`.`ID`, `ID_PROJET`, `ID_ORGANISME`, `NUMERO`, `NOM`, `TYPE`, `REBRIQUE`, `OBJECT`" +
                ", `DATE`, `NUMBER_LOGTS`, `DUREE`, `TYPE_DUREE`, `ODS`, `HT`, `TVA`, `TTC`, `COMPTE_NUMERO`" +
                ", `COMPTE_BANCK`, `COMPTE_AGENCE` " +
                ", `ORGANISME`.`RAISON_SOCIAL` FROM `MAR_CON_BC` , `ORGANISME`" +
                " WHERE  `ORGANISME`.`ID` = `ID_ORGANISME` AND `MAR_CON_BC`.`ARCHIVE` = 0";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                MarConBc marConBc = new MarConBc();
                marConBc.setId(resultSet.getInt("ID"));
                marConBc.setIdProjet(resultSet.getInt("ID_PROJET"));
                marConBc.setIdOrganisme(resultSet.getInt("ID_ORGANISME"));
                marConBc.setNomOrganisme(resultSet.getString("RAISON_SOCIAL"));
                marConBc.setNumero(resultSet.getString("NUMERO"));
                marConBc.setNom(resultSet.getString("NOM"));
                marConBc.setType(resultSet.getString("TYPE"));
                marConBc.setRebrique(resultSet.getString("REBRIQUE"));
                marConBc.setObject(resultSet.getString("OBJECT"));
                marConBc.setDate(resultSet.getDate("DATE").toLocalDate());
                marConBc.setNumbreLogts(resultSet.getInt("NUMBER_LOGTS"));
                marConBc.setDuree(resultSet.getInt("DUREE"));
                marConBc.setTypeDuree(resultSet.getString("TYPE_DUREE"));
                marConBc.setOds(resultSet.getDate("ODS").toLocalDate());
                marConBc.setHt(resultSet.getDouble("HT"));
                marConBc.setTva(resultSet.getDouble("TVA"));
                marConBc.setTtc(resultSet.getDouble("TTC"));
                marConBc.setCompteNumero(resultSet.getString("COMPTE_NUMERO"));
                marConBc.setCompteBank(resultSet.getString("COMPTE_BANCK"));
                marConBc.setCompteAgence(resultSet.getString("COMPTE_AGENCE"));

                list.add(marConBc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<MarConBc> getAllByProjet(int idPrj) {
        ArrayList<MarConBc> list = new ArrayList<>();
        String query = "SELECT `MAR_CON_BC`.`ID`, `ID_PROJET`, `ID_ORGANISME`, `NOM`, `TYPE`, `NUMERO`, `HT`, `TVA`, `TTC`, `COMPTE_NUMERO`," +
                " `COMPTE_BANCK`, `COMPTE_AGENCE`, `NUMBER_LOGTS`, `DATE`, `MAR_CON_BC`.`ARCHIVE` , `ORGANISME`.`ID` , " +
                " `TYPE_DUREE`, `DUREE`, `ODS`, " +
                "`ORGANISME`.`RAISON_SOCIAL` FROM `MAR_CON_BC` , `ORGANISME` WHERE  `ORGANISME`.`ID` = `ID_ORGANISME` AND " +
                " `ID_PROJET` = ? AND `MAR_CON_BC`.`ARCHIVE` = 0";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,idPrj);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                MarConBc marConBc = new MarConBc();
                marConBc.setId(resultSet.getInt("ID"));
                marConBc.setIdProjet(resultSet.getInt("ID_PROJET"));
                marConBc.setIdOrganisme(resultSet.getInt("ID_ORGANISME"));
                marConBc.setNomOrganisme(resultSet.getString("RAISON_SOCIAL"));
                marConBc.setNom(resultSet.getString("NOM"));
                marConBc.setType(resultSet.getString("TYPE"));
                marConBc.setNumero(resultSet.getString("NUMERO"));
                marConBc.setHt(resultSet.getDouble("HT"));
                marConBc.setTva(resultSet.getDouble("TVA"));
                marConBc.setTtc(resultSet.getDouble("TTC"));
                marConBc.setCompteNumero(resultSet.getString("COMPTE_NUMERO"));
                marConBc.setCompteBank(resultSet.getString("COMPTE_BANCK"));
                marConBc.setCompteAgence(resultSet.getString("COMPTE_AGENCE"));
                marConBc.setNumbreLogts(resultSet.getInt("NUMBER_LOGTS"));
                marConBc.setDate(resultSet.getDate("DATE").toLocalDate());
                marConBc.setTypeDuree(resultSet.getString("TYPE_DUREE"));
                marConBc.setDuree(resultSet.getInt("DUREE"));
                marConBc.setOds(resultSet.getDate("ODS").toLocalDate());

                list.add(marConBc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<MarConBc> getAllArchived() {
        ArrayList<MarConBc> list = new ArrayList<>();
        String query = "SELECT `MAR_CON_BC`.`ID`, `ID_PROJET`, `ID_ORGANISME`, `NOM`, `TYPE`, `NUMERO`, `HT`, `TVA`, `TTC`," +
                " `COMPTE_NUMERO`, `COMPTE_BANCK`, `COMPTE_AGENCE`, `NUMBER_LOGTS`, `DATE`, `MAR_CON_BC`.`ARCHIVE` ," +
                " `TYPE_DUREE`, `DUREE`, `ODS`, " +
                " `ORGANISME`.`ID` , `ORGANISME`.`RAISON_SOCIAL` FROM `MAR_CON_BC` , `ORGANISME`" +
                " WHERE  `ORGANISME`.`ID` = `ID_ORGANISME` AND `MAR_CON_BC`.`ARCHIVE` = 1";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                MarConBc marConBc = new MarConBc();
                marConBc.setId(resultSet.getInt("ID"));
                marConBc.setIdProjet(resultSet.getInt("ID_PROJET"));
                marConBc.setIdOrganisme(resultSet.getInt("ID_ORGANISME"));
                marConBc.setNomOrganisme(resultSet.getString("RAISON_SOCIAL"));
                marConBc.setNom(resultSet.getString("NOM"));
                marConBc.setType(resultSet.getString("TYPE"));
                marConBc.setNumero(resultSet.getString("NUMERO"));
                marConBc.setHt(resultSet.getDouble("HT"));
                marConBc.setTva(resultSet.getDouble("TVA"));
                marConBc.setTtc(resultSet.getDouble("TTC"));
                marConBc.setCompteNumero(resultSet.getString("COMPTE_NUMERO"));
                marConBc.setCompteBank(resultSet.getString("COMPTE_BANCK"));
                marConBc.setCompteAgence(resultSet.getString("COMPTE_AGENCE"));
                marConBc.setNumbreLogts(resultSet.getInt("NUMBER_LOGTS"));
                marConBc.setDate(resultSet.getDate("DATE").toLocalDate());
                marConBc.setTypeDuree(resultSet.getString("TYPE_DUREE"));
                marConBc.setDuree(resultSet.getInt("DUREE"));
                marConBc.setOds(resultSet.getDate("ODS").toLocalDate());

                list.add(marConBc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addToArchive(MarConBc o) {
        boolean upd = false;
        String query = "UPDATE `MAR_CON_BC` SET `ARCHIVE` = 1 WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    public boolean deleteFromArchive(MarConBc o) {
        boolean upd = false;
        String query = "UPDATE `MAR_CON_BC` SET `ARCHIVE` = 0 WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    public MarConBc get(Integer id) {
        MarConBc marConBc = new MarConBc();

        String query ="SELECT `MAR_CON_BC`.`ID`, `ID_PROJET`, `ID_ORGANISME`, `NUMERO`, `NOM`, `TYPE`, `REBRIQUE`, `OBJECT`" +
                ", `DATE`, `NUMBER_LOGTS`, `DUREE`, `TYPE_DUREE`, `ODS`, `HT`, `TVA`, `TTC`, `COMPTE_NUMERO`" +
                ", `COMPTE_BANCK`, `COMPTE_AGENCE` " +
                ", `ORGANISME`.`RAISON_SOCIAL` FROM `MAR_CON_BC` , `ORGANISME`" +
                " WHERE  `ORGANISME`.`ID` = `ID_ORGANISME` AND `MAR_CON_BC`.`ARCHIVE` = 0 AND `MAR_CON_BC`.`ID` = ? ";

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()){

                marConBc.setId(resultSet.getInt("ID"));
                marConBc.setIdProjet(resultSet.getInt("ID_PROJET"));
                marConBc.setIdOrganisme(resultSet.getInt("ID_ORGANISME"));
                marConBc.setNomOrganisme(resultSet.getString("RAISON_SOCIAL"));
                marConBc.setNumero(resultSet.getString("NUMERO"));
                marConBc.setNom(resultSet.getString("NOM"));
                marConBc.setType(resultSet.getString("TYPE"));
                marConBc.setRebrique(resultSet.getString("REBRIQUE"));
                marConBc.setObject(resultSet.getString("OBJECT"));
                marConBc.setDate(resultSet.getDate("DATE").toLocalDate());
                marConBc.setNumbreLogts(resultSet.getInt("NUMBER_LOGTS"));
                marConBc.setDuree(resultSet.getInt("DUREE"));
                marConBc.setTypeDuree(resultSet.getString("TYPE_DUREE"));
                marConBc.setOds(resultSet.getDate("ODS").toLocalDate());
                marConBc.setHt(resultSet.getDouble("HT"));
                marConBc.setTva(resultSet.getDouble("TVA"));
                marConBc.setTtc(resultSet.getDouble("TTC"));
                marConBc.setCompteNumero(resultSet.getString("COMPTE_NUMERO"));
                marConBc.setCompteBank(resultSet.getString("COMPTE_BANCK"));
                marConBc.setCompteAgence(resultSet.getString("COMPTE_AGENCE"));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marConBc;
    }
}
