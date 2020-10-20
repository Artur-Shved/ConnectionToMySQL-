package UserAccount;

import java.sql.SQLException;
import java.util.Objects;

public class User {
    public static void main(String[] args) throws SQLException {
        UserProgram user1 = new UserProgram("Ivan", "Artyr4uk228");
        UserProgram user2 = new UserProgram("Tania", "taniatania");
        UserProgram user3 = new UserProgram("Petro", "lolkek123");
        user1.createAlbom("Photos");
        user1.getAlbom();
        user1.deleteAlbom("Photos");








    }
}


