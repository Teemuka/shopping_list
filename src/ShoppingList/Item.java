/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ShoppingList;

import java.io.Serializable;

/**
 * @author Teemu Kaunisto  {@literal (teemu.kaunisto@cs.tamk.fi)}
 * @version 3.0
 * @since 1.8
 */
public class Item implements Serializable {

    /**
     * name of the item.
     */
    private String name;
    
    /**
     * amount of the item.
     */
    private int amount;

    /**
     * Works as class constructor.
     *
     * Initializes the name and amount of the item. Name and item are from
     * user's input
     *
     * @param name name of the item
     * @param amount amount of the item
     */
    public Item(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    /**
     * Gets name of item.
     *
     * @return name of the item
     */
    public String getName() {

        return this.name;
    }

    /**
     * Gets amount of item.
     *
     * @return amount of the item
     */
    public int getAmount() {

        return this.amount;
    }

    /**
     * Sets new name.
     *
     * @param name name of the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets amount of item.
     *
     * @param amount amount of the item
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
