package UserAccount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TryPassword {
    String con = "jdbc:mysql://127.0.0.1:3307/loginUser?user=ArturShved&password=Artyr4uk228&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private Connection connect() throws SQLException {
        Connection connection = DriverManager.getConnection(con);
        return connection;
    }

    private String password(String name){
        return name;
    }


}
