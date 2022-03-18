package BddPackage;

import Models.OrderePaiment;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrderPaimentOperation extends BDD<OrderePaiment>{

    @Override
    public boolean insert(OrderePaiment o) {
        boolean ins = false;
        String query = "INSERT INTO `ORDRE_PAIEMENT`(`ID_FACTURE`, `NUMERO`, `DATE`, `MONTANT`,`PINALITE_ROUTARD`,`RETUNE_GARANTE`)" +
                " VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getIdFacture());
            preparedStmt.setString(2,o.getNumero());
            preparedStmt.setDate(3, Date.valueOf(o.getDate()));
            preparedStmt.setDouble(4,o.getMontant());
            preparedStmt.setDouble(5,o.getPenaliteRotarde());
            preparedStmt.setDouble(6,o.getRetuneGarante());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(OrderePaiment o, OrderePaiment o2) {
        boolean upd = false;
        String query = "UPDATE `ORDRE_PAIEMENT` SET `NUMERO`= ?,`DATE`= ?,`MONTANT`= ? , `PINALITE_ROUTARD` = ? , " +
                "`RETUNE_GARANTE` = ? WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            preparedStmt.setString(1,o.getNumero());
            preparedStmt.setDate(2,Date.valueOf(o.getDate()));
            preparedStmt.setDouble(3,o.getMontant());
            preparedStmt.setDouble(4,o.getPenaliteRotarde());
            preparedStmt.setDouble(5,o.getRetuneGarante());
            preparedStmt.setInt(6,o2.getId());

            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(OrderePaiment o) {
        boolean del = false;
        String query = "DELETE FROM `ORDRE_PAIEMENT` WHERE `ID` ? ";
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
    public boolean isExist(OrderePaiment o) {
        return false;
    }

    public OrderePaiment get(int id) {
        OrderePaiment orderePaiment = new OrderePaiment();
        String query = "SELECT * FROM `ORDRE_PAIEMENT`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()){

                orderePaiment.setId(resultSet.getInt("ID"));
                orderePaiment.setIdFacture(resultSet.getInt("ID_FACTURE"));
                orderePaiment.setNumero(resultSet.getString("NUMERO"));
                orderePaiment.setDate(resultSet.getDate("DATE").toLocalDate());
                orderePaiment.setMontant(resultSet.getDouble("MONTANT"));
                orderePaiment.setPenaliteRotarde(resultSet.getDouble("PINALITE_ROUTARD"));
                orderePaiment.setRetuneGarante(resultSet.getDouble("RETUNE_GARANTE"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderePaiment;
    }


    @Override
    public ArrayList<OrderePaiment> getAll() {
        ArrayList<OrderePaiment> list = new ArrayList<>();
        String query = "SELECT * FROM `ORDRE_PAIEMENT`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                OrderePaiment orderePaiment = new OrderePaiment();
                orderePaiment.setId(resultSet.getInt("ID"));
                orderePaiment.setIdFacture(resultSet.getInt("ID_FACTURE"));
                orderePaiment.setNumero(resultSet.getString("NUMERO"));
                orderePaiment.setDate(resultSet.getDate("DATE").toLocalDate());
                orderePaiment.setMontant(resultSet.getDouble("MONTANT"));
                orderePaiment.setPenaliteRotarde(resultSet.getDouble("PINALITE_ROUTARD"));
                orderePaiment.setRetuneGarante(resultSet.getDouble("RETUNE_GARANTE"));

                list.add(orderePaiment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public OrderePaiment getByFacture(int id) {
        OrderePaiment orderePaiment = new OrderePaiment();
        String query = "SELECT * FROM `ORDRE_PAIEMENT` WHERE `ID_FACTURE` = ? ;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()){

                orderePaiment.setId(resultSet.getInt("ID"));
                orderePaiment.setIdFacture(resultSet.getInt("ID_FACTURE"));
                orderePaiment.setNumero(resultSet.getString("NUMERO"));
                orderePaiment.setDate(resultSet.getDate("DATE").toLocalDate());
                orderePaiment.setMontant(resultSet.getDouble("MONTANT"));
                orderePaiment.setPenaliteRotarde(resultSet.getDouble("PINALITE_ROUTARD"));
                orderePaiment.setRetuneGarante(resultSet.getDouble("RETUNE_GARANTE"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderePaiment;
    }

    public OrderePaiment getByFactureAndDate (int id, LocalDate From, LocalDate To) {
        OrderePaiment orderePaiment = new OrderePaiment();
        String query = "SELECT * FROM `ORDRE_PAIEMENT` WHERE `ID_FACTURE` = ? AND `DATE` BETWEEN ? AND ?;";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,id);
            preparedStmt.setDate(2,Date.valueOf(From));
            preparedStmt.setDate(3,Date.valueOf(To));
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()){

                orderePaiment.setId(resultSet.getInt("ID"));
                orderePaiment.setIdFacture(resultSet.getInt("ID_FACTURE"));
                orderePaiment.setNumero(resultSet.getString("NUMERO"));
                orderePaiment.setDate(resultSet.getDate("DATE").toLocalDate());
                orderePaiment.setMontant(resultSet.getDouble("MONTANT"));
                orderePaiment.setPenaliteRotarde(resultSet.getDouble("PINALITE_ROUTARD"));
                orderePaiment.setRetuneGarante(resultSet.getDouble("RETUNE_GARANTE"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderePaiment;
    }
}
