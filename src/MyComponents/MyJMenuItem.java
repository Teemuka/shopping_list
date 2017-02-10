/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MyComponents;

import SystemHandler.FileSystem;
import SystemHandler.Functionalities;
import ShoppingList.MyWindow;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * @author Teemu Kaunisto  {@literal (teemu.kaunisto@cs.tamk.fi)}
 * @version 3.0
 * @since 1.8
 */
public class MyJMenuItem extends JMenuItem {

    /**
     * Works as class constructor.
     * 
     * custom JMenuItem allow to add more functionalities
     * 
     * @param name      name of menu item
     * @param keyevent  shortcut key for menu item
     * @param window    main window
     * @param tag       personal tag for menu item
     */
    public MyJMenuItem(String name, int keyevent, MyWindow window, String tag) {
    
        // calling parent class for adding name and shortcut key
        super(name, keyevent);
        
        // adding actionlistener allows menu items to run functions
        addActionListener((ActionEvent e) -> {
            
            // save menu item
            if (tag.equalsIgnoreCase("save")) {

                Functionalities.saveShoppingList(window.getList());
            
            // load menu item
            } else if (tag.equalsIgnoreCase("load")) {
                
                // loads shopping list from file
                Functionalities.loadShoppingList(window.getList());
                
                // updates shopping list (jtable) 
                Functionalities.updateTable(window.getMyTable(), 
                        window.getList());
            
            // print menu item    
            } else if (tag.equalsIgnoreCase("print")) {
                
                try {
                    
                    // prints shopping list using connecter printer
                    Functionalities.printShoppingList(window.getJTable());
                
                // if an error in the print system causes the job to be aborted
                } catch (PrinterException ex) {
                
                }
                
            // link to dropbox menu item
            } else if (tag.equalsIgnoreCase("dropbox")) {
   
                // if user hasnt yet linked the app to db
                if (Functionalities.linkedToDB == false) {
                
                    // generates authentication key for user
                    Functionalities.dropBoxAuth();
                    
                    // asks user for the key: if no key given/pressed cancel
                    // then do nothing
                    String key = JOptionPane.showInputDialog("Give auth key");
                    
                    if (key != null) {
                        
                        // setups the connection
                        Functionalities.dropBox(key);
                    } 
                } else {
                   
                    // error message if user's account is already linked
                    Functionalities.
                            errorMessage("You have already linked to DropBox");
                }
            
            // save to dropbox menu item
            } else if (tag.equalsIgnoreCase("savedb")) {
                
                // check if user has linked the account
                if (Functionalities.linkedToDB) {
                
                    // get filename for file from user
                    String fileName = JOptionPane.
                            showInputDialog("Give filename");
              
                    // if no file name given/cancelled do nothing
                    if (fileName != null) {
                
                        // add the extension for filename
                        fileName = fileName + (".ser");
                
                        // save file to dropbox
                        FileSystem.saveToDropBox(fileName, window.getList());
                    }
                } else {
                    
                    // error message if user hasnt linked to db
                    Functionalities.
                            errorMessage("You haven't linked to DropBox");
                }
            
            // load from dropbox menu item
            } else if (tag.equalsIgnoreCase("loaddb")) {
                
                // checks if linked
                if (Functionalities.linkedToDB) {
                    
                    // load from db
                    FileSystem.loadFromDropBox(window.getList());
                    
                    // update the shopping list (jtable)
                    Functionalities.updateTable(
                            window.getMyTable(), 
                            window.getList());
                } else {
                    
                    // if user hasn't linked to db
                    Functionalities.
                            errorMessage("You haven't linked to DropBox");
                }
            }
        });
    }
}
