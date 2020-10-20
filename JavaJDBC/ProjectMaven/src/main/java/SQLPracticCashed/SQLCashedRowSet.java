package SQLPracticCashed;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class SQLCashedRowSet {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String connectionString = "jdbc:mysql://127.0.0.1:3307/practic?user=ArturShved&password=Artyr4uk228&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        ResultSet resultSet = getData();
        while(resultSet.next()){
            System.out.println(resultSet.getInt("id"));
            System.out.println(resultSet.getString("name"));
        }
        CachedRowSet cachedRowSet = (CachedRowSet) resultSet;
        cachedRowSet.setUrl(connectionString);

        //        cachedRowSet.setCommand("select * from books where id = ?");
//        cachedRowSet.setInt(1,1);
//        cachedRowSet.execute();
//        System.out.println("------------------------------------");
//        while(cachedRowSet.next()){
//            System.out.println(cachedRowSet.getInt("id"));
//            System.out.println(cachedRowSet.getString("name"));
//        }
        System.out.println("----------------------------------------------------------");
        cachedRowSet.setTableName("books");// вказується таблиця
        cachedRowSet.absolute(1);// вказується рядок
        cachedRowSet.deleteRow();// видаляє рядок на якому стоїть маркер
        cachedRowSet.beforeFirst();// збиває маркер на початок
        cachedRowSet.setCommand("select * from books"); // що повинно вивести
        while(cachedRowSet.next()){
            System.out.println(cachedRowSet.getInt("id")); // вказується  з якої колонки виводить
            System.out.println(cachedRowSet.getString("name"));
        }

        cachedRowSet.acceptChanges(DriverManager.getConnection(connectionString));
    }

    static ResultSet getData() throws ClassNotFoundException, SQLException {
        try(Connection connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/practic?user=ArturShved&password=Artyr4uk228&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
            Statement statement = connect.createStatement()){
            statement.execute("drop table if exists books");
            statement.executeUpdate("create table if not exists books(id int not null primary key auto_increment,name varchar(30) not null);");
            statement.executeUpdate("insert into books (name) values ('Eragon'), ('HarryPotter'),('The black Tower');");
            RowSetFactory factory = RowSetProvider.newFactory(); // метод newFactory  класі RowSetFactory
            CachedRowSet cachedRowSet = factory.createCachedRowSet();// CachedRowSet - дочірній клвс RowSet

            Statement stat  = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("Select * from books");
            cachedRowSet.populate(resultSet);
            return cachedRowSet;
        }


    }
}
