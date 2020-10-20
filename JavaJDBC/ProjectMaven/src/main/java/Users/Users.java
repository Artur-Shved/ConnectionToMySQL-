package Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Users {
   private String userName;
   private String lastName;
   private String password;
   private  static Connection connection;
   private boolean b;

   public Users(String userName, String lastName, String password) throws SQLException {
        this.userName = userName;
        this.lastName = lastName;
        this.password = password;
        System.out.println(getUser());

    }


   public  static void getConnection(String c) {
       try {
           connection = DriverManager.getConnection(String.valueOf(c));
       }catch(SQLException s){
           System.out.println("Bad connection");
       }
   }

   public String getUser() throws SQLException{

           Statement stage1 = connection.createStatement();
           ResultSet stage2 = stage1.executeQuery("Select * from user;");
           String name = "";
           String userLastName = "";
           String userPassword = "";
           while(stage2.next()){
               if(stage2.getObject("name").equals(userName)){
                   name+=stage2.getString("name");
               }
               if(stage2.getObject("last_name").equals(lastName)){
                   userLastName += stage2.getString("last_name");
               }
               if(stage2.getObject("password").equals(password)){
                   userPassword = stage2.getString("password");
               }
           }

           if(name.equals(userName) && userLastName.equals(lastName) && userPassword.equals(password)){
               b = true;
               return ("Successes Login to Your cabinet");
           }else{
               b = false;
               return "Try again 1";
           }
   }

   public void function (String a) throws SQLException {
       if (b) {
           String albom = "Select a.name from albom a join user u on a.user_id = u.id  where u.name = '" + userName + "'";
           String photo = "Select p.url_adress,a.name from photo p join albom a on p.albom_id = a.id join user u on a.user_id = u.id where u.name = '" + userName + "'";
           Statement stage1 = connection.createStatement();
           ResultSet stage2 = stage1.executeQuery(albom);
           List albom1 = new ArrayList();
           List photo1 = new ArrayList();

           if (a.equals("albom")) {
               while (stage2.next()) {
                   albom1.add(stage2.getObject("name"));
               }

               for (int i = 0; i < albom1.size(); i++) {
                   System.out.println(albom1.get(i));
               }
           } else if (a.equals("photo")) {
               ResultSet stage3 = stage1.executeQuery(photo);

               while (stage3.next()) {
                   photo1.add(stage3.getObject("url_adress"));
                   photo1.add(stage3.getObject("a.name"));
               }

               for (int i = 0; i < photo1.size(); i++) {
                   System.out.println(photo1.get(i));
               }

           }

       }else{
           System.out.println("Faild Login");
       }
   }
}
