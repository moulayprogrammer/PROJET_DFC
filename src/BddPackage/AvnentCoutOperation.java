package BddPackage;

import Models.AvnentCout;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AvnentCoutOperation extends BDD<AvnentCout>{

    @Override
    public boolean insert(AvnentCout o) {
        boolean ins = false;
        String query = "INSERT INTO `AVENANT_COUT`(`ID_COUT`, `DATE`, `TYPE`, `MONTANT`) VALUES  (?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getIdCout());
            preparedStmt.setDate(2, Date.valueOf(o.getDate()));
            preparedStmt.setString(3,o.getType());
            preparedStmt.setDouble(4,o.getMontant());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(AvnentCout o, AvnentCout o2) {
        boolean upd = false;
        String query = "UPDATE `AVENANT_COUT` SET `DATE`=?,`TYPE`=?,`MONTANT`= ? " +
                "WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setDate(1,Date.valueOf(o.getDate()));
            preparedStmt.setString(2,o.getType());
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
    public boolean delete(AvnentCout o) {
        boolean del = false;
        String query = "DELETE FROM `AVENANT_COUT` WHERE `ID` = ? ";
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
    public boolean isExist(AvnentCout o) {
        return false;
    }

    @Override
    public ArrayList<AvnentCout> getAll() {
        return null;
    }

    public AvnentCout get(int id) {
        AvnentCout avnentCout = new AvnentCout();
        String query = "SELECT * FROM `avenant_cout` WHERE `ID` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()){

                avnentCout.setId(resultSet.getInt("ID"));
                avnentCout.setIdCout(resultSet.getInt("ID_COUT"));
                avnentCout.setDate(resultSet.getDate("DATE").toLocalDate());
                avnentCout.setType(resultSet.getString("TYPE"));
                avnentCout.setMontant(resultSet.getDouble("MONTANT"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avnentCout;
    }

    public ArrayList<AvnentCout> getAll(int idCout) {
        ArrayList<AvnentCout> list = new ArrayList<>();
        String query = "SELECT * FROM `AVENANT_COUT` WHERE `ID_COUT` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,idCout);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                AvnentCout avnentCout = new AvnentCout();
                avnentCout.setId(resultSet.getInt("ID"));
                avnentCout.setIdCout(resultSet.getInt("ID_COUT"));
                avnentCout.setDate(resultSet.getDate("DATE").toLocalDate());
                avnentCout.setType(resultSet.getString("TYPE"));
                avnentCout.setMontant(resultSet.getDouble("MONTANT"));

                list.add(avnentCout);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<AvnentCout> getAllByProjet(int idProjet) {
        ArrayList<AvnentCout> list = new ArrayList<>();
        String query = "SELECT PROJET.ID , COUT.ID , COUT.ID_PROJET , COUT.TYPE , AVENANT_COUT.ID , " +
                "AVENANT_COUT.ID_COUT, AVENANT_COUT.DATE , AVENANT_COUT.TYPE , AVENANT_COUT.MONTANT" +
                " FROM PROJET,COUT,AVENANT_COUT WHERE PROJET.ID = ? && PROJET.ID = COUT.ID_PROJET && COUT.ID = AVENANT_COUT.ID_COUT";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,idProjet);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                AvnentCout avnentCout = new AvnentCout();
                avnentCout.setId(resultSet.getInt(5));
                avnentCout.setIdCout(resultSet.getInt(6));
                avnentCout.setDate(resultSet.getDate(7).toLocalDate());
                avnentCout.setType(resultSet.getString(8));
                avnentCout.setCoutApplique(resultSet.getString(4));
                avnentCout.setMontant(resultSet.getDouble(9));

                list.add(avnentCout);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<AvnentCout> getAllByType(int idCout, String type) {
        ArrayList<AvnentCout> list = new ArrayList<>();
        String query = "SELECT * FROM `AVENANT_COUT` WHERE `ID_COUT` = ? AND  `TYPE` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,idCout);
            preparedStmt.setString(2,type);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                AvnentCout avnentCout = new AvnentCout();
                avnentCout.setId(resultSet.getInt("ID"));
                avnentCout.setIdCout(resultSet.getInt("ID_COUT"));
                avnentCout.setDate(resultSet.getDate("DATE").toLocalDate());
                avnentCout.setType(resultSet.getString("TYPE"));
                avnentCout.setMontant(resultSet.getDouble("MONTANT"));

                list.add(avnentCout);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
