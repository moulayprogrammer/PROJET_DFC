package BddPackage;

import Models.AvnentCompteMarConBc;
import Models.AvnentMarConBc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AvnentCompteOperation extends  BDD<AvnentCompteMarConBc>{

    @Override
    public boolean insert(AvnentCompteMarConBc o) {
        boolean ins = false;
        String query = "INSERT INTO `avenant_compte_mar_con_bc`( `ID_MAR_CON_BC`, `NUMERO`, `DATE`, `COMPTE_NUMERO`, `AGENCE`, `BANK`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getIdMarConBc());
            preparedStmt.setString(2,o.getNumero());
            preparedStmt.setDate(3, Date.valueOf(o.getDate()));
            preparedStmt.setString(4,o.getCompteNumero());
            preparedStmt.setString(5,o.getCompteAgence());
            preparedStmt.setString(6,o.getCompteBank());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(AvnentCompteMarConBc o1, AvnentCompteMarConBc o2) {
        boolean upd = false;
        String query = "UPDATE `avenant_compte_mar_con_bc` SET `NUMERO`= ?, `DATE`= ?, `COMPTE_NUMERO`= ?, " +
                " `AGENCE`= ?, `BANK`= ? ,`UPDATE_LE`= CURRENT_TIMESTAMP " +
                " WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o1.getNumero());
            preparedStmt.setDate(2,Date.valueOf(o1.getDate()));
            preparedStmt.setString(3,o1.getCompteNumero());
            preparedStmt.setString(4,o1.getCompteAgence());
            preparedStmt.setString(5,o1.getCompteBank());
            preparedStmt.setInt(6,o2.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(AvnentCompteMarConBc o) {
        boolean del = false;
        String query = "DELETE FROM `avenant_compte_mar_con_bc` WHERE `ID` = ? ";
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
    public boolean isExist(AvnentCompteMarConBc o) {
        return false;
    }


    @Override
    public ArrayList<AvnentCompteMarConBc> getAll() {
        ArrayList<AvnentCompteMarConBc> list = new ArrayList<>();
        String query = "SELECT * FROM `avenant_compte_mar_con_bc`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                AvnentCompteMarConBc avnentMarConBc = new AvnentCompteMarConBc();

                avnentMarConBc.setId(resultSet.getInt("ID"));
                avnentMarConBc.setIdMarConBc(resultSet.getInt("ID_MAR_CON_BC"));
                avnentMarConBc.setNumero(resultSet.getString("NUMERO"));
                avnentMarConBc.setDate(resultSet.getDate("DATE").toLocalDate());
                avnentMarConBc.setCompteNumero(resultSet.getString("COMPTE_NUMERO"));
                avnentMarConBc.setCompteAgence(resultSet.getString("AGENCE"));
                avnentMarConBc.setCompteBank(resultSet.getString("BANK"));
                avnentMarConBc.setCreeLe(resultSet.getTimestamp("CREE_LE").toLocalDateTime());
                avnentMarConBc.setUpdateLe(resultSet.getTimestamp("UPDATE_LE").toLocalDateTime());

                list.add(avnentMarConBc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<AvnentCompteMarConBc> getAllByConvention(int id) {
        ArrayList<AvnentCompteMarConBc> list = new ArrayList<>();
        String query = "SELECT * FROM `avenant_compte_mar_con_bc` WHERE `ID_MAR_CON_BC` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                AvnentCompteMarConBc avnentMarConBc = new AvnentCompteMarConBc();

                avnentMarConBc.setId(resultSet.getInt("ID"));
                avnentMarConBc.setIdMarConBc(resultSet.getInt("ID_MAR_CON_BC"));
                avnentMarConBc.setNumero(resultSet.getString("NUMERO"));
                avnentMarConBc.setDate(resultSet.getDate("DATE").toLocalDate());
                avnentMarConBc.setCompteNumero(resultSet.getString("COMPTE_NUMERO"));
                avnentMarConBc.setCompteAgence(resultSet.getString("AGENCE"));
                avnentMarConBc.setCompteBank(resultSet.getString("BANK"));
                avnentMarConBc.setCreeLe(resultSet.getTimestamp("CREE_LE").toLocalDateTime());
                avnentMarConBc.setUpdateLe(resultSet.getTimestamp("UPDATE_LE").toLocalDateTime());

                list.add(avnentMarConBc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
