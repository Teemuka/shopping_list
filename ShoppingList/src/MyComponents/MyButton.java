/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MyComponents;

import SystemHandler.Functionalities;
import ShoppingList.MyWindow;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

/**
 * @author Teemu Kaunisto  {@literal (teemu.kaunisto@cs.tamk.fi)}
 * @version 3.0
 * @since 1.8
 */
public class MyButton extends JButton {
    
    /**
     * Works as class constructor.
     * 
     * custom button for extra functionalities
     * 
     * @param name  name of the button
     * @param gui   main window where button is  
     * @param tag   personal tag for button
     */
    public MyButton(String name, MyWindow gui, String tag) {
    
        super(name);
        
        // added actionlistener for executing functionalities  
        addActionListener((ActionEvent e) -> {
        
            // buttons are separeted by tags
            // add button
            if (tag.equalsIgnoreCase("add")) {
                
                Functionalities.addNewItem(gui, gui.getList());
                Functionalities.updateTable(gui.getMyTable(), gui.getList());
            
            // remove button
            } else if (tag.equalsIgnoreCase("remove")) {
                
                Functionalities.removeItem(gui, gui.getList());                
                Functionalities.updateTable(gui.getMyTable(), gui.getList());
            } 
        });
    }
}
