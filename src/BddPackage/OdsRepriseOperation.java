package BddPackage;

import Models.OdsReprise;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OdsRepriseOperation extends BDD<OdsReprise> {

    @Override
    public boolean insert(OdsReprise o) {
        boolean ins = false;
        String query = "INSERT INTO `ods_reprise` (`ID_MAR_CON_BC`, `NUMBER`, `DATE`) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getIdConvention());
            preparedStmt.setString(2,o.getNumber());
            preparedStmt.setDate(3, Date.valueOf(o.getDate()));
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(OdsReprise o, OdsReprise o2) {
        boolean upd = false;
        String query = "UPDATE `ods_reprise` SET `NUMBER`= ?,`DATE`= ? " +
                "WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getNumber());
            preparedStmt.setDate(2, Date.valueOf(o.getDate()));
            preparedStmt.setInt(3,o2.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(OdsReprise o) {
        boolean del = false;
        String query = "DELETE FROM `ods_reprise` WHERE `ID` = ? ";
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
    public boolean isExist(OdsReprise o) {
        return false;
    }

    @Override
    public ArrayList<OdsReprise> getAll() {
        ArrayList<OdsReprise> list = new ArrayList<>();
        String query = "SELECT * FROM `ods_reprise` ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                OdsReprise odsReprise = new OdsReprise();

                odsReprise.setId(resultSet.getInt("ID"));
                odsReprise.setIdConvention(resultSet.getInt("ID_MAR_CON_BC"));
                odsReprise.setNumber(resultSet.getString("NUMBER"));
                odsReprise.setDate(resultSet.getDate("DATE").toLocalDate());

                list.add(odsReprise);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public OdsReprise getLast(int idConv) {
        OdsReprise odsReprise = new OdsReprise();
        String query = "SELECT * FROM `ods_reprise` WHERE `ID_MAR_CON_BC` = ? ORDER BY `DATE` DESC LIMIT 1 ;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,idConv);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                odsReprise.setId(resultSet.getInt("ID"));
                odsReprise.setIdConvention(resultSet.getInt("ID_MAR_CON_BC"));
                odsReprise.setNumber(resultSet.getString("NUMBER"));
                odsReprise.setDate(resultSet.getDate("DATE").toLocalDate());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return odsReprise;
    }

    public ArrayList<OdsReprise> getAllByConvention(int idConv) {
        ArrayList<OdsReprise> list = new ArrayList<>();
        String query = "SELECT * FROM `ods_reprise` WHERE `ID_MAR_CON_BC` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,idConv);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                OdsReprise odsReprise = new OdsReprise();

                odsReprise.setId(resultSet.getInt("ID"));
                odsReprise.setIdConvention(resultSet.getInt("ID_MAR_CON_BC"));
                odsReprise.setNumber(resultSet.getString("NUMBER"));
                odsReprise.setDate(resultSet.getDate("DATE").toLocalDate());

                list.add(odsReprise);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
