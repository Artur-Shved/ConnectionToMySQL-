package UserAccount;

import javax.xml.transform.Result;
import java.sql.*;

public class TryLogin{
    private String conn = "jdbc:mysql://127.0.0.1:3307/loginUser?user=ArturShved&password=Artyr4uk228&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private Connection connect() throws SQLException {
        Connection connection = DriverManager.getConnection(conn);
        return connection;
    }

    private String login(String name){
        return name;
    }
    public boolean correctLogin(String name) throws SQLException {
        boolean b = false;
        Statement statement = connect().createStatement();
        ResultSet resultSet = statement.executeQuery("select name from user");
        while(resultSet.next()){
            if(resultSet.getString("name").equals(login(name))){
                b = true;
            }
        }
        return b;
    }
}
