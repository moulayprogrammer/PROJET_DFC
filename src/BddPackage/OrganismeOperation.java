package BddPackage;

import Models.Organisme;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrganismeOperation extends BDD<Organisme>{

    @Override
    public boolean insert(Organisme o) {
        boolean ins = false;
        String query = "INSERT INTO `ORGANISME`(`RAISON_SOCIAL`, `ADRESSE`, `TEL`, `RC`, `NIF`) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getRaisonSocial());
            preparedStmt.setString(2,o.getAdresse());
            preparedStmt.setString(3,o.getTel());
            preparedStmt.setString(4,o.getRc());
            preparedStmt.setString(5,o.getNif());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(Organisme o, Organisme o2) {
        boolean upd = false;
        String query = "UPDATE `ORGANISME` SET `RAISON_SOCIAL`= ?,`ADRESSE`= ?,`TEL`= ?,`RC`= ?,`NIF`= ? " +
                "WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getRaisonSocial());
            preparedStmt.setString(2,o.getAdresse());
            preparedStmt.setString(3,o.getTel());
            preparedStmt.setString(4,o.getRc());
            preparedStmt.setString(5,o.getNif());
            preparedStmt.setInt(6,o2.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(Organisme o) {
        boolean del = false;
        String query = "DELETE FROM `ORGANISME` WHERE `ID` = ? ";
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
    public boolean isExist(Organisme o) {
        return false;
    }


    public Organisme get(int id) {
        Organisme organisme = new Organisme();
        String query = "SELECT * FROM `ORGANISME` WHERE `ID` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()){

                organisme.setId(resultSet.getInt("ID"));
                organisme.setRaisonSocial(resultSet.getString("RAISON_SOCIAL"));
                organisme.setAdresse(resultSet.getString("ADRESSE"));
                organisme.setTel(resultSet.getString("TEL"));
                organisme.setRc(resultSet.getString("RC"));
                organisme.setNif(resultSet.getString("NIF"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return organisme;
    }

    @Override
    public ArrayList<Organisme> getAll() {
        ArrayList<Organisme> list = new ArrayList<>();
        String query = "SELECT * FROM `ORGANISME` WHERE `ARCHIVE` = 0";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                Organisme organisme = new Organisme();
                organisme.setId(resultSet.getInt("ID"));
                organisme.setRaisonSocial(resultSet.getString("RAISON_SOCIAL"));
                organisme.setAdresse(resultSet.getString("ADRESSE"));
                organisme.setTel(resultSet.getString("TEL"));
                organisme.setRc(resultSet.getString("RC"));
                organisme.setNif(resultSet.getString("NIF"));

                list.add(organisme);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean AddToArchive(Organisme organismeArchive) {
        boolean upd = false;
        String query = "UPDATE `ORGANISME`  SET `ARCHIVE` = 1 WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,organismeArchive.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    public boolean DeleteFromArchive(Organisme organismeArchive) {
        boolean upd = false;
        String query = "UPDATE `ORGANISME`  SET `ARCHIVE` = 0 WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,organismeArchive.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    public ArrayList<Organisme> getAllArchive() {
        ArrayList<Organisme> list = new ArrayList<>();
        String query = "SELECT * FROM `ORGANISME` WHERE `ARCHIVE` = 1";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                Organisme organisme = new Organisme();
                organisme.setId(resultSet.getInt("ID"));
                organisme.setRaisonSocial(resultSet.getString("RAISON_SOCIAL"));
                organisme.setAdresse(resultSet.getString("ADRESSE"));
                organisme.setTel(resultSet.getString("TEL"));
                organisme.setRc(resultSet.getString("RC"));
                organisme.setNif(resultSet.getString("NIF"));

                list.add(organisme);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
