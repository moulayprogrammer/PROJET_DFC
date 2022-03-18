package BddPackage;

import Models.Programme;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProgrammeOperation extends BDD<Programme> {

    @Override
    public boolean insert(Programme o) {
        boolean ins = false;
        String query = "INSERT INTO `PROGRAMME`( `CODE`, `NOM_PROGRAMME`, `NOMBRE_LOGTS`, `NUMERO_CD`, `DATE_INSCRIPTION`) VALUES  (?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getCode());
            preparedStmt.setString(2,o.getNomProgramme());
            preparedStmt.setInt(3,o.getNombreLogts());
            preparedStmt.setString(4,o.getNumeroCD());
            preparedStmt.setString(5,o.getDateInscription());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(Programme o1, Programme o2) {
        boolean upd = false;
        String query = "UPDATE `PROGRAMME` SET `CODE` = ?, `NOM_PROGRAMME`= ?,`NOMBRE_LOGTS`= ?,`NUMERO_CD`=?,`DATE_INSCRIPTION`= ? " +
                "WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o1.getCode());
            preparedStmt.setString(2,o1.getNomProgramme());
            preparedStmt.setInt(3,o1.getNombreLogts());
            preparedStmt.setString(4,o1.getNumeroCD());
            preparedStmt.setString(5,o1.getDateInscription());
            preparedStmt.setInt(6,o2.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(Programme o) {
        boolean del = false;
        String query = "DELETE FROM `PROGRAMME` WHERE `ID` = ? ";
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
    public boolean isExist(Programme o) {
        return false;
    }

    @Override
    public ArrayList<Programme> getAll() {
        ArrayList<Programme> list = new ArrayList<>();
        String query = "SELECT * FROM `PROGRAMME` WHERE `ARCHIVE` = 0";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                Programme programme = new Programme();
                programme.setId(resultSet.getInt("ID"));
                programme.setCode(resultSet.getString("CODE"));
                programme.setNomProgramme(resultSet.getString("NOM_PROGRAMME"));
                programme.setNombreLogts(resultSet.getInt("NOMBRE_LOGTS"));
                programme.setNumeroCD(resultSet.getString("NUMERO_CD"));
                programme.setDateInscription(resultSet.getString("DATE_INSCRIPTION"));

                list.add(programme);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Programme> getAllBy(String cause,String rsh) {
        ArrayList<Programme> list = new ArrayList<>();
        String query;
        switch (cause){
            case "code":
                query = "SELECT * FROM `PROGRAMME` WHERE `CODE` LIKE  ? && `ARCHIVE` = 0;";
                break;
            case "date inscrit":
                query = "SELECT * FROM `PROGRAMME` WHERE `DATE_INSCRIPTION` LIKE  ? &&  `ARCHIVE` = 0;";
                break;
            case "convention":
                query = "SELECT * FROM `PROGRAMME` WHERE `NUMERO_CD` LIKE  ?  &&  `ARCHIVE` = 0;";
                break;
            case "nb logts":
                query = "SELECT * FROM `PROGRAMME` WHERE `NOMBRE_LOGTS` LIKE  ?  &&  `ARCHIVE` = 0";
                break;
            default:
                query = "SELECT * FROM `PROGRAMME` WHERE `CODE` LIKE  ? &&  `ARCHIVE` = 0";
                break;
        }

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,"%" + rsh + "%");
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                Programme programme = new Programme();
                programme.setId(resultSet.getInt("ID"));
                programme.setCode(resultSet.getString("CODE"));
                programme.setNomProgramme(resultSet.getString("NOM_PROGRAMME"));
                programme.setNombreLogts(resultSet.getInt("NOMBRE_LOGTS"));
                programme.setNumeroCD(resultSet.getString("NUMERO_CD"));
                programme.setDateInscription(resultSet.getString("DATE_INSCRIPTION"));

                list.add(programme);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean AddToArchive(Programme programme){
        boolean upd = false;
        String query = "UPDATE `PROGRAMME` SET `ARCHIVE` = 1 WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,programme.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    public boolean DeleteFromArchive(Programme programme){
        boolean upd = false;
        String query = "UPDATE `PROGRAMME` SET `ARCHIVE` = 0 WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,programme.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    public ArrayList<Programme> getAllArchiver() {
        ArrayList<Programme> list = new ArrayList<>();
        String query = "SELECT * FROM `PROGRAMME` WHERE `ARCHIVE` = 1";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){

                Programme programme = new Programme();
                programme.setId(resultSet.getInt("ID"));
                programme.setCode(resultSet.getString("CODE"));
                programme.setNomProgramme(resultSet.getString("NOM_PROGRAMME"));
                programme.setNumeroCD(resultSet.getString("NUMERO_CD"));

                list.add(programme);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Programme> getAllArchiverBy(String cause, String rch) {
        ArrayList<Programme> list = new ArrayList<>();
        String query;
        switch (cause){
            case "code":
                query = "SELECT * FROM `PROGRAMME` WHERE `CODE` LIKE  ? &&  `ARCHIVE` =  1";
                break;
            case "convention":
                query = "SELECT * FROM `PROGRAMME` WHERE `NUMERO_CD` LIKE  ?  &&  `ARCHIVE` = 1";
                break;
            default:
                query = "SELECT * FROM `PROGRAMME` WHERE `CODE` LIKE  ? &&  `ARCHIVE` = 1";
                break;
        }

        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,"%" + rch + "%");
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                Programme programme = new Programme();
                programme.setId(resultSet.getInt("ID"));
                programme.setCode(resultSet.getString("CODE"));
                programme.setNomProgramme(resultSet.getString("NOM_PROGRAMME"));
                programme.setNombreLogts(resultSet.getInt("NOMBRE_LOGTS"));
                programme.setNumeroCD(resultSet.getString("NUMERO_CD"));
                programme.setDateInscription(resultSet.getString("DATE_INSCRIPTION"));

                list.add(programme);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
