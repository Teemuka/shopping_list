/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ShoppingList;

import MyLinkedList.*;
import java.util.Scanner;

/**
 * @author Teemu Kaunisto  {@literal (teemu.kaunisto@cs.tamk.fi)}
 * @version 3.0
 * @since 1.8
 */
public class Main {

    static MyLinkedList<Item> shoppingList = new MyLinkedList<>();
    static Scanner sc = new Scanner(System.in);
    
    /**
     * Launches all vital methods.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        MyWindow myWindow = new MyWindow();
        
        System.out.println("Give the amount of products and the exact product "
                + "to be added to shopping list: (For example: 4 milk)\n"
                + "if you wanna quit type: quit or exit");
        
        while (true) {
            
            separateInput();
            printShoppingList();
        } 
    }

    /**
     * Separates user's text line to certain item and to amount of that item.
     * 
     * If user gives bad input, then method will call method called 
     * errorMessage that will print guiding error message to screen.
     * 
     * @see #errorMessage(java.lang.String, java.lang.String) 
     * @see MyLinkedList
     */
    private static void separateInput() {
    
        String line = sc.nextLine();
        checkExit(line);

        String[] allItems = line.split(";");
        String[] itemAndAmount;
        int amount = 0;
         
        for (int i = 0; i < allItems.length; i++) {
                        
            String item = allItems[i].trim();  
                        
            itemAndAmount = item.split(" ");
            
            if (itemAndAmount.length < 2) {
                
                errorMessage("falseinput", itemAndAmount[0]);
                break;
            }
            
            try {
                                                
                amount = Integer.parseInt(itemAndAmount[0]);
                item = itemAndAmount[1];
                                
                if (amount == 0) {
                    
                    errorMessage("zeroAmount", "");
                }
            } catch (NumberFormatException e) {
                                
                amount = Integer.parseInt(itemAndAmount[1]);
                item = itemAndAmount[0];
                
                if (amount == 0) {
                    
                    errorMessage("zeroAmount", "");
                }
            }
            
            if (itemExist(item, amount)) {
            
            } else {
             
                shoppingList.add(new Item(item, amount));
            }
        }
    }

    /**
     * Prints shopping list.
     */
    private static void printShoppingList() {

        Item temp;
        System.out.println("Your shopping list now: ");

        for (int i = 0; i < shoppingList.size(); i++) {
            
            temp = (Item) shoppingList.get(i);
            System.out.println(temp.getAmount() + " " + temp.getName());
        }  
    }
    
    /**
     * Checks user's input and exits the application if necessary.
     * 
     * @param line user's input
     */
    private static void checkExit(String line) {

        if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
            
            System.exit(0);
        }
    }

    /**
     * Checks if item is already in the shopping list.
     * 
     * if the shopping list already contains the given item, then only the
     * amount of it is increased. 
     * 
     * if no amount is given then remove whole item from shopping list.
     * 
     * @param item      name of the item to be checked
     * @param amount    amount of the given item
     * @return          true or false, depending is item is already presented
     */
    public static boolean itemExist(String item, int amount) {

        Item temp;
        
        for (int i = 0; i < shoppingList.size(); i++) {
            
            temp = (Item)shoppingList.get(i);
            
            if (temp.getName().equalsIgnoreCase(item)) {
                
                if (temp.getAmount() + amount > 0) {
                
                    temp.setAmount(temp.getAmount() + amount);      
                } else {
                    
                    shoppingList.remove(i);
                }
                
                return true;
            }
        } 
        
        return false;
    }

    /**
     * Prints error message to guide the user.
     * 
     * Error message depends on the error that user has made.
     * 
     * @param error     type of the error
     * @param string    detail that is needed in error message
     */
    private static void errorMessage(String error, String string) {
        
        if (error.equalsIgnoreCase("falseinput"))
        
            if (Character.isDigit(string.charAt(0))) {
                System.out.println("You gave amount for your item"
                        + " but not the item itself!");
            } else {
                System.out.println("You gave the item but not the amount."
                        + " Item was not added to your shopping list");
            } else if (error.equalsIgnoreCase("zeroAmount")) {
            System.out.println("Amount must be more than 0");
        }
    }
}
