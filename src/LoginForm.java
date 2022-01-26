import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;

//import com.formdev.flatlaf.FlatDarculaLaf;

public class LoginForm extends JFrame {
   
    JTextField tfEmail,tfname,tfphone,tfaddress ;
    JLabel screen,lblHead ,lblLogin, lblName , lblEmail , lblPhone , lblAddress , lblPassword;
    JPasswordField pfPassword;

    public void initialize() throws IOException {
        /***** Form Panel *****/
        Container c = getContentPane();
        c.setLayout(null);
        lblHead = new JLabel("Login");
        lblHead.setBounds(650,80,300,35);
        lblHead.setFont(new Font("verdana" ,Font.BOLD, 27));
        c.add(lblHead);


        

        lblEmail = new JLabel("Email-id");
        lblEmail.setBounds(650,200,170,27);
        lblEmail.setFont(new Font("verdana" ,Font.BOLD, 22));
        c.add(lblEmail);

        tfEmail = new JTextField();
        tfEmail.setBounds(850,200,300,27);
        tfEmail.setFont(new Font("verdana" ,Font.BOLD, 15));
        c.add(tfEmail);
        //name inputbox
         //sapid label
        
        //radiobutton
        lblPassword = new JLabel("Password");
        lblPassword.setBounds(650,300,170,27);
        lblPassword.setFont(new Font("verdana" ,Font.BOLD, 22));
        c.add(lblPassword);
        //to select department
        pfPassword = new JPasswordField();
        pfPassword.setBounds(850,300,300,27);
        pfPassword.setFont(new Font("verdana" ,Font.BOLD, 15));
        c.add(pfPassword);

        BufferedImage myPicture;
        myPicture = ImageIO.read(getClass().getResource("/image/order.jpg"));
        screen = new JLabel(new ImageIcon(myPicture));
        screen.setBounds(0,-90,550, 800);
        screen.setBorder(new EmptyBorder(300,60,10,10));
        c.add(screen);

        /***** Buttons Panel *****/
        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("verdana" ,Font.BOLD, 18));
        btnLogin.setBounds(650,400,170,27);
        c.add(btnLogin);
        btnLogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String email = tfEmail.getText();
                String password = String.valueOf(pfPassword.getPassword());

                User user = getAuthenticatedUser(email, password);

                if (user != null) {
                    try {
                        Homepage.main(null);
                        
                    dispose();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Email or Password Invalid",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            
        });

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font("verdana" ,Font.BOLD, 18));
        btnCancel.setBounds(980,400,170,27);
        c.add(btnCancel);
        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                dispose();
            }
            
        });

        lblLogin = new JLabel("Create New Account ? ");
        lblLogin.setBounds(700,500,200,27);
        lblLogin.setFont(new Font("verdana" ,Font.BOLD, 15));
        c.add(lblLogin);

        JButton btnLogindirect = new JButton("Sign Up");
        btnLogindirect.setFont(new Font("verdana" ,Font.BOLD, 15));
        btnLogindirect.setBounds(900,500,170,27);
        c.add(btnLogindirect);
        btnLogindirect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                SignUpForm mainFrame = new SignUpForm();
                try {
                    mainFrame.initialize();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                dispose();
            }
            
        });
        





        setTitle("Login Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(350, 450));
        setLocationRelativeTo(null);
        setVisible(true);
    }



    private User getAuthenticatedUser(String email, String password) {
        User user = new User();

        final String DB_URL = "jdbc:mysql://localhost:3306/onlineFood?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "Prachip@109";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

            String sql = "SELECT * FROM login WHERE email=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.name = resultSet.getString("name");
                user.email = resultSet.getString("email");
                user.phone = resultSet.getString("phone");
                user.address = resultSet.getString("address");
                user.password = resultSet.getString("password");
                user.id = resultSet.getString("id");
            }

            preparedStatement.close();
            conn.close();

        }catch(Exception e){
            System.out.println(e);
        }


        return user;
    }


    public static void main(String[] args) throws IOException {
        try {
           // UIManager.setLookAndFeel( new FlatDarculaLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        LoginForm loginForm = new LoginForm();
        loginForm.initialize();
    }
}