import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Order extends JFrame{
        JLabel label1,label2,label3,label4,label5 , screen , lbl1,lbl2,lbl3,lbl4,lbl5,lbl6 , lbl;
        JTextField t1,t2;
        JRadioButton male,female;
        JCheckBox terms;
        JButton submit , home;
        JLabel msg;
    public void initialize(User user) throws IOException {
// setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
setExtendedState(JFrame.MAXIMIZED_BOTH);
setMinimumSize(new Dimension(350, 450));
setLocationRelativeTo(null);
setVisible(true);
Container c = getContentPane();
setTitle("Registration Form");
//setUndecorated(false);
//setResizable(true); 
c.setLayout(null);

c.setBackground(Color.decode("#2ADDC0"));

lbl = new JLabel("Happy Ordering ");
lbl.setBounds(650,80,300,35);
lbl.setFont(new Font("verdana" ,Font.BOLD, 27));
c.add(lbl);


//name label
lbl1 = new JLabel("Name");
lbl1.setBounds(650,200,170,27);
lbl1.setFont(new Font("verdana" ,Font.BOLD, 22));
c.add(lbl1);

label1 = new JLabel(":      " + user.name);
label1.setBounds(850,200,300,27);
label1.setFont(new Font("verdana" ,Font.BOLD, 22));
c.add(label1);

lbl2 = new JLabel("Email-id");
lbl2.setBounds(650,250,170,27);
lbl2.setFont(new Font("verdana" ,Font.BOLD, 22));
c.add(lbl2);

label5 = new JLabel(":     " +user.email);
label5.setBounds(850,250,300,27);
label5.setFont(new Font("verdana" ,Font.BOLD, 22));
c.add(label5);
//name inputbox
 //sapid label
lbl3 = new JLabel("Contact No");
lbl3.setBounds(650,300,170,27);
lbl3.setFont(new Font("verdana" ,Font.BOLD, 22));
c.add(lbl3);

label2 = new JLabel(":      "+user.phone);
label2.setBounds(850,300,300,27);
label2.setFont(new Font("verdana" ,Font.BOLD, 22));
c.add(label2);
//sapid inputbox
//gender label
lbl4 = new JLabel("Address");
lbl4.setBounds(650,350,170,27);
lbl4.setFont(new Font("verdana" ,Font.BOLD, 22));
c.add(lbl4);

label3 = new JLabel(":      "+user.address);
label3.setBounds(850,350,300,27);
label3.setFont(new Font("verdana" ,Font.BOLD, 22));
c.add(label3);
//radiobutton
lbl5 = new JLabel("Order Total");
lbl5.setBounds(650,400,170,27);
lbl5.setFont(new Font("verdana" ,Font.BOLD, 22));
c.add(lbl5);
//to select department
label4 = new JLabel(":      Rs."+user.total);
label4.setBounds(850,400,300,27);
label4.setFont(new Font("verdana" ,Font.BOLD, 22));
c.add(label4);

Image myPicture = ImageIO.read(getClass().getResource("/image/order.jpg"));
Image imageScaled = myPicture.getScaledInstance(550,850,Image.SCALE_SMOOTH);
screen = new JLabel(new ImageIcon(imageScaled));
screen.setBounds(-10,-50,550, 800);
c.add(screen);
//submit button
submit = new JButton("Submit");
submit.setBounds(650,550,170,30);
submit.setFont(new Font("verdana" ,Font.BOLD, 18));
c.add(submit);
submit.addActionListener(new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        try {
            SendEmail.send("prachipp999@gmail.com" , user.total , user.name , user.phone , user.address);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        dispose();
        
    }
});

home = new JButton("Home");
home.setBounds(950,550,170,30);
home.setFont(new Font("verdana" ,Font.BOLD, 18));
c.add(home);
//submit.addActionListener(this);
home.addActionListener(new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        try {
            // menu = new Homepage();
             Homepage.main(null);
             dispose();
         } catch (IOException e1) {
             // TODO Auto-generated catch block
             e1.printStackTrace();
         }
        
    }
});

} 
}
