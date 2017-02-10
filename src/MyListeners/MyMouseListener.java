/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MyListeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTextField;

/**
 * @author Teemu Kaunisto  {@literal (teemu.kaunisto@cs.tamk.fi)}
 * @version 3.0
 * @since 1.8
 */
public class MyMouseListener implements MouseListener{

    /**
     * Checks if mouse is clicked.
     * 
     * @param e mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    
        // checks if the object which was clicked is jtextfield
        if (e.getSource() instanceof JTextField) {
            
            JTextField temp;
            
            temp = (JTextField) e.getSource();
            
            temp.setText("");
        }
    }

    /**
     * Checks if mouse was pressed.
     * 
     * @param e mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
    
    }

    /**
     * Checks if mouse was released.
     * 
     * @param e mouse event
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    
    }

    /**
     * Checks if mouse enters certain area..
     * 
     * @param e mouse event
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    /**
     * Checks if mouse exits certain area.
     * 
     * @param e mouse event
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
