import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

class MyFrame extends JFrame implements Runnable {
    Container c;
    JLabel title, ad, label, image, label1,title2,image2;
    JButton menu;

    public MyFrame() throws IOException {
        setTitle("Advertisement");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        c = getContentPane();
        c.setBackground(Color.decode("#151515"));
        c.setLayout(null);

        BufferedImage myPicture;
        myPicture = ImageIO.read(getClass().getResource("/image/img (2).jpg"));
        image = new JLabel(new ImageIcon(myPicture));
        image.setSize(900, 1100);
        image.setLocation(-160, 0);
        c.add(image);

        BufferedImage myPicture2;
        myPicture2 = ImageIO.read(getClass().getResource("/image/cat.gif"));
        image2 = new JLabel(new ImageIcon(myPicture2));
        image2.setSize(300, 300);
        image2.setLocation(920, 380);
        c.add(image2);

        title = new JLabel("Welcome");
        title.setForeground(Color.white);
        title.setFont(new Font("Verdana", Font.BOLD, 45));
        title.setSize(300, 200);
        title.setLocation(830, 30);
        c.add(title);

        title2 = new JLabel("Welcome");
        title2.setForeground(Color.red);
        title2.setFont(new Font("Verdana", Font.BOLD, 45));
        title2.setSize(300, 200);
        title2.setLocation(830, 33);
        c.add(title2);

        ad = new JLabel(":)");
        ad.setForeground(Color.white);
        ad.setFont(new Font("Osaka", Font.BOLD, 25));
        ad.setSize(300, 30);
        ad.setLocation(830, 200);
        c.add(ad);

        label = new JLabel("Food can make you happier than humans !!");
        label.setForeground(Color.green);
        label.setFont(new Font("Verdana", Font.BOLD, 13));
        label.setSize(1000, 30);
        label.setLocation(800, 300);
        c.add(label);

        label1 = new JLabel("Get FLAT 25% OFF");
        label1.setForeground(Color.lightGray);
        label1.setFont(new Font("Arial", Font.BOLD, 20));
        label1.setSize(1000, 30);
        label1.setLocation(800, 360);
        c.add(label1);

        menu=new JButton("Menu");
        menu.setSize(100,50);
        menu.setLocation(830, 420);
        menu.setBackground(Color.green);
        menu.setForeground(Color.darkGray);
        c.add(menu);

        new Thread(this).start();
    }

    public void run() {
        try {
            while (true) {
                if (title.getText() == "Welcome") {
                    title.setText("Food world");
                    title2.setText("Food world");
                    Thread.sleep(1000);
                } else if (ad.getText() == ":)") {
                    ad.setText(";)");
                    Thread.sleep(1000);
                } else {
                    title.setText("Welcome");
                    title2.setText("Welcome");
                    ad.setText(":)");
                    Thread.sleep(1000);
                }
            }

        } catch (InterruptedException e) {
        }
    }
}

public class Homepage {

    public static void main(String[] args) throws IOException {
        MyFrame frame = new MyFrame();
        frame.setSize(1280,1200);
        frame.setLocation(0, 0);
    }
}
