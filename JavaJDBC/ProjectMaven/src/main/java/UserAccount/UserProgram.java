package UserAccount;

import java.sql.*;
import java.util.Scanner;

public class UserProgram extends TryLogin {
    private String connection = "jdbc:mysql://127.0.0.1:3307/loginUser?user=ArturShved&password=Artyr4uk228&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String name;
    private String email;
    private String password;
    private Boolean bool;

    public UserProgram(String name, String password) throws SQLException{
        this.name = name;
        this.password = password;
        if(getAcount()){
            System.out.println("Sucsses login");
        }else if(!getAcount()){

            System.out.println("Faild Login");
            System.out.println("Do you want to registretion?");
            Scanner scanner = new Scanner(System.in);
            String question = scanner.nextLine();
            question = question.toLowerCase();
            if(question.equals("yes")){
                System.out.println("Write your login: ");
                String name1 = scanner.nextLine();
                setName(name1);
                System.out.println("Write your password: ");
                String password1 = scanner.nextLine();
                setPassword(password1);
                System.out.println("Write your email: ");
                String email1 = scanner.nextLine();
                setEmail(email1);
               if(!correctLogin(name1)) {
                   createUser();
                   System.out.println("You succses registred");
               }else{
                   System.out.println("This login alredy busy");
               }
            }
        }
    }
    private void setName(String name){
        this.name = name;
    }
    private String getName() {
        return name;
    }


    private String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    private void setPassword(String password){
        this.password = password;
    }
    private String getPassword() {
        return password;
    }

    private Connection ConnectionUser()throws SQLException {
        Connection connect = DriverManager.getConnection(connection);
        return connect;
    }

    private Boolean getAcount() throws SQLException {
        String name1 = "";
        String password1 = "";
        Statement statement = ConnectionUser().createStatement();
        ResultSet resultSet = statement.executeQuery("Select * from user");
        while(resultSet.next()) {
            if (resultSet.getString("name").equals(getName())) {
                name1 = resultSet.getString("name");
            }
            if (resultSet.getString("password").equals(getPassword())) {
                password1 = resultSet.getString("password");
            }
        }

        if(name1.equals(getName()) && password1.equals(getPassword())){
            return true;
        }else{
            return false;
        }
    }

//    public void createTables()throws SQLException{
//        Statement statement = ConnectionUser().createStatement();
//        statement.execute("drop table if exists user");
//        statement.executeUpdate("create table if not exists user (id int not null primary key auto_increment, name varchar (30) not null unique, password varchar(40) not null, email varchar(60) not null unique);");
//        statement.execute("drop table if exists albom");
//        statement.executeUpdate("create table if not exists albom (id int not null primary key auto_increment, name varchar(40) not null unique, user_id int not null);");
//        statement.executeUpdate("alter table albom add foreign key (user_id) references user(id)");
//        statement.execute("drop table if exists photo");
//        statement.executeUpdate("create table if not exists photo (id int not null primary key auto_increment, url_adress varchar(100) not null,albom_id int not null);");
//        statement.executeUpdate("alter table photo add foreign key (albom_id) references albom(id);");
//
//    }
    public void dropAlbom() throws SQLException {
        if(getAcount()) {
            Statement statement = ConnectionUser().createStatement();
            statement.execute("drop table if exists albom");
            statement.executeUpdate("create table if not exists albom (id int not null primary key auto_increment, name varchar(30) not null , user_id int not null);");
        }else{
            System.out.println("Try login");
        }
    }

    public void createUser() throws SQLException{
        if(!getAcount()) {
            PreparedStatement preparedStatement = ConnectionUser().prepareStatement("insert into user (name, password,email) values (?,?,?);");
            preparedStatement.setString(1, getName());
            preparedStatement.setString(2, getPassword());
            preparedStatement.setString(3, getEmail());
            preparedStatement.execute();
        }else{
            System.out.println("You alredy created");
        }
    }

    public void createAlbom(String name) throws SQLException {
        if(getAcount()) {

            PreparedStatement pr = ConnectionUser().prepareStatement("insert into albom (name,user_id) values (?,?);");
            pr.setString(1, name);
            pr.setString(2, getId());
            pr.execute();
        }else{
            System.out.println("You cant create Albom");
        }
    }

    public void getAlbom() throws SQLException{
        if(getAcount()) {
            PreparedStatement preparedStatement = ConnectionUser().prepareStatement("select a.name from albom a join user u on a.user_id = u.id and u.id = ?");
           preparedStatement.setString(1,getId());
             ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
        }
    }

    public String getId() throws SQLException{
        String id = "";
        PreparedStatement preparedStatement = ConnectionUser().prepareStatement("select * from user where name = ?");
        preparedStatement.setString(1,getName());
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            id = resultSet.getString("id");
        }
        return id;
    }

    public void updateAlbom(String name,String name1) throws SQLException {
        if (getAcount()) {
            PreparedStatement preparedStatement1 = ConnectionUser().prepareStatement("select * from albom where id = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            preparedStatement1.setString(1, getIdForAlbom(name));
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            resultSet1.absolute(1);
            resultSet1.updateString("name", name1);
            resultSet1.updateRow();
        }
    }

        public void deleteAlbom(String name) throws SQLException {
            if (getAcount()) {
                if (getIdForAlbom(name).isEmpty()) {
                    System.out.println("Альбому по назві не знайдено");
                }else {
                    PreparedStatement preparedStatement1 = ConnectionUser().prepareStatement("select * from albom where id = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    preparedStatement1.setString(1, getIdForAlbom(name));
                    ResultSet resultSet1 = preparedStatement1.executeQuery();
                    resultSet1.absolute(1);
                    resultSet1.deleteRow();
                }
            }
        }

        private String getIdForAlbom(String name) throws SQLException {
            if (getAcount()) {
                PreparedStatement preparedStatement = ConnectionUser().prepareStatement("select a.id from albom a join user u on a.user_id = u.id and u.name = ? and a.name = ?");
                preparedStatement.setString(1, getName());
                preparedStatement.setString(2, name);
                ResultSet resultSet = preparedStatement.executeQuery();
                String id = "";
                while (resultSet.next()) {
                    id = resultSet.getString("id");
                }
                return id;
            }else{
                return "Спочатку зереєструйтесь!";
            }
        }

        public void deleteUser() throws SQLException {
            PreparedStatement preparedStatement = ConnectionUser().prepareStatement("select a.user_id from albom a join user u on a.user_id = u.id and u.name = ? ");
            preparedStatement.setString(1,getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            String id = "";
            if(resultSet.next()){
                id = resultSet.getString("user_id");
            }
            PreparedStatement preparedStatement1 = ConnectionUser().prepareStatement("select * from albom where user_id = ?", ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            preparedStatement1.setString(1,id);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            while(resultSet1.next()){
                resultSet1.deleteRow();
            }
            PreparedStatement preparedStatement2 = ConnectionUser().prepareStatement("select * from user where name = ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            preparedStatement2.setString(1,getName());
            ResultSet resultSet2  = preparedStatement2.executeQuery();
            while(resultSet2.next()){
                resultSet2.deleteRow();
            }



    }
    }
