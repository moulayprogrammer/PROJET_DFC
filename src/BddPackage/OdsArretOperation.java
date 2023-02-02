package BddPackage;

import Models.OdsArret;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OdsArretOperation extends BDD<OdsArret> {

    @Override
    public boolean insert(OdsArret o) {
        boolean ins = false;
        String query = "INSERT INTO `ods_arret`( `ID_MAR_CON_BC`, `NUMBER`, `DATE`, `RAISON`) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getIdConvention());
            preparedStmt.setString(2,o.getNumber());
            preparedStmt.setDate(3, Date.valueOf(o.getDate()));
            preparedStmt.setString(4,o.getRaison());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(OdsArret o, OdsArret o2) {
        boolean upd = false;
        String query = "UPDATE `ods_arret` SET `ID_MAR_CON_BC`= ?,`NUMBER`= ?,`DATE`= ?,`RAISON`= ? " +
                "WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getIdConvention());
            preparedStmt.setString(2,o.getNumber());
            preparedStmt.setDate(3, Date.valueOf(o.getDate()));
            preparedStmt.setString(4,o.getRaison());
            preparedStmt.setInt(5,o2.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(OdsArret o) {
        boolean del = false;
        String query = "DELETE FROM `ods_arret` WHERE `ID` = ? ";
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
    public boolean isExist(OdsArret o) {
        return false;
    }

    @Override
    public ArrayList<OdsArret> getAll() {
        ArrayList<OdsArret> list = new ArrayList<>();
        String query = "SELECT * FROM `ods_arret` ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                OdsArret odsArret = new OdsArret();

                odsArret.setId(resultSet.getInt("ID"));
                odsArret.setIdConvention(resultSet.getInt("ID_MAR_CON_BC"));
                odsArret.setNumber(resultSet.getString("NUMBER"));
                odsArret.setDate(resultSet.getDate("DATE").toLocalDate());
                odsArret.setRaison(resultSet.getString("RAISON"));

                list.add(odsArret);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public OdsArret getLast(int idConv) {
        OdsArret odsArret = new OdsArret();
        String query = "SELECT * FROM `ods_arret` WHERE `ID_MAR_CON_BC` = ? ORDER BY `DATE` DESC LIMIT 1 ;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,idConv);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                odsArret.setId(resultSet.getInt("ID"));
                odsArret.setIdConvention(resultSet.getInt("ID_MAR_CON_BC"));
                odsArret.setNumber(resultSet.getString("NUMBER"));
                odsArret.setDate(resultSet.getDate("DATE").toLocalDate());
                odsArret.setRaison(resultSet.getString("RAISON"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return odsArret;
    }
}
