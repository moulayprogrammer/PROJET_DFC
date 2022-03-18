package BddPackage;

import Models.AvnentMarConBc;
import sun.dc.pr.PRError;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AvnentOperation extends  BDD<AvnentMarConBc>{

    @Override
    public boolean insert(AvnentMarConBc o) {
        boolean ins = false;
        String query = "INSERT INTO `AVENANT_MAR_CON_BC`( `ID_MAR_CON_BC`, `TYPE`, `DATE`, `MONTANT`) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getIdMarConBc());
            preparedStmt.setString(2,o.getType());
            preparedStmt.setString(3,o.getDate());
            preparedStmt.setDouble(4,o.getMontant());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(AvnentMarConBc o, AvnentMarConBc o2) {
        boolean upd = false;
        String query = "UPDATE `AVENANT_MAR_CON_BC` SET `TYPE`= ?,`DATE`= ?,`MONTANT`= ? " +
                "WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getType());
            preparedStmt.setString(2,o.getDate());
            preparedStmt.setDouble(3,o.getMontant());
            preparedStmt.setInt(4,o2.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(AvnentMarConBc o) {
        boolean del = false;
        String query = "DELETE FROM `AVENANT_MAR_CON_BC` WHERE `ID` = ? ";
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
    public boolean isExist(AvnentMarConBc o) {
        return false;
    }

    @Override
    public ArrayList<AvnentMarConBc> getAll() {
        ArrayList<AvnentMarConBc> list = new ArrayList<>();
        String query = "SELECT * FROM `AVENANT_MAR_CON_BC`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                AvnentMarConBc avnentMarConBc = new AvnentMarConBc();
                avnentMarConBc.setId(resultSet.getInt("ID"));
                avnentMarConBc.setIdMarConBc(resultSet.getInt("ID_MAR_CON_BC"));
                avnentMarConBc.setType(resultSet.getString("TYPE"));
                avnentMarConBc.setDate(resultSet.getString("DATE"));
                avnentMarConBc.setMontant(resultSet.getDouble("MONTANT"));

                list.add(avnentMarConBc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<AvnentMarConBc> getAllByConvention(int id) {
        ArrayList<AvnentMarConBc> list = new ArrayList<>();
        String query = "SELECT * FROM `AVENANT_MAR_CON_BC` WHERE `ID_MAR_CON_BC` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                AvnentMarConBc avnentMarConBc = new AvnentMarConBc();
                avnentMarConBc.setId(resultSet.getInt("ID"));
                avnentMarConBc.setIdMarConBc(resultSet.getInt("ID_MAR_CON_BC"));
                avnentMarConBc.setType(resultSet.getString("TYPE"));
                avnentMarConBc.setDate(resultSet.getString("DATE"));
                avnentMarConBc.setMontant(resultSet.getDouble("MONTANT"));

                list.add(avnentMarConBc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public double getSum(int idMar,String arg){
        double sum = 0.0;
        String query = "SELECT SUM(`MONTANT`) AS SOMME FROM `AVENANT_MAR_CON_BC` WHERE `ID_MAR_CON_BC` = ? AND  `TYPE` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,idMar);
            preparedStmt.setString(2,arg);
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()){
                sum = resultSet.getDouble("SOMME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }
}
