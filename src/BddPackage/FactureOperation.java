package BddPackage;

import Models.Facture;
import Models.MarConBc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FactureOperation extends BDD<Facture>{

    @Override
    public boolean insert(Facture o) {
        boolean ins = false;
        String query = "INSERT INTO `FACTURE`(  `ID_MAR_CON_BC`, `NUMERO`, `DATE`, `MONTANT`) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getIdMarConBc());
            preparedStmt.setString(2,o.getNumero());
            preparedStmt.setDate(3, Date.valueOf(o.getDate()));
            preparedStmt.setDouble(4,o.getMontant());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(Facture o, Facture o2) {
        boolean upd = false;
        String query = "UPDATE `FACTURE` SET `ID_MAR_CON_BC`= ?, `NUMERO`= ?,`DATE`= ?,`MONTANT`= ? " +
                "WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getIdMarConBc());
            preparedStmt.setString(2,o.getNumero());
            preparedStmt.setDate(3,Date.valueOf(o.getDate()));
            preparedStmt.setDouble(4,o.getMontant());
            preparedStmt.setInt(5,o2.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(Facture o) {
        boolean del = false;
        String query = "DELETE FROM `FACTURE` WHERE `ID` = ? ";
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
    public boolean isExist(Facture o) {
        return false;
    }

    public Facture get(int id) {
        Facture facture = new Facture();
        String query = "SELECT * FROM `FACTURE` WHERE `ARCHIVE` = 0 AND `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()){

                facture.setId(resultSet.getInt("ID"));
                facture.setIdMarConBc(resultSet.getInt("ID_MAR_CON_BC"));
                facture.setNumero(resultSet.getString("NUMERO"));
                facture.setDate(resultSet.getDate("DATE").toLocalDate());
                facture.setMontant(resultSet.getDouble("MONTANT"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facture;
    }

    @Override
    public ArrayList<Facture> getAll() {
        ArrayList<Facture> list = new ArrayList<>();
        String query = "SELECT * FROM `FACTURE` WHERE `ARCHIVE` = 0";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                Facture facture = new Facture();
                facture.setId(resultSet.getInt("ID"));
                facture.setIdMarConBc(resultSet.getInt("ID_MAR_CON_BC"));
                facture.setNumero(resultSet.getString("NUMERO"));
                facture.setDate(resultSet.getDate("DATE").toLocalDate());
                facture.setMontant(resultSet.getDouble("MONTANT"));

                list.add(facture);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Facture> getAllByConvention(MarConBc mar) {
        ArrayList<Facture> list = new ArrayList<>();
        String query = "SELECT * FROM `FACTURE` WHERE `ID_MAR_CON_BC` = ? AND `ARCHIVE` = 0";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,mar.getId());
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                Facture facture = new Facture();
                facture.setId(resultSet.getInt("ID"));
                facture.setIdMarConBc(resultSet.getInt("ID_MAR_CON_BC"));
                facture.setNumero(resultSet.getString("NUMERO"));
                facture.setDate(resultSet.getDate("DATE").toLocalDate());
                facture.setMontant(resultSet.getDouble("MONTANT"));

                list.add(facture);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public boolean addToArchive(Facture facture) {
        boolean upd = false;
        String query = "UPDATE `FACTURE` SET `ARCHIVE`= 1 WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,facture.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    public boolean DeleteFromArchive(Facture facture) {
        boolean upd = false;
        String query = "UPDATE `FACTURE` SET `ARCHIVE`= 0 WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,facture.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }


    public ArrayList<Facture> getAllArchive() {
        ArrayList<Facture> list = new ArrayList<>();
        String query = "SELECT * FROM `FACTURE` WHERE `ARCHIVE` = 1";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                Facture facture = new Facture();
                facture.setId(resultSet.getInt("ID"));
                facture.setIdMarConBc(resultSet.getInt("ID_MAR_CON_BC"));
                facture.setNumero(resultSet.getString("NUMERO"));
                facture.setDate(resultSet.getDate("DATE").toLocalDate());
                facture.setMontant(resultSet.getDouble("MONTANT"));

                list.add(facture);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }


}
