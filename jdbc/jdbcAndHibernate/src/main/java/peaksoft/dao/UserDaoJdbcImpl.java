package peaksoft.dao;

import peaksoft.model.User;
import peaksoft.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {
    private Connection connection;

    public UserDaoJdbcImpl() {
        this.connection = Util.getConnection();
    }

    public void createUsersTable() {
        String sql = """
                CREATE TABLE if not exists users(
                id SERIAL PRIMARY KEY,
                name VARCHAR(50) NOT NULL,
                last_name VARCHAR(50) NOT NULL,
                age SMALLINT NOT NULL
                );
                """;
        try (Statement statement = connection.createStatement()){
            statement.execute(sql);
            System.out.println("Successfully created!");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        String sql = """
                DROP TABLE users;
                """;
        try (Statement statement = connection.createStatement()){
            statement.execute(sql);
            System.out.println("Deleted table successfully!");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = """
                INSERT INTO users(name,last_name,age) 
                VALUES(?,?,?);
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
            System.out.println("User successfully saved!");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String sql = """
                DELETE FROM users WHERE id = ?;
                """;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
            System.out.println("User successfully removed");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        String sql = """
                SELECT * FROM users;
                """;
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                userList.add(new User(resultSet.getLong("id"),resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age")));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return userList;
    }

    public void cleanUsersTable() {
        String sql = """
                TRUNCATE users;
                """;
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
            System.out.println("Successfully cleared!");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}