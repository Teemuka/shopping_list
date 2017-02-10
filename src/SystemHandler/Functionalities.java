/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SystemHandler;

import MyComponents.MyTableModel;
import MyLinkedList.MyLinkedList;
import ShoppingList.Item;
import ShoppingList.Main;
import ShoppingList.MyWindow;
import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import java.awt.Desktop;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Locale;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Teemu Kaunisto  {@literal (teemu.kaunisto@cs.tamk.fi)}
 * @version 3.0
 * @since 1.8
 */
public class Functionalities {
    
    // if user has linked to dropbox this will be true
    static public boolean linkedToDB = false;
    
    // dropbox client
    static public DbxClient client;
    
    // configs from dropbox
    static public DbxRequestConfig config;
    
    // authentication from dropbox
    static public DbxWebAuthNoRedirect webAuth;
    
    /**
     * Adds new item to shopping list.
     * 
     * if item is already in the shopping list the amount will only increase
     * otherwise new item will be created
     * 
     * @param window    main window
     * @param list      shopping list
     */
    public static void addNewItem(MyWindow window, MyLinkedList<Item> list) {
        
        // get name of the product from main window
        String nameOfProduct = window.getProductName();
        int amountOfProduct = 0;
        
        try {
            
            // try to parse users amount input to integer
            amountOfProduct = Integer.parseInt(window.getProductAmount());
        } catch (NumberFormatException e) {
            
            // if failed show the error msg
            errorMessage("Amount must be a numeric value!");
            
            // and then return doing nothing
            return;
        }
        
        // if user tries to add less than 0 items or equal to 0
        // show error msg
        if (amountOfProduct < 0 || amountOfProduct == 0) {
            
            errorMessage("Amount must be greater than 0");
        
        // check if item already exists
        } else if (Main.itemExist(nameOfProduct, amountOfProduct)) {
            
        // check if name of the product is not empty
        } else if (!nameOfProduct.equalsIgnoreCase("")) {
            
            // add new item
            list.add(new Item(nameOfProduct, amountOfProduct));   
        } else {
            
            // name of the item was not given
            errorMessage("Give name of the product");         
        }
    }
    
    /**
     * Opens "load file" option window.
     * 
     * User can open either .ser files or .txt files that are recognized
     * as shopping lists made by this application.
     * 
     * @param list  shopping list
     * 
     */
    public static void loadShoppingList(MyLinkedList<Item> list) {
    
        // file chooser
        JFileChooser chooser = new JFileChooser();
        
        // own file extensions
        FileNameExtensionFilter txtFilter;
        FileNameExtensionFilter serFilter;
        
        // add extensions to chooser
        chooser.addChoosableFileFilter(txtFilter = 
                new FileNameExtensionFilter("Text file (.txt)", "txt"));
        chooser.addChoosableFileFilter(serFilter = 
                new FileNameExtensionFilter("Shopping List (.ser)", ("ser")));
        
        // set default file extension
        chooser.setFileFilter(serFilter);
        
        // remove all files option
        chooser.setAcceptAllFileFilterUsed(false);
                
        // set default directory
        chooser.setCurrentDirectory(new File("/home/me/Documents"));
                
        // get integer by user's choice and the parent component to null
        int retrival = chooser.showOpenDialog(null);
        
        // if ok choosed
        if (retrival == JFileChooser.APPROVE_OPTION) {
            
            // check the file type user gave
            if (chooser.getFileFilter().equals(txtFilter)) {
                
                FileSystem.loadTxtFile(chooser.getSelectedFile(), list);
            }
            
            if (chooser.getFileFilter().equals(serFilter)) {
             
                FileSystem.loadSerFile(chooser.getSelectedFile(), list);
            }
        }
    }
    
    /**
     * Opens "save file" option window.
     * 
     * User can save the shopping list either as .txt file or .ser file.
     * Method calls propes saving method depending on user's choice.
     * 
     * @param list  shopping list
     */
    public static void saveShoppingList(MyLinkedList<Item> list) {

        JFileChooser chooser = new JFileChooser();
        
        FileNameExtensionFilter txtFilter;
        FileNameExtensionFilter serFilter;
        
        chooser.addChoosableFileFilter(txtFilter = 
                new FileNameExtensionFilter("Text file (.txt)", "txt"));
        chooser.addChoosableFileFilter(serFilter = 
                new FileNameExtensionFilter("Shopping List (.ser)", "ser"));
        
        // set default filter
        chooser.setFileFilter(serFilter);
                
        chooser.setCurrentDirectory(new File("/home/me/Documents"));
                
        // returns int depending on choice from user
        int retrival = chooser.showSaveDialog(null);
        
        // ok pressed
        if (retrival == JFileChooser.APPROVE_OPTION) {
            
            if (chooser.getFileFilter().equals(txtFilter)) {
            
                FileSystem.saveAsTxtFile(chooser.getSelectedFile(), list);
            }
            
            if (chooser.getFileFilter().equals(serFilter)) {
                
                FileSystem.saveAsSerFile(chooser.getSelectedFile(), list);
            }
        }   
    }
    
    /**
     * Prints shopping list.
     * 
     * Used to print shopping list via connected printer.
     * 
     * @param jtable    table form of shopping list
     * 
     * @throws PrinterException if an 
     *         error in the print system causes the job to be aborted
     */
    public static void printShoppingList(JTable jtable) 
            throws PrinterException {
    
            boolean complete = jtable.print(JTable.PrintMode.NORMAL, 
                    new MessageFormat("SHOPPING LIST"), null);    
    }
    
    /**
     * Updates table.
     * 
     * Updates table by setting it to zero first and then making it again.
     * 
     * @param table shopping list table model
     * @param list  shopping list
     */
    public static void updateTable(MyTableModel table, 
            MyLinkedList<Item> list) {
        
        table.setRowCount(0);
        
        for (int i = 0; i < list.size(); i++) {
            
            Item temp = (Item)list.get(i);
            
            table.addRow(new Object[]{temp.getAmount(), temp.getName()});
        }
    }

    /**
     * Removes item from shopping list.
     * 
     * @param window    main window
     * @param list      shopping list
     */
    public static void removeItem(MyWindow window, MyLinkedList<Item> list) {
    
        String nameOfProduct = window.getProductName();
        String amountOfProduct = window.getProductAmount();
                
        // this check is needed because you can't parse empty String
        for (int i = 0; i < list.size(); i++) {

            Item temp = (Item) list.get(i);

            if (temp.getName().equalsIgnoreCase(nameOfProduct)) {

                // if amount was not given, remove whole product
                if (amountOfProduct.equals("")) {

                    list.remove(i);          
                 } else {

                    try {

                        // try to parse amount to be removed to integer and
                        // make it to be negative
                        int tempRemoveAmount = 
                                -Integer.parseInt(amountOfProduct);

                        // check if item already exists and if so remove
                        // the given amount
                        Main.itemExist(nameOfProduct, tempRemoveAmount);
                     } catch (NumberFormatException e) {

                        // show error message if bad amount format was given 
                        errorMessage("Give amount to remove/remove"
                                + " whole item by leaving field empty");
                     }
                 }
                
                return;
            }
        }

        // error if the was not such item
        errorMessage("Item was not found");
    }

    /**
     * Tries to link application with users DropBox account.
     * 
     * if linking won't succeed because of bad key the the error message
     * will be displayed
     * 
     * @param code code for linking
     */
    public static void dropBox(String code) {
    
        try {
            
            // this fails if the code is wrong
            DbxAuthFinish authFinish = webAuth.finish(code);
            
            // personal code for user, with this it is possible to
            // skip authentication
            String accessToken = authFinish.accessToken;
            
            // new client of current user
            client = new DbxClient(config, accessToken);
            
            // display to user that connection succeeded
            JOptionPane.showMessageDialog(null, "Hi, " + 
                    client.getAccountInfo().displayName, 
                    "Account", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            // inform to other methods that linking succeeded
            Functionalities.linkedToDB = true;
        } catch (DbxException ex) {  
        
            // if linking fails
            errorMessage("Bad key was given. Copy the line exactly");
        }
    }
    
    /**
     * Starts user authentication for application.
     * 
     * tries to open a link to dropbox authentication page where user needs to
     * log in with account, if URL cannot be opened then a message will display
     * the web page
     */
    public static void dropBoxAuth() {

        // personal key for developers app
        final String APP_KEY = "ltrou9ejsk6a4rd";
        // personal secret for developers app
        final String APP_SECRET = "c9w02x49n8p1hsf";
        
        // application info required forauthentication
        // the used app is recognised by this
        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        // create config for app
        config = new DbxRequestConfig("JavaTutorial/1.0",
            Locale.getDefault().toString());
        
        // register authentication code with your app
        webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        
        // store the authentication URL to String
        String authorizeUrl = webAuth.start();
        
        try {
            
            // try to open the authentication web page
            Desktop.getDesktop().browse((new URL(authorizeUrl)).toURI());
        } catch (URISyntaxException | IOException e) {
            
            // if the web paged cannot be opened then show the correct 
            // web page to user
            JOptionPane.showInputDialog(null, "Link could not be opened." +
                    " Here's the link in text format: " + authorizeUrl, 
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Shows error dialog to user depending on error.
     * 
     * @param message the error message to be shown 
     */
    public static void errorMessage(String message) {
        
        JOptionPane.showMessageDialog(null, 
                            message, "ERROR",
                    JOptionPane.ERROR_MESSAGE);
    }
}
