package Users;

import java.sql.SQLException;

public class user1 extends Users {
    public user1(String userName, String lastName, String password) throws SQLException{
        super(userName,lastName,password);
    }
    public static void main(String[] args) throws SQLException {
        getConnection("jdbc:mysql://127.0.0.1:3307/test?user=ArturShved&password=Artyr4uk228&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
            Users user1 = new Users("Artur", "Shved", "Artyr4uk227");
            user1.function("albom");
            Users user2 = new Users("Tanya","Puhach","taniatania228");
            user2.function("photo");
    }
}
