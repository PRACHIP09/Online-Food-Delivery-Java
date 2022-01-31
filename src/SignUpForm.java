import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class SignUpForm extends JFrame {
    
    JTextField tfEmail,tfname,tfphone,tfaddress ;
    JLabel screen , lblHead ,lblLogin, lblName , lblEmail , lblPhone , lblAddress , lblPassword;
    JPasswordField pfPassword;

    public void initialize() throws IOException {
        Container c = getContentPane();
        c.setLayout(null);
        c.setBackground(Color.decode("#2ADDC0"));
        lblHead = new JLabel("Sign Up ");
        lblHead.setBounds(650,80,300,35);
        lblHead.setFont(new Font("verdana" ,Font.BOLD, 27));
        c.add(lblHead);

        //name label
        lblName = new JLabel("Name");
        lblName.setBounds(650,200,170,27);
        lblName.setFont(new Font("verdana" ,Font.BOLD, 22));
        c.add(lblName);

        tfname = new JTextField();
        tfname.setBounds(850,200,300,27);
        tfname.setFont(new Font("verdana" ,Font.BOLD, 18));
        c.add(tfname);

        lblEmail = new JLabel("Email-id");
        lblEmail.setBounds(650,250,170,27);
        lblEmail.setFont(new Font("verdana" ,Font.BOLD, 22));
        c.add(lblEmail);

        tfEmail = new JTextField();
        tfEmail.setBounds(850,250,300,27);
        tfEmail.setFont(new Font("verdana" ,Font.BOLD, 18));
        c.add(tfEmail);
        //name inputbox
         //sapid label
        lblPhone = new JLabel("Contact No");
        lblPhone.setBounds(650,300,170,27);
        lblPhone.setFont(new Font("verdana" ,Font.BOLD, 22));
        c.add(lblPhone);

        tfphone = new JTextField();
        tfphone.setBounds(850,300,300,27);
        tfphone.setFont(new Font("verdana" ,Font.BOLD, 18));
        c.add(tfphone);
        //sapid inputbox
        //gender label
        lblAddress = new JLabel("Address");
        lblAddress.setBounds(650,350,170,27);
        lblAddress.setFont(new Font("verdana" ,Font.BOLD, 22));
        c.add(lblAddress);

        tfaddress = new JTextField();
        tfaddress.setBounds(850,350,300,27);
        tfaddress.setFont(new Font("verdana" ,Font.BOLD, 18));
        c.add(tfaddress);
        //radiobutton
        lblPassword = new JLabel("Password");
        lblPassword.setBounds(650,400,170,27);
        lblPassword.setFont(new Font("verdana" ,Font.BOLD, 22));
        c.add(lblPassword);
        //to select department
        pfPassword = new JPasswordField();
        pfPassword.setBounds(850,400,300,27);
        pfPassword.setFont(new Font("verdana" ,Font.BOLD, 18));
        c.add(pfPassword);

        Image myPicture = ImageIO.read(getClass().getResource("/image/signup.jpg"));
        Image imageScaled = myPicture.getScaledInstance(650,850,Image.SCALE_SMOOTH);
        screen = new JLabel(new ImageIcon(imageScaled));
        screen.setBounds(-10,-50,600, 800);
        c.add(screen);

        /***** Buttons Panel *****/
        JButton btnLogin = new JButton("Sign Up ");
        btnLogin.setFont(new Font("verdana" ,Font.BOLD, 18));
        btnLogin.setBounds(650,470,170,27);
        c.add(btnLogin);
        btnLogin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String email = tfEmail.getText();
                String password = String.valueOf(pfPassword.getPassword());
                String name = tfname.getText();
                String phone = tfphone.getText();
                String address = tfaddress.getText();

                User user = getAuthenticatedUser(email, password,name,phone,address);

                if (user != null) {
                    LoginForm mainFrame = new LoginForm();
                    try {
                        mainFrame.initialize();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(SignUpForm.this,
                            "Invalid input",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            
        });

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font("verdana" ,Font.BOLD, 18));
        btnCancel.setBounds(980,470,170,27);
        c.add(btnCancel);
        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                dispose();
            }
            
        });

        lblLogin = new JLabel("Already Registered ? ");
        lblLogin.setBounds(700,570,200,27);
        lblLogin.setFont(new Font("verdana" ,Font.BOLD, 15));
        c.add(lblLogin);

        JButton btnLogindirect = new JButton("Login");
        btnLogindirect.setFont(new Font("verdana" ,Font.BOLD, 15));
        btnLogindirect.setBounds(900,570,170,27);
        c.add(btnLogindirect);
        btnLogindirect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                LoginForm mainFrame = new LoginForm();
                try {
                    mainFrame.initialize();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                dispose();
            }
            
        });
        



        /***** Initialise the frame *****/

        setTitle("Login Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(450, 650));
        setLocationRelativeTo(null);
        setVisible(true);
    }



    private User getAuthenticatedUser(String email, String password , String name , String phone , String address) {
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost:3306/onlineFood?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "Prachip@109";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

            String sql = "INSERT INTO login  (name, phone, email, address ,password) "+"VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, password);


            int resultSet = preparedStatement.executeUpdate();
            if (resultSet > 0) {
                user = new User();
                user.name = name;
                user.email = email;
                user.phone = phone;
                user.address = address;
                user.password = password;
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

        SignUpForm signupform = new SignUpForm();
        signupform.initialize();
    }
}
