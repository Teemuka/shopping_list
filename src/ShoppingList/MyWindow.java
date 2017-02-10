/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ShoppingList;

import MyComponents.MyTableModel;
import MyListeners.MyMouseListener;
import MyComponents.MyButton;
import MyComponents.MyJMenuItem;
import MyLinkedList.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * @author Teemu Kaunisto  {@literal (teemu.kaunisto@cs.tamk.fi)}
 * @version 3.0
 * @since 1.8
 */
public class MyWindow extends JFrame {

    MyLinkedList<Item> list;
    MyTableModel table;
    JTable jtable;
    JTextField item;
    JTextField amount;
    JPanel addItemJPanel;
    JScrollPane scrollPane;
    JMenuBar menubar;
    
    MyButton addButton;
    MyButton removeButton;
    
    MyJMenuItem print;
    MyJMenuItem load;
    MyJMenuItem save;
    MyJMenuItem savedb;
    MyJMenuItem loaddb;
    MyJMenuItem linkdb;
    
    JMenu menuDropDown;
    
    /**
     * Creates new graphical user interface window.
     */
    public MyWindow() {
   
        super("Shopping list application");
        
        this.list = Main.shoppingList;

        this.table = new MyTableModel();
        
        this.jtable = new JTable(table);
        this.scrollPane = new JScrollPane(jtable);
        
        // initializes components
        initializeJmenuBar();
        initializeButtons();
 
        initializeAddItem();
        
        initializeTable();
        initializeWindow();
    }    
    
    /**
     * Initializes window.
     * 
     */
    private void initializeWindow() {

        this.setSize(800, 640);
        this.setMinimumSize(new Dimension(400, 200));
        this.setContentPane(new JDesktopPane());
        
        this.getContentPane().setLayout(new GridLayout(1, 3));
        
        this.add(addItemJPanel);
        this.add(scrollPane);
        this.setJMenuBar(menubar);
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.setVisible(true);
    }

    /**
     * Initializes spesific JPanel.
     * 
     * Initializes panel that holds needed objects to add 
     * new item to shopping list.
     * 
     * @param e mouse event
     */
    private void initializeAddItem() {

        this.addItemJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
             
        this.addItemJPanel.setBackground(Color.red);
        
        this.amount = new JTextField("amount", 6);
        
        this.amount.addMouseListener(new MyMouseListener());
        
        this.item = new JTextField("name of the product", 12);
        
        this.item.addMouseListener(new MyMouseListener() {
            
            /**
             * anonymous inner class.
             * 
             * if mouse exits name of the product -jtext field
             * set it empty
             * 
             * @param e event triggered by mouse
             */
            @Override
            public void mouseExited(MouseEvent e) {

                if (item.getText().equalsIgnoreCase(""))
                item.setText("name of the product");
            } 
        });
            
        this.addItemJPanel.add(this.amount);
        this.addItemJPanel.add(this.item);
        this.addItemJPanel.add(this.addButton);
        this.addItemJPanel.add(this.removeButton);
    }

    /**
     * Initializes table.
     */
    private void initializeTable() {

        this.jtable.getTableHeader().setReorderingAllowed(false);
        this.jtable.setEnabled(false);
    }
    
    /**
     * Gets product name.
     * 
     * Name is given to TextArea by user.
     * 
     * @return gived name of the spesific item
     */
    public String getProductName() {
        
        String itemName = this.item.getText().trim();
        
        return itemName;
    }
    
    /**
     * Gets the original list.
     * 
     * @return list original shopping list
     */
    public MyLinkedList<Item> getList() {

        return this.list;
    }
    
    /**
     * Gets shopping list table.
     * 
     * @return shopping list table
     */
    public JTable getJTable() {
        
        return this.jtable;
    }
    
    /**
     * Gets table model.
     * 
     * @return used table model
     */
    public MyTableModel getMyTable() {
        
        return this.table;
    }

    /**
     * Gets amount of product.
     * 
     * Tries parse the String to Int. If gived amount is zero or
     * parsing is not succesful then amount of item is zero and error
     * message will show up.
     * 
     * @return gived amount of item or zero
     */
    public String getProductAmount() {
    
        String productAmount = this.amount.getText().trim();
        
        return productAmount;
    }

    /**
     * Initializes the buttons.
     */
    private void initializeButtons() {

        // remember to add custom tag
        this.addButton = new MyButton("Add", this, "add");
        this.removeButton = new MyButton("Remove", this, "remove");
    }
    
    /**
     * Gets main window.
     * 
     * @return the main window
     */
    public JFrame getMainWindow() {
        
        return this;
    }

    /**
     * Initializes JmenuBar.
     * 
     * jmenubar holds all vital functional buttons
     * 
     */
    private void initializeJmenuBar() {
    
        this.menubar = new JMenuBar();
        
        this.menuDropDown = new JMenu("File");
        this.menuDropDown.setMnemonic(KeyEvent.VK_F);   
        
        this.save = new MyJMenuItem("Save", KeyEvent.VK_S, this, "save");
        this.load = new MyJMenuItem("Load", KeyEvent.VK_L, this, "load");
        this.print = new MyJMenuItem("Print", KeyEvent.VK_P, this, "print");
        
        this.menuDropDown.add(save);
        this.menuDropDown.add(load);
        this.menuDropDown.add(print);
        
        this.menubar.add(menuDropDown);
        
        this.menuDropDown = new JMenu("DropBox");
        this.menuDropDown.setMnemonic(KeyEvent.VK_D);
        
        this.linkdb = new MyJMenuItem("Link to DropBox", 
                KeyEvent.VK_D, this, "dropbox");
        this.savedb = new MyJMenuItem("Save", KeyEvent.VK_S, this, "savedb");
        this.loaddb = new MyJMenuItem("Load", KeyEvent.VK_L, this, "loaddb");
        
        this.menuDropDown.add(linkdb);
        this.menuDropDown.add(savedb);
        this.menuDropDown.add(loaddb);
        
        this.menubar.add(menuDropDown);
    }
}
