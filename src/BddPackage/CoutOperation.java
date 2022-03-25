package BddPackage;

import Models.Cout;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CoutOperation extends BDD<Cout>{

    @Override
    public boolean insert(Cout o) {
        boolean ins = false;
        String query = "INSERT INTO `COUT`( `ID_PROJET`, `TYPE`, `MONTANT`) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getIdProjet());
            preparedStmt.setString(2,o.getType());
            preparedStmt.setDouble(3,o.getMontant());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(Cout o, Cout o2) {
        boolean upd = false;
        String query = "UPDATE `COUT` SET `ID_PROJET`= ?,`TYPE`= ?,`MONTANT`= ? " +
                "WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getIdProjet());
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
    public boolean delete(Cout o) {
        boolean del = false;
        String query = "DELETE FROM `COUT` WHERE `ID` = ? ";
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
    public boolean isExist(Cout o) {
       return false;
    }

    @Override
    public ArrayList<Cout> getAll() {
        ArrayList<Cout> list = new ArrayList<>();
        String query = "SELECT * FROM `COUT`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                Cout cout = new Cout();
                cout.setId(resultSet.getInt("ID"));
                cout.setIdProjet(resultSet.getInt("ID_PROJET"));
                cout.setType(resultSet.getString("TYPE"));
                cout.setMontant(resultSet.getDouble("MONTANT"));


                list.add(cout);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public double getMontant(int idPr,String type) {
        double cout = 0.0;
        String query = "SELECT `MONTANT` FROM `COUT` WHERE `ID_PROJET` = ? && `TYPE` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,idPr);
            preparedStmt.setString(2,type);
            ResultSet resultSet = preparedStmt.executeQuery();

            if (resultSet.next())
                cout = resultSet.getDouble("MONTANT");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  cout;
    }

    public Cout getCoutByProjet(int idPr,String type){
        Cout cout = new Cout();
        String query = "SELECT * FROM `COUT` WHERE `ID_PROJET` = ? AND `TYPE` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,idPr);
            preparedStmt.setString(2,type);
            ResultSet resultSet = preparedStmt.executeQuery();

            if (resultSet.next()) {
                cout.setId(resultSet.getInt("ID"));
                cout.setIdProjet(resultSet.getInt("ID_PROJET"));
                cout.setType(resultSet.getString("TYPE"));
                cout.setMontant(resultSet.getDouble("MONTANT"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cout;
    }
}
