package BddPackage;

import Models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserOperation extends BDD<User>{

    @Override
    public boolean insert(User o) {
        boolean ins = false;
        String query = "INSERT INTO `USER`(`USERNAME`, `PASSWORD`) VALUES (?,?)";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getUserName());
            preparedStmt.setString(2,o.getPassWord());
            int insert = preparedStmt.executeUpdate();
            if(insert != -1) ins = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ins;
    }

    @Override
    public boolean update(User o1, User o2) {
        boolean upd = false;
        String query = "UPDATE `USER` SET `USERNAME`= ?,`PASSWORD`= ? " +
                "WHERE `ID` = ? ";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o1.getUserName());
            preparedStmt.setString(2,o1.getPassWord());
            preparedStmt.setInt(3,o2.getId());
            int update = preparedStmt.executeUpdate();
            if(update != -1) upd = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upd;
    }

    @Override
    public boolean delete(User o) {
        boolean del = false;
        String query = "DELETE FROM `USER` WHERE `ID` = ? ";
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
    public boolean isExist(User o) {
        boolean exist = false;
        String query = "SELECT * FROM `USER` WHERE `USERNAME` = ? AND `PASSWORD` = ?";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,o.getUserName());
            preparedStmt.setString(2,o.getPassWord());
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()){
                exist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> list = new ArrayList<>();
        String query = "SELECT * FROM `DRINKS_CATEGORY`";
        try {
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt(""));
                user.setUserName(resultSet.getString(""));
                user.setPassWord(resultSet.getString(""));

                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
