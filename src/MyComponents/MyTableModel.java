/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MyComponents;

import javax.swing.table.DefaultTableModel;

/**
 * @author Teemu Kaunisto  {@literal (teemu.kaunisto@cs.tamk.fi)}
 * @version 3.0
 * @since 1.8
 */
public class MyTableModel extends DefaultTableModel {

    /**
     * Constructs new table model.
     * 
     * Includes spesific column names for shopping list.
     */
    public MyTableModel() {
        
        addColumn("AMOUNT");
        addColumn("NAME OF THE PRODUCT");
    }
}
