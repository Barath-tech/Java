package net.barath.usermanagement.dao;

import net.barath.usermanagement.model.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private String jdbcURL = "jdbc: mysql://localhost: 3306/demo?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "password";

    private static final String INSERT_USERS_SQL="insert into users"+"(name,email,country)values"+"(?,?,?);";
    private static final String SELECT_USERS_SQL="select id,name,email,country from users where id=?;";
    private static final String DELETE_USERS_SQL="delete from users where id=?;";
    private static final String SELECT_ALL_USERS="select * from users;";
    private static final String UPDATE_USERS="update users set name=?,email=?,country=? where id=?;";

    protected Connection getConnection(){
        Connection connection=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return connection;
    }

    //Create or insert User
    public void insertUser(User user){
        try (Connection connection=getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement(INSERT_USERS_SQL)){
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getCountry());
            preparedStatement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // update user
    public boolean updateUser (User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement (UPDATE_USERS);) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());
            statement.setInt (4, user.getId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    // select user by id
    public User selectUser(int id) {
        User user = null;
    // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
    // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement (SELECT_USERS_SQL);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
    // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
    // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                user = new User(id, name, email, country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // select users
    public List<User> selectAllUsers(){
    List<User> users = new ArrayList<>();
    // Step 1: Establishing a Connection
    try(Connection connection = getConnection();
    // Step 2:Create a statement using connection object
    PreparedStatement preparedStatement = connection.prepareStatement (SELECT_USERS_SQL);) {
        System.out.println (preparedStatement);
    // Step 3: Execute the query or update query
        ResultSet rs = preparedStatement.executeQuery();
    // Step 4: Process the ResultSet object.
        while (rs.next()) {
            int id= rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String country = rs.getString("country");
            users.add(new User(id, name, email, country));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
return users;
}

    //Delete user
    public boolean deleteUser(int id)throws SQLException{
        boolean rowDeleted;
        try(Connection connection=getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement(DELETE_USERS_SQL)){
            preparedStatement.setInt(1,id);
            rowDeleted=preparedStatement.executeUpdate()>0;
        }
        return rowDeleted;
    }



}
