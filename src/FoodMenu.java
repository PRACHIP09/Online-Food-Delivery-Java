import java.awt.Font;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;  
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;  
public class FoodMenu extends JFrame implements ActionListener{
    java.awt.Container c;
    JButton backButton, orderButton;
    JTextField textField;
    GridBagConstraints gbc;
    JTable table;
    DefaultTableModel dtm;
    Double[] price;
    Double[] priceDrinks;
    Double[] priceDesserts;
    double totalPrice = 0;
    JSpinner[] numSpinner;
    JLabel[] foodLabel;
    JLabel[] foodImage;
    String[] file;
    JSpinner[] numSpinnerDrinks;
    JLabel[] drinkLabel;
    JLabel[] drinkImage;
    String[] fileDrinks;
    JSpinner[] numSpinnerDesserts;
    JLabel[] dessertLabel;
    JLabel[] dessertImage;
    String[] fileDesserts;
    int ELEMENTS = 12;
    int DRINK_ELEMENTS = 12;
    int DESSERT_ELEMENTS = 12;
    double total = 0;
    double food1, food2, food3, food4, food5, food6, food7, food8, food9, food10, food11, food12;
    double drink1, drink2, drink3, drink4, drink5, drink6, drink7, drink8, drink9, drink10, drink11, drink12;
    double pr, pr1, pr2, pr3, pr4, pr5, pr6, pr7, pr8, pr9, pr10, pr11;
    double totalForFoods;
    double totalForDrinks;
    double totalForDesserts;
    public void FoodMenuFrame(String id) throws IOException {
        setTitle("Main Menu");
        setBounds(0, 0,1382,744);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        c = getContentPane();
        c.setLayout(null);
        c.setBackground(Color.decode("#151515"));
        JLabel lblFoodOrdered = new JLabel("Food Ordered");
        lblFoodOrdered.setFont(new Font("Verdana", Font.BOLD, 15));
        lblFoodOrdered.setBounds(980, 11, 200, 24);
        lblFoodOrdered.setForeground(Color.white); //set bounds is like mix of set size and location
        c.add(lblFoodOrdered);
        dtm = new DefaultTableModel(0, 0);
        final String header[] = new String[] { "Item", "Qty", "Price", "Spinner" };
        dtm.setColumnIdentifiers(header);
        dtm.addRow(header);
        table = new JTable();
        table.setModel(dtm);
        table.setBounds(920, 40, 400, 500); // int x, int y, int width, int height
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(30);
        table.getColumnModel().getColumn(2).setPreferredWidth(30);
        table.getColumnModel().getColumn(3).setMinWidth(0); // hide spinner
        table.getColumnModel().getColumn(3).setMaxWidth(0); // hide spinner
        table.setShowGrid(true); // remove cell boarder
        c.add(table);
        JLabel lblTotal = new JLabel("Total  : ");
        lblTotal.setBounds(950, 550, 200, 25);
        lblTotal.setFont(new Font("verdana" ,Font.BOLD, 15));
        lblTotal.setForeground(Color.white);
        c.add(lblTotal);


        textField = new JTextField(); //for total
        textField.setBounds(1050, 550, 200, 25);
        textField.setFont(new Font("verdana" ,Font.BOLD, 18));
        c.add(textField);
        textField.setColumns(10);


        orderButton = new JButton("Order");
        orderButton.setBounds(950, 600, 89, 23);
        orderButton.addActionListener(this);
        c.add(orderButton);
        orderButton.addActionListener(new ActionListener() {

            
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (textField.getText().equals("")) 
            {
                JOptionPane.showMessageDialog(null, "You not ordered anything yet");
            } 
            int total_bill = (int)Math.round(total);
            int user_id = Integer.parseInt(id);
            User user = getTotalBill(total_bill, user_id);
            if(user != null)
            {
                 Order mainFrame = new Order();
                 try {
                     mainFrame.initialize(user);
                 } catch (IOException e1) {
                     // TODO Auto-generated catch block
                     e1.printStackTrace();
                 }
                 dispose();
            }
            }
           
            
        });

        backButton = new JButton("Back");
        backButton.setBounds(1050, 600, 89, 23);
        backButton.addActionListener(this);
        c.add(backButton);
        backButton.addActionListener(new ActionListener() {

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

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        addIt(tabbedPane, "Foods");
        addIt1(tabbedPane, "Drinks");
        addIt2(tabbedPane, "Desserts");
        tabbedPane.setBounds(8, 10, 900, 670);
        tabbedPane.setBackground(Color.decode("#2ADDC0"));
        c.add(tabbedPane);

        setVisible(true);
    }

    void addIt(JTabbedPane tabbedPane, String text) throws IOException {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#2ADDC0"));
        gbc = new GridBagConstraints();
        foodImage = new JLabel[ELEMENTS];
        foodLabel = new JLabel[ELEMENTS];
        numSpinner = new JSpinner[ELEMENTS];
        file = new String[ELEMENTS];
        price = new Double[ELEMENTS];

        file[0] = new String("/image/MedSalad.png");
        file[1] = new String("/image/JapanesePanNoodles.png");
        file[2] = new String("/image/spaghetti.jpg");
        file[3] = new String("/image/PadThai.png");
        file[4] = new String("/image/RamenNoodles.png");
        file[5] = new String("/image/kids_spaghetti.png");
        file[6] = new String("/image/chickenRice.jpg");
        file[7] = new String("/image/thaiFood.jpg");
        file[8] = new String("/image/vietnamFood.jpg");
        file[9] = new String("/image/paneertikka.jpg");
        file[10] = new String("/image/pavbhaji.jpg");
        file[11] = new String("/image/burger.jpg");

        foodLabel[0] = new JLabel("Salad");
        foodLabel[0].setFont(new Font("Verdana", Font.BOLD, 12));
        foodLabel[1] = new JLabel("Japanese Noodles");
        foodLabel[1].setFont(new Font("Verdana", Font.BOLD, 12));
        foodLabel[2] = new JLabel("Spaghetti");
        foodLabel[2].setFont(new Font("Verdana", Font.BOLD, 12));
        foodLabel[3] = new JLabel("Spaghetti Meat Balls");
        foodLabel[3].setFont(new Font("Verdana", Font.BOLD, 12));
        foodLabel[4] = new JLabel("Noodles");
        foodLabel[4].setFont(new Font("Verdana", Font.BOLD, 12));
        foodLabel[5] = new JLabel("Kids Spaghetti");
        foodLabel[5].setFont(new Font("Verdana", Font.BOLD, 12));
        foodLabel[6] = new JLabel("Chicken Rice");
        foodLabel[6].setFont(new Font("Verdana", Font.BOLD, 12));
        foodLabel[7] = new JLabel("Thai Food");
        foodLabel[7].setFont(new Font("Verdana", Font.BOLD, 12));
        foodLabel[8] = new JLabel("Vietnam Food");
        foodLabel[8].setFont(new Font("Verdana", Font.BOLD, 12));
        foodLabel[9] = new JLabel("Paneer Tikka Masala");
        foodLabel[9].setFont(new Font("Verdana", Font.BOLD, 12));
        foodLabel[10] = new JLabel("Pav Bhaji");
        foodLabel[10].setFont(new Font("Verdana", Font.BOLD, 12));
        foodLabel[11] = new JLabel("Burger");
        foodLabel[11].setFont(new Font("Verdana", Font.BOLD, 12));

        price[0] = 350.00;
        price[1] = 450.00;
        price[2] = 370.00;
        price[3] = 450.00;
        price[4] = 550.00;
        price[5] = 400.00;
        price[6] = 350.00;
        price[7] = 650.00;
        price[8] = 755.00;
        price[9] = 375.00;
        price[10] = 325.00;
        price[11] = 350.00;

        for (int i = 0; i < ELEMENTS; i++) {
        try {
            
            Image image = ImageIO.read(this.getClass().getResource(file[i]));
            Image imageScaled = image.getScaledInstance(125, 125, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(imageScaled);
            SpinnerNumberModel spnummodel = new SpinnerNumberModel(0, 0, 10, 1); // value,minimum,maximum,stepSize
            numSpinner[i] = new JSpinner(spnummodel);
            numSpinner[i].addChangeListener(listener);
            foodImage[i] = new JLabel(imageIcon);
            }catch(Exception e) {
                System.out.print(e);
            }
        }

        gbc.gridx = 0; // gridx 0 represent the most left
        gbc.insets = new Insets(10, 10, 10, 10); // top,left,bottom,right
        for (int i = 0; i < ELEMENTS; i++) {
            if (i % 4 == 0) {
                gbc.gridy += 2;
                gbc.gridx = 0;
            }
            panel.add(foodImage[i], gbc);
            gbc.gridy++; // gridy---> add one row,for foodLabel
            panel.add(foodLabel[i], gbc);
            gbc.gridy--; // remove the row
            gbc.gridx++; // move to next column
            panel.add(numSpinner[i], gbc);
            gbc.gridx++; // move to next column
            tabbedPane.addTab(text, panel);
        }
    }

    void addIt1(JTabbedPane tabbedPane, String text) throws IOException {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#2ADDC0"));
        GridBagConstraints gbc = new GridBagConstraints();
        drinkImage = new JLabel[DRINK_ELEMENTS];
        drinkLabel = new JLabel[DRINK_ELEMENTS];
        numSpinnerDrinks = new JSpinner[DRINK_ELEMENTS];
        priceDrinks = new Double[DRINK_ELEMENTS];

        fileDrinks = new String[DRINK_ELEMENTS];
        fileDrinks[0] = new String("/image/raspberry.jpg");
        fileDrinks[1] = new String("/image/chocalate_pudding.jpg");
        fileDrinks[2] = new String("/image/blue hawailan.jpg");
        fileDrinks[3] = new String("/image/Pina.jpg");
        fileDrinks[4] = new String("/image/lemon ice.jpg");
        fileDrinks[5] = new String("/image/Margarita.jpg");
        fileDrinks[6] = new String("/image/Cosmopolitan.jpg");
        fileDrinks[7] = new String("/image/MoscowMule.jpg");
        fileDrinks[8] = new String("/image/Martini.jpg");
        fileDrinks[9] = new String("/image/Watermelonslush.jpg");
        fileDrinks[10] = new String("/image/Orange Punch.jpg");
        fileDrinks[11] = new String("/image/hotchoc.jpg");


        drinkLabel[0] = new JLabel("Raspberry");
        drinkLabel[0].setFont(new Font("Verdana", Font.BOLD, 12));
        drinkLabel[1] = new JLabel("Chocolate Pudding");
        drinkLabel[1].setFont(new Font("Verdana", Font.BOLD, 12));
        drinkLabel[2] = new JLabel("Blue Hawailan");
        drinkLabel[2].setFont(new Font("Verdana", Font.BOLD, 12));
        drinkLabel[3] = new JLabel("Pina");
        drinkLabel[3].setFont(new Font("Verdana", Font.BOLD, 12));
        drinkLabel[4] = new JLabel("Lemon Ice");
        drinkLabel[4].setFont(new Font("Verdana", Font.BOLD, 12));
        drinkLabel[5] = new JLabel("Margarita");
        drinkLabel[5].setFont(new Font("Verdana", Font.BOLD, 12));
        drinkLabel[6] = new JLabel("Cosmopolitan");
        drinkLabel[6].setFont(new Font("Verdana", Font.BOLD, 12));
        drinkLabel[7] = new JLabel("Moscow Mule");
        drinkLabel[7].setFont(new Font("Verdana", Font.BOLD, 12));
        drinkLabel[8] = new JLabel("Martini");
        drinkLabel[8].setFont(new Font("Verdana", Font.BOLD, 12));
        drinkLabel[9] = new JLabel("Watermelon slush");
        drinkLabel[9].setFont(new Font("Verdana", Font.BOLD, 12));
        drinkLabel[10] = new JLabel("Orange Punch");
        drinkLabel[10].setFont(new Font("Verdana", Font.BOLD, 12));
        drinkLabel[11] = new JLabel("Hot chocolate");
        drinkLabel[11].setFont(new Font("Verdana", Font.BOLD, 12));

        priceDrinks[0] = 350.00;
        priceDrinks[1] = 450.00;
        priceDrinks[2] = 300.00;
        priceDrinks[3] = 500.00;
        priceDrinks[4] = 300.00;
        priceDrinks[5] = 350.00;
        priceDrinks[6] = 450.00;
        priceDrinks[7] = 300.00;
        priceDrinks[8] = 500.00;
        priceDrinks[9] = 300.00;
        priceDrinks[10] = 300.00;
        priceDrinks[11] = 500.00;


        for (int i = 0; i < DRINK_ELEMENTS; i++) {
            Image image = ImageIO.read(this.getClass().getResource(fileDrinks[i]));
            Image imageScaled = image.getScaledInstance(125, 125, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(imageScaled);
            SpinnerNumberModel spnummodel = new SpinnerNumberModel(0, 0, 10, 1); // value,minimum,maximum,stepSize
            numSpinnerDrinks[i] = new JSpinner(spnummodel);
            numSpinnerDrinks[i].addChangeListener(listenerForDrinks);
            drinkImage[i] = new JLabel(imageIcon);
        }
        gbc.gridx = 0; // gridx 0 represent the most left
        gbc.insets = new Insets(10, 10, 10, 10); // top,left,bottom,right
        for (int i = 0; i < DRINK_ELEMENTS; i++) {
            if (i % 4 == 0) {
                gbc.gridx = 0;
                gbc.gridy += 2;
            }
            panel.add(drinkImage[i], gbc);
            gbc.gridy++; // gridy---> add one row,for foodLabel
            panel.add(drinkLabel[i], gbc);
            gbc.gridy--; // remove the row
            gbc.gridx++; // move to next column
            panel.add(numSpinnerDrinks[i], gbc);
            gbc.gridx++; // move to next column
            tabbedPane.addTab(text, panel);

        }
    }

    void addIt2(JTabbedPane tabbedPane, String text) throws IOException {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#2ADDC0"));
        GridBagConstraints gbc = new GridBagConstraints();
        dessertImage = new JLabel[DESSERT_ELEMENTS];
        dessertLabel = new JLabel[DESSERT_ELEMENTS];
        numSpinnerDesserts = new JSpinner[DESSERT_ELEMENTS];
        priceDesserts = new Double[DESSERT_ELEMENTS];

        fileDesserts = new String[DESSERT_ELEMENTS];
        fileDesserts[0] = new String("/image/strawberry cake.jpg");
        fileDesserts[1] = new String("/image/chocolate cake.jpg");
        fileDesserts[2] = new String("/image/cccookies.jpg");
        fileDesserts[3] = new String("/image/applepie.jpg");
        fileDesserts[4] = new String("/image/cheesecake.jpg");
        fileDesserts[5] = new String("/image/carrotcake.jpg");
        fileDesserts[6] = new String("/image/icecream.jpg");
        fileDesserts[7] = new String("/image/bananapudding.jpg");
        fileDesserts[8] = new String("/image/pecanPie.jpg");
        fileDesserts[9] = new String("/image/bostoncream.jpg");
        fileDesserts[10] = new String("/image/pantrycake.jpg");
        fileDesserts[11] = new String("/image/swedishalmondcake.jpg");

        dessertLabel[0] = new JLabel("Strawberry Cake");
        dessertLabel[0].setFont(new Font("Verdana", Font.BOLD, 12));
        dessertLabel[1] = new JLabel("Chocolate Cake");
        dessertLabel[1].setFont(new Font("Verdana", Font.BOLD, 12));
        dessertLabel[2] = new JLabel("Chocolate Chip Cookies");
        dessertLabel[2].setFont(new Font("Verdana", Font.BOLD, 12));
        dessertLabel[3] = new JLabel("Apple Pie");
        dessertLabel[3].setFont(new Font("Verdana", Font.BOLD, 12));
        dessertLabel[4] = new JLabel("Cheesecake");
        dessertLabel[4].setFont(new Font("Verdana", Font.BOLD, 12));
        dessertLabel[5] = new JLabel("Carrot Cake");
        dessertLabel[5].setFont(new Font("Verdana", Font.BOLD, 12));
        dessertLabel[6] = new JLabel("Ice Cream");
        dessertLabel[6].setFont(new Font("Verdana", Font.BOLD, 12));
        dessertLabel[7] = new JLabel("Banana Pudding");
        dessertLabel[7].setFont(new Font("Verdana", Font.BOLD, 12));
        dessertLabel[8] = new JLabel("Pecan pie");
        dessertLabel[8].setFont(new Font("Verdana", Font.BOLD, 12));
        dessertLabel[9] = new JLabel("Boston cream pie");
        dessertLabel[9].setFont(new Font("Verdana", Font.BOLD, 12));
        dessertLabel[10] = new JLabel("Pantry Crumb Cake");
        dessertLabel[10].setFont(new Font("Verdana", Font.BOLD, 12));
        dessertLabel[11] = new JLabel("Swedish Almond Cake");
        dessertLabel[11].setFont(new Font("Verdana", Font.BOLD, 12));

        priceDesserts[0] = 450.00;
        priceDesserts[1] = 300.00;
        priceDesserts[2] = 450.00;
        priceDesserts[3] = 300.00;
        priceDesserts[4] = 450.00;
        priceDesserts[5] = 300.00;
        priceDesserts[6] = 550.00;
        priceDesserts[7] = 300.00;
        priceDesserts[8] = 450.00;
        priceDesserts[9] = 300.00;
        priceDesserts[10] = 350.00;
        priceDesserts[11] = 300.00;

        for (int i = 0; i < DESSERT_ELEMENTS; i++) {
            Image image = ImageIO.read(this.getClass().getResource(fileDesserts[i]));
            Image imageScaled = image.getScaledInstance(125, 125, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(imageScaled);
            SpinnerNumberModel spnummodel = new SpinnerNumberModel(0, 0, 10, 1); // value,minimum,maximum,stepSize
            numSpinnerDesserts[i] = new JSpinner(spnummodel);
            numSpinnerDesserts[i].addChangeListener(listenerForDesserts);
            dessertImage[i] = new JLabel(imageIcon);
        }
        gbc.gridx = 0; // gridx 0 represent the most left
        gbc.insets = new Insets(10, 10, 10, 10); // top,left,bottom,right
        for (int i = 0; i < DESSERT_ELEMENTS; i++) {
            if (i % 4 == 0) {
                gbc.gridx = 0;
                gbc.gridy += 2;
            }
            panel.add(dessertImage[i], gbc);
            gbc.gridy++; // gridy---> add one row,for foodLabel
            panel.add(dessertLabel[i], gbc);
            gbc.gridy--; // remove the row
            gbc.gridx++; // move to next column
            panel.add(numSpinnerDesserts[i], gbc);
            gbc.gridx++; // move to next column
            tabbedPane.addTab(text, panel);
        }

    }

    public void actionPerformed(ActionEvent ae){}

    ChangeListener listener = new ChangeListener() {
        public void stateChanged(ChangeEvent e) {

            final int quantity = (int) ((JSpinner) e.getSource()).getValue();
            final int rows = table.getRowCount();
            for (int row = 0; row < rows; row++) {
                if (dtm.getValueAt(row, 3) == e.getSource()) 
                {
                    if (dtm.getValueAt(row, 0).equals("Salad")) {
                        dtm.setValueAt(quantity, row, 1); // obj, row, column
                        dtm.setValueAt(price[0] * quantity, row, 2);
                        food1 = price[0] * quantity;

                    } else if (dtm.getValueAt(row, 0).equals("Japanese Noodles")) {

                        dtm.setValueAt(quantity, row, 1);
                        dtm.setValueAt(price[1] * quantity, row, 2);
                        food2 = price[1] * quantity;
                    } else if (dtm.getValueAt(row, 0).equals("Spaghetti")) {

                        dtm.setValueAt(quantity, row, 1);
                        dtm.setValueAt(price[2] * quantity, row, 2);
                        food3 = price[2] * quantity;
                    } else if (dtm.getValueAt(row, 0).equals("Spaghetti Meat Balls")) {

                        dtm.setValueAt(quantity, row, 1);
                        dtm.setValueAt(price[3] * quantity, row, 2);
                        food4 = price[3] * quantity;
                    } else if (dtm.getValueAt(row, 0).equals("Noodles")) {

                        dtm.setValueAt(quantity, row, 1);
                        dtm.setValueAt(price[4] * quantity, row, 2);
                        food5 = price[4] * quantity;
                    } else if (dtm.getValueAt(row, 0).equals("Kids Spaghetti")) {

                        dtm.setValueAt(quantity, row, 1);
                        dtm.setValueAt(price[5] * quantity, row, 2);
                        food6 = price[5] * quantity;
                    } else if (dtm.getValueAt(row, 0).equals("Chicken Rice")) {

                        dtm.setValueAt(quantity, row, 1); // obj, row,
                                                            // column
                        dtm.setValueAt(price[6] * quantity, row, 2);
                        food7 = price[6] * quantity;
                    } else if (dtm.getValueAt(row, 0).equals("Thai Food")) {

                        dtm.setValueAt(quantity, row, 1); // obj, row,
                                                            // column
                        dtm.setValueAt(price[7] * quantity, row, 2);
                        food8 = price[7] * quantity;
                    } else if (dtm.getValueAt(row, 0).equals("Vietnam Food")) {

                        dtm.setValueAt(quantity, row, 1); // obj, row,
                                                            // column
                        dtm.setValueAt(price[8] * quantity, row, 2);
                        food9 = price[8] * quantity;
                    }else if (dtm.getValueAt(row, 0).equals("Paneer Tikka Masala")) {

                        dtm.setValueAt(quantity, row, 1); // obj, row,
                                                            // column
                        dtm.setValueAt(price[9] * quantity, row, 2);
                        food10 = price[9] * quantity;
                    }else if (dtm.getValueAt(row, 0).equals("Pav Bhaji")) {

                        dtm.setValueAt(quantity, row, 1); // obj, row,
                                                            // column
                        dtm.setValueAt(price[10] * quantity, row, 2);
                        food11 = price[10] * quantity;
                    }else if (dtm.getValueAt(row, 0).equals("Burger")) {

                        dtm.setValueAt(quantity, row, 1); // obj, row,
                                                            // column
                        dtm.setValueAt(price[11] * quantity, row, 2);
                        food12 = price[11] * quantity;
                    }

                    if (quantity == 0) {
                        dtm.removeRow(row);
                    }
                    totalForFoods = food1 + food2 + food3 + food4 + food5 + food6 + food7 + food8 + food9+ food10+ food11+ food12;
                    total = totalForFoods + totalForDrinks + totalForDesserts;
                    textField.setText(total + "");
                    return;
                }
            }

            // there was no row with this JSpinner, so we have to add it
            for (int i = 0; i < ELEMENTS; i++) {
                // looking for the "clicked" JSpinner
                if (numSpinner[i] == e.getSource()) {
                    totalPrice = price[i];
                    switch (foodLabel[i].getText()) {
                    case "Salad":
                        food1 = price[0];
                        break;
                    case "Japanese Noodles":
                        food2 = price[1];
                        break;
                    case "Spaghetti":
                        food3 = price[2];
                        break;
                    case "Spaghetti Meat Balls":
                        food4 = price[3];
                        break;
                    case "Noodles":
                        food5 = price[4];
                        break;
                    case "Kids Spaghetti":
                        food6 = price[5];
                        break;
                    case "Chicken Rice":
                        food7 = price[6];
                        break;
                    case "Thai Food":
                        food8 = price[7];
                        break;
                    case "Vietnam Food":
                        food9 = price[8];
                        break;
                    case "Paneer Tikka Masala":
                        food10 = price[9];
                        break;
                    case "Pav Bhaji":
                        food11 = price[10];
                        break;
                    case "Burger":
                        food12 = price[11];
                        break;

                    }
                    totalForFoods = food1 + food2 + food3 + food4 + food5 + food6 + food7 + food8 + food9+ food10+ food11+ food12;
                    total = totalForFoods + totalForDrinks + totalForDesserts;
                    textField.setText(total + "");
                    dtm.addRow(new Object[] { foodLabel[i].getText(), quantity, totalPrice, numSpinner[i] });
                    return;
                }

            }
        }
    };

    ChangeListener listenerForDesserts = new ChangeListener() {
        public void stateChanged(ChangeEvent e) {

            final int quantity = (int) ((JSpinner) e.getSource()).getValue();
            final int rows = table.getRowCount();

            for (int row = 0; row < rows; row++) {
                if (dtm.getValueAt(row, 3) == e.getSource()) {
                    if (dtm.getValueAt(row, 0).equals("Strawberry Cake")) {
                        dtm.setValueAt(quantity, row, 1); // obj, row,
                        dtm.setValueAt(priceDesserts[0] * quantity, row, 2);
                        pr =priceDesserts[0] * quantity; // column
                    } else if (dtm.getValueAt(row, 0).equals("Chocolate Cake")) {
                        dtm.setValueAt(quantity, row, 1); // obj, row, // column
                        dtm.setValueAt(priceDesserts[1] * quantity, row, 2);
                        pr1 = priceDesserts[1] * quantity;
                    }
                    else if (dtm.getValueAt(row, 0).equals("Chocolate Chip Cookies")) {
                        dtm.setValueAt(quantity, row, 1); // obj, row, // column
                        dtm.setValueAt(priceDesserts[2] * quantity, row, 2);
                        pr2 = priceDesserts[2] * quantity;
                    }
                    else if (dtm.getValueAt(row, 0).equals("Apple Pie")) {
                        dtm.setValueAt(quantity, row, 1); // obj, row, // column
                        dtm.setValueAt(priceDesserts[3] * quantity, row, 2);
                        pr3 = priceDesserts[3] * quantity;
                    }
                    else if (dtm.getValueAt(row, 0).equals("Cheesecake")) {
                        dtm.setValueAt(quantity, row, 1); // obj, row, // column
                        dtm.setValueAt(priceDesserts[4] * quantity, row, 2);
                        pr4 = priceDesserts[4] * quantity;
                    }
                    else if (dtm.getValueAt(row, 0).equals("Carrot Cake")) {
                        dtm.setValueAt(quantity, row, 1); // obj, row, // column
                        dtm.setValueAt(priceDesserts[5] * quantity, row, 2);
                        pr5 = priceDesserts[5] * quantity;
                    }
                    else if (dtm.getValueAt(row, 0).equals("Ice Cream")) {
                        dtm.setValueAt(quantity, row, 1); // obj, row, // column
                        dtm.setValueAt(priceDesserts[6] * quantity, row, 2);
                        pr6 = priceDesserts[6] * quantity;
                    }
                    else if (dtm.getValueAt(row, 0).equals("Banana Pudding")) {
                        dtm.setValueAt(quantity, row, 1); // obj, row, // column
                        dtm.setValueAt(priceDesserts[7] * quantity, row, 2);
                        pr7 = priceDesserts[7] * quantity;
                    }
                    else if (dtm.getValueAt(row, 0).equals("Pecan pie")) {
                        dtm.setValueAt(quantity, row, 1); // obj, row, // column
                        dtm.setValueAt(priceDesserts[8] * quantity, row, 2);
                        pr8 = priceDesserts[8] * quantity;
                    }
                    else if (dtm.getValueAt(row, 0).equals("Boston cream pie")) {
                        dtm.setValueAt(quantity, row, 1); // obj, row, // column
                        dtm.setValueAt(priceDesserts[9] * quantity, row, 2);
                        pr9 = priceDesserts[9] * quantity;
                    }
                    else if (dtm.getValueAt(row, 0).equals("Pantry Crumb Cake")) {
                        dtm.setValueAt(quantity, row, 1); // obj, row, // column
                        dtm.setValueAt(priceDesserts[10] * quantity, row, 2);
                        pr10 = priceDesserts[10] * quantity;
                    }
                    else if (dtm.getValueAt(row, 0).equals("Swedish Almond Cake")) {
                        dtm.setValueAt(quantity, row, 1); // obj, row, // column
                        dtm.setValueAt(priceDesserts[11] * quantity, row, 2);
                        pr11 = priceDesserts[11] * quantity;
                    }
                    if (quantity == 0) {
                        dtm.removeRow(row);
                    }
                    totalForDesserts = pr + pr1 + pr2 + pr3 + pr4 + pr5+ pr6+ pr7+ pr8+ pr9+ pr10+ pr11;
                    total = totalForFoods + totalForDrinks + totalForDesserts;
                    textField.setText(total + "");
                    return;
                }
            }

            // there was no row with this JSpinner, so we have to add it
            for (int i = 0; i < DESSERT_ELEMENTS; i++) {
                // looking for the "clicked" JSpinner
                if (numSpinnerDesserts[i] == e.getSource()) {
                    totalPrice = priceDesserts[i];
                    switch (dessertLabel[i].getText()) {
                    case "Strawberry Cake":
                        pr = priceDesserts[0];
                        break;
                    case "Chocolate Cake":
                        pr1 = priceDesserts[1];
                        break;
                    case "Chocolate Chip Cookies":
                        pr2 = priceDesserts[2];
                        break;
                    case "Apple Pie":
                        pr3 = priceDesserts[3];
                        break;
                    case "Cheesecake":
                        pr4 = priceDesserts[4];
                        break;
                    case "Carrot Cake":
                        pr5 = priceDesserts[5];
                        break;
                    case "Ice Cream":
                        pr6 = priceDesserts[6];
                        break;
                    case "Banana Pudding":
                        pr7 = priceDesserts[7];
                        break;
                    case "Pecan pie":
                        pr8 = priceDesserts[8];
                        break;
                    case "Boston cream pie":
                        pr9 = priceDesserts[9];
                        break;
                    case "Pantry Crumb Cake":
                        pr10 = priceDesserts[10];
                        break;
                    case "Swedish Almond Cake":
                        pr11 = priceDesserts[11];
                        break;
                    }
                    totalForDesserts = pr + pr1 + pr2 + pr3 + pr4 + pr5+ pr6+ pr7+ pr8+ pr9+ pr10+ pr11;
                    total = totalForFoods + totalForDrinks + totalForDesserts;
                    textField.setText(total + "");
                    dtm.addRow(new Object[] { dessertLabel[i].getText(), quantity, totalPrice, numSpinnerDesserts[i] });
                    return;
                }

            }
        }
    };

    ChangeListener listenerForDrinks = new ChangeListener() {
        public void stateChanged(ChangeEvent e) {

            final int quantity = (int) ((JSpinner) e.getSource()).getValue();
            final int rows = table.getRowCount();
            for (int row = 0; row < rows; row++) {
                if (dtm.getValueAt(row, 3) == e.getSource()) {
                    if (dtm.getValueAt(row, 0).equals("Raspberry")) {
                        dtm.setValueAt(quantity, row, 1);
                        dtm.setValueAt(priceDrinks[0] * quantity, row, 2);
                        drink1 = priceDrinks[0] * quantity;

                    } else if (dtm.getValueAt(row, 0).equals("Chocolate Pudding")) {
                        dtm.setValueAt(quantity, row, 1); // obj, row,
                                                            // column
                        dtm.setValueAt(priceDrinks[1] * quantity, row, 2);
                        drink2 = priceDrinks[1] * quantity;

                    } else if (dtm.getValueAt(row, 0).equals("Blue Hawailan")) {

                        dtm.setValueAt(quantity, row, 1);
                        dtm.setValueAt(priceDrinks[2]* quantity, row, 2);
                        drink3 = priceDrinks[2] * quantity;

                    } else if (dtm.getValueAt(row, 0).equals("Pina")) {

                        dtm.setValueAt(quantity, row, 1);
                        dtm.setValueAt(priceDrinks[3] * quantity, row, 2);
                        drink4 = priceDrinks[3] * quantity;

                    } else if (dtm.getValueAt(row, 0).equals("Lemon Ice")) {

                        dtm.setValueAt(quantity, row, 1); // obj, row,
                                                            // column
                        dtm.setValueAt(priceDrinks[4]* quantity, row, 2);
                        drink5 = priceDrinks[4] * quantity;

                    }
                    else if (dtm.getValueAt(row, 0).equals("Margarita")) {

                        dtm.setValueAt(quantity, row, 1); // obj, row,
                                                            // column
                        dtm.setValueAt(priceDrinks[5] * quantity, row, 2);
                        drink6 = priceDrinks[5] * quantity;

                    }
                    else if (dtm.getValueAt(row, 0).equals("Cosmopolitan")) {

                        dtm.setValueAt(quantity, row, 1); // obj, row,
                                                            // column
                        dtm.setValueAt(priceDrinks[6] * quantity, row, 2);
                        drink7 = priceDrinks[6] * quantity;

                    }
                    else if (dtm.getValueAt(row, 0).equals("Moscow Mule")) {

                        dtm.setValueAt(quantity, row, 1); // obj, row,
                                                            // column
                        dtm.setValueAt(priceDrinks[7] * quantity, row, 2);
                        drink8 = priceDrinks[7] * quantity;

                    }
                    else if (dtm.getValueAt(row, 0).equals("Martini")) {

                        dtm.setValueAt(quantity, row, 1); // obj, row,
                                                            // column
                        dtm.setValueAt(priceDrinks[8] * quantity, row, 2);
                        drink9 = priceDrinks[8] * quantity;

                    }
                    else if (dtm.getValueAt(row, 0).equals("Watermelon slush")) {

                        dtm.setValueAt(quantity, row, 1); // obj, row,
                                                            // column
                        dtm.setValueAt(priceDrinks[9] * quantity, row, 2);
                        drink10 = priceDrinks[9] * quantity;

                    }
                    else if (dtm.getValueAt(row, 0).equals("Orange Punch")) {

                        dtm.setValueAt(quantity, row, 1); // obj, row,
                                                            // column
                        dtm.setValueAt(priceDrinks[10] * quantity, row, 2);
                        drink11 = priceDrinks[10] * quantity;

                    }
                    else if (dtm.getValueAt(row, 0).equals("Hot chocolate")) {

                        dtm.setValueAt(quantity, row, 1); // obj, row,
                                                            // column
                        dtm.setValueAt(priceDrinks[11] * quantity, row, 2);
                        drink12 = priceDrinks[11] * quantity;

                    }
                    if (quantity == 0) {
                        dtm.removeRow(row);
                    }
                    totalForDrinks = drink1 + drink2 + drink3 + drink4 + drink5+ drink6+ drink7+ drink8+ drink9+ drink10+ drink11+ drink12;
                    total = totalForFoods + totalForDrinks + totalForDesserts;
                    textField.setText(total + "");

                    return;
                }
            }

            // there was no row with this JSpinner, so we have to add it
            for (int i = 0; i < DRINK_ELEMENTS; i++) {
                // looking for the "clicked" JSpinner
                if (numSpinnerDrinks[i] == e.getSource()) {
                    totalPrice = priceDrinks[i];
                    switch (drinkLabel[i].getText()) {
                    case "Raspberry":
                        drink1 = priceDrinks[0];
                        break;
                    case "Chocolate Pudding":
                        drink2 = priceDrinks[1];
                        break;
                    case "Blue Hawailan":
                        drink3 = priceDrinks[2];
                        break;
                    case "Pina":
                        drink4 = priceDrinks[3];
                        break;
                    case "Lemon Ice":
                        drink5 = priceDrinks[4];
                        break;
                    case "Margarita":
                        drink6 = priceDrinks[5];
                        break;
                    case "Cosmopolitan":
                        drink7 = priceDrinks[6];
                        break;
                    case "Moscow Mule":
                        drink8 = priceDrinks[7];
                        break;
                    case "Martini":
                        drink9 = priceDrinks[8];
                        break;
                    case "Watermelon slush":
                        drink10 = priceDrinks[9];
                        break;
                    case "Orange Punch":
                        drink11 = priceDrinks[10];
                        break;
                    case "Hot chocolate":
                        drink12 = priceDrinks[11];
                        break;
                    }
                    totalForDrinks = drink1 + drink2 + drink3 + drink4 + drink5+ drink6+ drink7+ drink8+ drink9+ drink10+ drink11+ drink12;
                    total = totalForFoods + totalForDrinks + totalForDesserts;
                    textField.setText(total + "");
                    dtm.addRow(new Object[] { drinkLabel[i].getText(), quantity, totalPrice, numSpinnerDrinks[i] });
                    return;
                }

            }
        };
    
};
private User getTotalBill(int total_bill , int id) {
            User user = new User();
            final String DB_URL = "jdbc:mysql://localhost:3306/onlineFood?serverTimezone=UTC";
            final String USERNAME = "root";
            final String PASSWORD = "Prachip@109";
            try{
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String sql = "SELECT * FROM login WHERE id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                String sql1 = "INSERT INTO orders (customer_id , total) " + "VALUES("+id+"," + total_bill +")";
                PreparedStatement preparedStatement1 = conn.prepareStatement(sql1);
                int resultSet1 = preparedStatement1.executeUpdate();
                if (resultSet1 > 0) {
                    user = new User();
                    user.total= Integer.toString(total_bill);
                }
                if(resultSet.next())
                {
                    //user = new User();
                    user.name = resultSet.getString("name");
                    user.email = resultSet.getString("email");
                    user.phone = resultSet.getString("phone");
                    user.address = resultSet.getString("address");
                    user.password = resultSet.getString("password");
                    user.id = resultSet.getString("id");
                    user.total = Integer.toString(total_bill);
                }
                preparedStatement.close();
                conn.close();
            }catch(Exception e){
                System.out.println(e);
            }
            return user;
        };

}
