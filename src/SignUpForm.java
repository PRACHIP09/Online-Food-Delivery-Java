import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;

//import com.formdev.flatlaf.FlatDarculaLaf;

public class SignUpForm extends JFrame {
    final private Font mainFont = new Font("Segoe print", Font.BOLD, 18);
    JTextField tfEmail,tfname,tfphone,tfaddress;
    JPasswordField pfPassword;

    public void initialize() {
        /***** Form Panel *****/
        JLabel lbLoginForm = new JLabel("SignUp Form", SwingConstants.CENTER);
        lbLoginForm.setFont(mainFont);

        JLabel lbName = new JLabel("Name");
        lbName.setFont(mainFont);

        tfname = new JTextField();
        tfname.setFont(mainFont);

        JLabel lbPhone = new JLabel("Phone");
        lbPhone.setFont(mainFont);

        tfphone = new JTextField();
        tfphone.setFont(mainFont);

        JLabel lbEmail = new JLabel("Email");
        lbEmail.setFont(mainFont);

        tfEmail = new JTextField();
        tfEmail.setFont(mainFont);

        JLabel lbAddress = new JLabel("Address");
        lbAddress.setFont(mainFont);

        tfaddress = new JTextField();
        tfaddress.setFont(mainFont);

        JLabel lbPassword = new JLabel("Password");
        lbPassword.setFont(mainFont);

        pfPassword = new JPasswordField();
        pfPassword.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 1, 1));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        formPanel.add(lbLoginForm);
        formPanel.add(lbName);
        formPanel.add(tfname);
        formPanel.add(lbPhone);
        formPanel.add(tfphone);
        formPanel.add(lbEmail);
        formPanel.add(tfEmail);
        formPanel.add(lbAddress);
        formPanel.add(tfaddress);
        formPanel.add(lbPassword);
        formPanel.add(pfPassword);

        

        /***** Buttons Panel *****/
        JButton btnLogin = new JButton("SignUp");
        btnLogin.setFont(mainFont);
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
                    mainFrame.initialize();
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
        btnCancel.setFont(mainFont);
        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                dispose();
            }
            
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        buttonsPanel.add(btnLogin);
        buttonsPanel.add(btnCancel);



        /***** Initialise the frame *****/
        add(formPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Login Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setMinimumSize(new Dimension(450, 650));
        //setResizable(false);
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


    public static void main(String[] args) {
        try {
           // UIManager.setLookAndFeel( new FlatDarculaLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        SignUpForm signupform = new SignUpForm();
        signupform.initialize();
    }
}
