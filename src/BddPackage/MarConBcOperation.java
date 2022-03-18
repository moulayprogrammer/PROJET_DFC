package BddPackage;

import Models.MarConBc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MarConBcOperation extends BDD<MarConBc>{

    @Override
    public boolean insert(MarConBc o) {
        boolean ins = false;
        String query = "INSERT INTO `MAR_CON_BC`(`ID_PROJET`, `ID_ORGANISME`, `NOM`, `TYPE`, `NUMERO`, `HT`, `TVA`, `TTC`, `COMPTE_NUMERO`, `COMPTE_BANCK`, `COMPTE_AGENCE`, `NUMBER_LOGTS`, `DATE`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getIdProjet());
            preparedStmt.setInt(2,o.getIdOrganisme());
            preparedStmt.setString(3,o.getNom());
            preparedStmt.setString(4,o.getType());
            preparedStmt.setString(5,o.getNumero());
            preparedStmt.setDouble(6,o.getHt());
            preparedStmt.setDouble(7,o.getTva());
            preparedStmt.setDouble(8,o.getTtc());
            preparedStmt.setString(9,o.getCompteNumero());
            preparedStmt.setString(10,o.getCompteBank());
            preparedStmt.setString(11,o.getCompteAgence());
            preparedStmt.setInt(12,o.getNumbreLogts());
            preparedStmt.setString(13,o.getDate());
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
        String query = "UPDATE `MAR_CON_BC` SET `ID_ORGANISME`= ?,`NOM`= ?,`TYPE`= ?," +
                "`NUMERO`= ?,`HT`= ?,`TVA`= ?,`TTC`= ?,`NUMBER_LOGTS`= ?,`DATE`= ? " +
                "WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getIdOrganisme());
            System.out.println("id = " + o.getIdOrganisme());
            preparedStmt.setString(2,o.getNom());
            preparedStmt.setString(3,o.getType());
            preparedStmt.setString(4,o.getNumero());
            preparedStmt.setDouble(5,o.getHt());
            preparedStmt.setDouble(6,o.getTva());
            preparedStmt.setDouble(7,o.getTtc());
            preparedStmt.setInt(8,o.getNumbreLogts());
            preparedStmt.setString(9,o.getDate());
            preparedStmt.setInt(10,o2.getId());
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
        String query = "SELECT `MAR_CON_BC`.`ID`, `ID_PROJET`, `ID_ORGANISME`, `NOM`, `TYPE`, `NUMERO`, `HT`, `TVA`, `TTC`," +
                " `COMPTE_NUMERO`, `COMPTE_BANCK`, `COMPTE_AGENCE`, `NUMBER_LOGTS`, `DATE`, `MAR_CON_BC`.`ARCHIVE` ," +
                " `ORGANISME`.`ID` , `ORGANISME`.`RAISON_SOCIAL` FROM `MAR_CON_BC` , `ORGANISME`" +
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
                marConBc.setDate(resultSet.getString("DATE"));

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
                marConBc.setDate(resultSet.getString("DATE"));

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
                marConBc.setDate(resultSet.getString("DATE"));

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

        String query = "SELECT `MAR_CON_BC`.`ID`, `ID_PROJET`, `ID_ORGANISME`, `NOM`, `TYPE`, `NUMERO`, `HT`, `TVA`, `TTC`," +
                " `COMPTE_NUMERO`, `COMPTE_BANCK`, `COMPTE_AGENCE`, `NUMBER_LOGTS`, `DATE`, `MAR_CON_BC`.`ARCHIVE` ," +
                " `ORGANISME`.`ID` , `ORGANISME`.`RAISON_SOCIAL` FROM `MAR_CON_BC` , `ORGANISME`" +
                " WHERE  `ORGANISME`.`ID` = `ID_ORGANISME` AND `MAR_CON_BC`.`ID` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()){

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
                marConBc.setDate(resultSet.getString("DATE"));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marConBc;
    }
}
