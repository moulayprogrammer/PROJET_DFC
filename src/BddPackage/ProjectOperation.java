package BddPackage;

import Models.Project;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectOperation extends  BDD<Project>{

    @Override
    public boolean insert(Project o) {
        boolean ins = false;
        String query = "INSERT INTO `PROJET`( `ID_PROGRAMME`, `NOM`, `NUMBER_LOGTS`, `NUMERO_CF`, `DATE_INSCRIPTION` , `SITE` ) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,o.getIdProgramme());
            preparedStmt.setString(2,o.getNom());
            preparedStmt.setInt(3,o.getNomberLogts());
            preparedStmt.setString(4,o.getNumeroCF());
            preparedStmt.setString(5, o.getDateInsription());
            preparedStmt.setString(6,o.getSite());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(Project o1, Project o2) {
        boolean upd = false;
        String query = "UPDATE `PROJET` SET `ID_PROGRAMME`= ?, `NOM`=?,`NUMBER_LOGTS`=?,`NUMERO_CF`=?,`DATE_INSCRIPTION`= ? , `SITE` = ?" +
                "WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            preparedStmt.setInt(1,o1.getIdProgramme());
            preparedStmt.setString(2,o1.getNom());
            preparedStmt.setInt(3,o1.getNomberLogts());
            preparedStmt.setString(4,o1.getNumeroCF());
            preparedStmt.setString(5,o1.getDateInsription());
            preparedStmt.setString(6,o1.getSite());
            preparedStmt.setInt(7,o2.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(Project o) {
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
    public boolean isExist(Project o) {
        return false;
    }

    public Project get(Integer id) {
        Project projet = new Project();
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
    public ArrayList<Project> getAll() {
        ArrayList<Project> list = new ArrayList<>();
        String query = "SELECT * FROM `PROJET` WHERE `ARCHIVE` = 0 ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                Project projet = new Project();
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

    public ArrayList<Project> getAllByProgramme(int idProg) {
        ArrayList<Project> list = new ArrayList<>();
        String query = "SELECT * FROM `PROJET` WHERE `ID_PROGRAMME` = ? && `ARCHIVE` = 0";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,idProg);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                Project projet = new Project();
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

    public boolean DeleteFromArchive(Project projet){
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

    public ArrayList<Project> getAllArchive() {
        ArrayList<Project> list = new ArrayList<>();
        String query = "SELECT * FROM `PROJET` WHERE `ARCHIVE` = 1 ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                Project projet = new Project();
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
