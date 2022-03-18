package BddPackage;

import Models.Programme;
import Models.Projet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjetOperation extends  BDD<Projet>{

    @Override
    public boolean insert(Projet o) {
        boolean ins = false;
        String query = "INSERT INTO `PROJET`( `ID_PROGRAMME`, `NOM`, `NUMBER_LOGTS`, `NUMERO_CF`, `DATE_INSCRIPTION` , `SITE` ) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getIdProgramme());
            preparedStmt.setString(2,o.getNom());
            preparedStmt.setInt(3,o.getNomberLogts());
            preparedStmt.setString(4,o.getNumeroCF());
            preparedStmt.setString(5,o.getDateInsription());
            preparedStmt.setString(6,o.getSite());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(Projet o1, Projet o2) {
        boolean upd = false;
        String query = "UPDATE `PROJET` SET `NOM`=?,`NUMBER_LOGTS`=?,`NUMERO_CF`=?,`DATE_INSCRIPTION`= ? , `SITE` = ?" +
                "WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            preparedStmt.setString(1,o1.getNom());
            preparedStmt.setInt(2,o1.getNomberLogts());
            preparedStmt.setString(3,o1.getNumeroCF());
            preparedStmt.setString(4,o1.getDateInsription());
            preparedStmt.setString(5,o1.getSite());
            preparedStmt.setInt(6,o2.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(Projet o) {
        boolean del = false;
        String query = "DELETE FROM `PROJET` WHERE `ID` = ? ";
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
    public boolean isExist(Projet o) {
        return false;
    }

    public Projet get(Integer id) {
        Projet projet = new Projet();
        String query = "SELECT * FROM `PROJET` WHERE `ID` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()){

                projet.setId(resultSet.getInt("ID"));
                projet.setIdProgramme(resultSet.getInt("ID_PROGRAMME"));
                projet.setNom(resultSet.getString("NOM"));
                projet.setSite(resultSet.getString("SITE"));
                projet.setNomberLogts(resultSet.getInt("NUMBER_LOGTS"));
                projet.setNumeroCF(resultSet.getString("NUMERO_CF"));
                projet.setDateInsription(resultSet.getString("DATE_INSCRIPTION"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projet;
    }

    @Override
    public ArrayList<Projet> getAll() {
        ArrayList<Projet> list = new ArrayList<>();
        String query = "SELECT * FROM `PROJET` WHERE `ARCHIVE` = 0 ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                Projet projet = new Projet();
                projet.setId(resultSet.getInt("ID"));
                projet.setIdProgramme(resultSet.getInt("ID_PROGRAMME"));
                projet.setNom(resultSet.getString("NOM"));
                projet.setSite(resultSet.getString("SITE"));
                projet.setNomberLogts(resultSet.getInt("NUMBER_LOGTS"));
                projet.setNumeroCF(resultSet.getString("NUMERO_CF"));
                projet.setDateInsription(resultSet.getString("DATE_INSCRIPTION"));

                list.add(projet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getLastAddId() {
        int id = 0;
        String query = "SELECT `ID` FROM `PROJET` ORDER BY `ID` DESC LIMIT 1";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next())
                id = resultSet.getInt("ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public ArrayList<Projet> getAllByProgramme(int idProg) {
        ArrayList<Projet> list = new ArrayList<>();
        String query = "SELECT * FROM `PROJET` WHERE `ID_PROGRAMME` = ? && `ARCHIVE` = 0";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,idProg);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                Projet projet = new Projet();
                projet.setId(resultSet.getInt("ID"));
                projet.setIdProgramme(resultSet.getInt("ID_PROGRAMME"));
                projet.setNom(resultSet.getString("NOM"));
                projet.setSite(resultSet.getString("SITE"));
                projet.setNomberLogts(resultSet.getInt("NUMBER_LOGTS"));
                projet.setNumeroCF(resultSet.getString("NUMERO_CF"));
                projet.setDateInsription(resultSet.getString("DATE_INSCRIPTION"));

                list.add(projet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addToArchive(int Id){
        boolean upd = false;
        String query = "UPDATE `PROJET` SET `ARCHIVE`= 1 WHERE `ID` = ? ";
        try {

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,Id);

            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    public boolean DeleteFromArchive(Projet projet){
        boolean upd = false;
        String query = "UPDATE `PROJET` SET `ARCHIVE`= 0 WHERE `ID` = ? ";
        try {

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,projet.getId());

            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    public ArrayList<Projet> getAllArchive() {
        ArrayList<Projet> list = new ArrayList<>();
        String query = "SELECT * FROM `PROJET` WHERE `ARCHIVE` = 1 ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                Projet projet = new Projet();
                projet.setId(resultSet.getInt("ID"));
                projet.setIdProgramme(resultSet.getInt("ID_PROGRAMME"));
                projet.setNom(resultSet.getString("NOM"));
                projet.setSite(resultSet.getString("SITE"));
                projet.setNomberLogts(resultSet.getInt("NUMBER_LOGTS"));
                projet.setNumeroCF(resultSet.getString("NUMERO_CF"));
                projet.setDateInsription(resultSet.getString("DATE_INSCRIPTION"));

                list.add(projet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


}
