package computershop;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Frame extends JFrame {
    
    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    CardLayout cardLayout;
    JPanel navbar;
    JPanel panelTitle;
    JPanel panelPrice;
    JPanel panelContainer;
    JPanel mainPanel;
    JPanel homePanel;
    JPanel priceMenu;
    JPanel panelNumberItems;
    JPanel panelShopping;
    JLabel welcome;
    JLabel labNumberItems;
    JLabel imageViewer;
    JLabel itemViewer;
    JLabel itemTitle;
    JLabel itemPrice;
    JComboBox inventory;
    JButton cancelBtn;
    JButton orderBtn;
    JTextField fieldNumberItems;
    JButton getPrice;
    JButton exitBtn;
    String nItems;
    boolean isNumber;
    String[] listInventory = { "" , "Lenovo Legion5", "NVIDIA RTX 4070Ti", "Crucial 32GB DDR5-5600", "Bambu Lab X1 Series", "Iphone SE 2020" , "HHKB HYBRID Type-S" , "Logitech Ergo M575 White" };
    String[] imgSrc = {"home.png" , "laptop.png" , "gpu.png" , "ram.png" , "printer.png" , "iphone.png" , "keyboard.png" , "mouse.png"};
    double[] prices = {0.0  , 949.99 , 899.0 , 124.49 , 799.99 , 184.20 , 343.49 , 44.50};
    int id;
    
    public Frame(String arg){
        super(arg);
        setLayout(new BorderLayout());
        
        
//        NAVBAR on the top
        welcome = new JLabel("Welcome, what are you looking for: ");
        welcome.setToolTipText("Choose a product from the list");
        inventory = new JComboBox(listInventory);
        getPrice = new JButton("Get price");
        getPrice.setBackground(new Color(0x0f82ff));
        getPrice.setToolTipText("See the price of the selected item");
        
        getPrice.addActionListener((ActionEvent e) -> {
            System.out.println("selected item: " + inventory.getSelectedItem() + " with index: " + inventory.getSelectedIndex());
            if(inventory.getSelectedIndex() == 0){
                JOptionPane.showMessageDialog(this , "You are supposted to choose something");
            }
            else{
//                Check if the given input is a number
                do{
                    nItems = JOptionPane.showInputDialog(this , "How many items do you wanna buy?", "Number of items", JOptionPane.QUESTION_MESSAGE);
                    isNumber = nItems.matches("-?\\d+(\\.\\d+)?");
                }while(!isNumber);
                
//                show the shopping menu
                cardLayout.show(panelContainer, "main");
                id = inventory.getSelectedIndex();
                itemTitle.setText(listInventory[id]);
//                round the last 2 digits
//                itemPrice.setText("Price: "+ df.format(prices[id]*Integer.valueOf(nItems)) + "$");
                itemPrice.setText("Price: "+ prices[id] + "$");
                itemViewer.setIcon(new ImageIcon("src/img/" + imgSrc[id]));
                fieldNumberItems.setText(nItems);
                
            }
        });

        
//        exit from the program
        exitBtn = new JButton("Exit");
        exitBtn.setToolTipText("Close this program");
        exitBtn.addActionListener((ActionEvent e) -> {
            dispose();
        });
                
                
//        search autocompletion for the JComboBox
        AutoCompletion.enable(inventory);
        navbar = new JPanel();
        navbar.setLayout(new FlowLayout(FlowLayout.CENTER ,  10 , 0 ));
        navbar.add(welcome);
        navbar.add(inventory);
        navbar.add(getPrice);
        navbar.add(exitBtn);


        
        
//        MAIN PANEL
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        labNumberItems = new JLabel("N.Items : ");
        fieldNumberItems = new JTextField("0");
        panelNumberItems = new JPanel();
        panelNumberItems.setLayout(new FlowLayout(FlowLayout.LEFT , 10 , 0));
        panelNumberItems.add(labNumberItems);
        panelNumberItems.add(fieldNumberItems);
        
        orderBtn = new JButton("Order");
        orderBtn.setBackground(new Color(0x0f82ff));
        orderBtn.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this , "Price of " + nItems + " " + listInventory[id] + " is : " + df.format(prices[id]*Integer.valueOf(nItems)) + "$");
        });

        
        cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener((ActionEvent e) -> {
            cardLayout.show(panelContainer, "home");
            inventory.setSelectedIndex(0);
        });


            
        panelShopping = new JPanel();
        panelShopping.setLayout(new FlowLayout(FlowLayout.LEFT , 10 , 0));
        panelShopping.add(orderBtn);
        panelShopping.add(cancelBtn);
        
        itemViewer = new JLabel(new ImageIcon("src/img/keyboard.png"));
        itemViewer.setBorder(new EmptyBorder(0,50,0,0));

        priceMenu = new JPanel();
        priceMenu.setLayout(new BoxLayout(priceMenu , BoxLayout.Y_AXIS));
        priceMenu.setBorder(new EmptyBorder(100,0,200,50));
        itemTitle = new JLabel("Item's name");
        itemTitle.setFont(new Font("Segoe UI" , Font.BOLD , 32));
        itemPrice = new JLabel("Price: 0.0$");
        itemPrice.setFont(new Font("Segoe UI" , Font.BOLD , 20));
        
        panelTitle = new JPanel();
        panelPrice = new JPanel();
        panelTitle.setLayout(new FlowLayout(FlowLayout.LEFT ,  10 , 0 ));
        panelPrice.setLayout(new FlowLayout(FlowLayout.LEFT ,  10 , 0 ));
        panelTitle.add(itemTitle);
        panelPrice.add(itemPrice);
        
        priceMenu.add(panelTitle);
        priceMenu.add(panelPrice);
        priceMenu.add(panelNumberItems);
        priceMenu.add(panelShopping);
        mainPanel.add(itemViewer,BorderLayout.WEST);
        mainPanel.add(priceMenu,BorderLayout.EAST);

        
//        HOME PANEL
        homePanel = new JPanel();
        homePanel.setLayout(new GridBagLayout());
        ImageIcon homeImage = new ImageIcon("src/img/home.png");
        imageViewer = new JLabel(homeImage);
        imageViewer.setToolTipText("Home image, with a bunch of computer parts");
        homePanel.add(imageViewer);
        
//        CARD LAYOUT
        cardLayout = new CardLayout();
        panelContainer = new JPanel();
        panelContainer.setLayout(cardLayout);
        panelContainer.add(homePanel , "home");
        panelContainer.add(mainPanel , "main");
        cardLayout.show(panelContainer, "home");
        

        
//        FRAME SETTINGS
        this.add(navbar , BorderLayout.NORTH);
        this.add(panelContainer , BorderLayout.CENTER);


        ImageIcon icon = new ImageIcon("src/img/icon.png");
        setIconImage(icon.getImage());
        
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        
        setVisible(true);
    }
}


    
        
