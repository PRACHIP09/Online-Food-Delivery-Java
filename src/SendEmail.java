// File Name SendEmail.java


import java.util.*;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.*;
public class SendEmail {
   public static void send(String recipient , String total , String Name , String phone , String address) throws Exception {    
      String to = recipient;
       String Name_cust = Name;
       String phone_cust = phone;
       String address_cust = address;
       String total_cust = total;
      System.out.println("preparing to send the mail");
      System.out.println(total + Name + phone + address);
      String host = "smtp.gmail.com";
      Properties properties = new Properties();
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.starttls.enable", "true");
      properties.put("mail.smtp.host", host);
      properties.put("mail.smtp.port", "587");
      String from = "shahh8138@gmail.com";
      String pass = "aisccinxktxjwsqa";

      // Get the default Session object.
      Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
          // @Overrride

         protected PasswordAuthentication getPasswordAuthentication() {

             return new PasswordAuthentication(from,pass);

         }

     });

   
        
         Message message = prepare(session,from,to,Name_cust,address_cust,phone_cust,total_cust);
         Transport.send(message);
         System.out.println("success!");
   }
   private static Message prepare(Session session,String from,String to, String Name_cust , String address_cust , String phone_cust , String total_cust){
      try{
     Message message = new MimeMessage(session);
      message.setFrom( new InternetAddress(from));
      message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
      message.setSubject("hello\n");
      message.setText( Name_cust );
      return message;
     }
     catch(Exception ex){
        Logger.getLogger(SendEmail.class.getName());
     }
     return null;
  }
}

