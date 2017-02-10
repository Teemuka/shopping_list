/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SystemHandler;

import MyLinkedList.MyLinkedList;
import ShoppingList.Item;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWriteMode;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;

/**
 * @author Teemu Kaunisto  {@literal (teemu.kaunisto@cs.tamk.fi)}
 * @version 3.0
 * @since 1.8
 */
public class FileSystem {
    
    /**
     * Loads .ser file.
     * 
     * @param path file path by user
     * @param list shopping list
     */
    public static void loadSerFile(File path, MyLinkedList<Item> list) {
    
        ObjectInputStream input = null;
        
        // first empty the old shopping list
        list.clear();
        
        // start input stream
        try (FileInputStream in = new FileInputStream(path)) {
            
            // reads serialized stream header from the stream and verifies that
            input = new ObjectInputStream(in);
            
            try {
            
                // try to read object from serialized stream
                // the object is the saved shopping list
                // suppress unchecked casting warnings
                @SuppressWarnings("unchecked")
                MyLinkedList<Item> temp = ((MyLinkedList<Item>)
                        input.readObject());
                
                // copies loaded list to original one
                list.copyList(temp);
            } catch (ClassNotFoundException e) {
                
            }      
        } catch (IOException e) {
                        
        } finally {
            
            if (input != null) {
                
                try {
                    // close the stream so it will empty the buffer
                    input.close();
                } catch (IOException ex) {
                
                }
            }
        }
    }
    
    /**
     * Saves file as .ser file.
     * 
     * @param path file path by user
     * @param list shopping list
     */
    public static void saveAsSerFile(File path, MyLinkedList<Item> list) {
    
        ObjectOutputStream objOut = null;
        
        try (FileOutputStream out = new FileOutputStream(path + ".ser")) {
            
            objOut = new ObjectOutputStream(out);
            objOut.writeObject(list);
        } catch (IOException e) {     
            System.out.println(e.getStackTrace());
        } finally {
            
            if (objOut != null) {
                
                try {
                    objOut.close();
                } catch (IOException ex) {
                
                }
            }
        }
    }
    
    /**
     * Saves shopping list as .txt file.
     * 
     * @param path file path by user
     * @param list shopping list
     */
    public static void saveAsTxtFile(File path, MyLinkedList<Item> list) {
    
        Item temp;
      
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(path+".txt"))) {

            for (int i = 0; i < list.size(); i++) {

                temp = (Item)list.get(i);

                writer.write("Amount: " + temp.getAmount() + "   Product: "
                        + temp.getName());
                writer.newLine();
            }
        } catch (IOException io) {

        } 
    }
    
    /**
     * Loads .txt file.
     * 
     * loads choosed .txt file an gets shopping list from it.
     * 
     * @param path  filepath from user
     * @param list  original shopping list
     */
    public static void loadTxtFile(File path, MyLinkedList<Item> list) {
     
        String templine;
        String[] tempwords;
        
        // empty the original shopping list
        list.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {

                while (reader.read() != -1) {
                    
                    templine = reader.readLine();
                    tempwords = templine.split(" ");
                    
                    list.add(new Item(tempwords[5], 
                            Integer.parseInt(tempwords[1])));
                }            
        } catch (IOException io) {

        }
    }

    /**
     * Saves the shopping list to DropBox.
     * 
     * saves current shopping list to DropBox with name that user gives
     * 
     * first link between app and DropBox is needed
     * 
     * @param filename  user given filename
     * @param list      current shopping list
     */
    public static void saveToDropBox(String filename, MyLinkedList<Item> list) {
        
        ObjectOutputStream objOut = null;
        File file = new File("temp.tm");
        
        try (FileOutputStream stream = new FileOutputStream(file)) {
            
            objOut = new ObjectOutputStream(stream);
            objOut.writeObject(list);  
        } catch (IOException e) {
            
        } finally {
            
            if (objOut != null) {
                
                try {
                    
                    objOut.close();
                } catch (IOException ex) {
                
                }
            }
        }
        
        try (FileInputStream stream = new FileInputStream (file)) {
        
            try {
                
                // upload file to DropBox named by user
                Functionalities.client.uploadFile(
                        "/" + filename,
                        DbxWriteMode.add(), 
                        -1 , 
                        stream);  
            } catch (DbxException e) {
                
            }
        } catch (IOException e) {
            
        } 
    }
    
    /**
     * Loads shopping list from DropBox.
     * 
     * loads given shopping list from DropBox
     * 
     * requires link between app and DropBox
     * 
     * @param list  current shopping list
     */
    public static void loadFromDropBox(MyLinkedList<Item> list) {
        
        DbxEntry.WithChildren listing = null;
        String[] temp = null;
        String fileToLoad = null;
        int index = 0;
        
        try {
            
            // got the names of files in dropbox and add them to
            // String array
            listing = Functionalities.client.getMetadataWithChildren("/");
            temp = new String[listing.children.size()];
            
            for (DbxEntry child : listing.children) {
    
                temp[index] = child.name;
                index++;
            }        
        } catch (DbxException ex) {
        
        }
        
        // check if there even was any files in dropbox
        if (listing != null) {
            
            // show file names in dropdown where user can choose file
            fileToLoad = (String) JOptionPane.showInputDialog(null, 
                    "Choose file", "Load from DrobBox", 
                    JOptionPane.QUESTION_MESSAGE, null, temp, temp[0]);
        }
        
        // check if user picked any file
        if (fileToLoad != null) {
            
            try (FileOutputStream outputStream = 
                    new FileOutputStream("temp.tm")) {
                 
                // load the file and from dropbox (dropbpx sends it with
                // output stream)
                Functionalities.client.getFile("/"+fileToLoad, null,
                outputStream);
            } catch(IOException | DbxException e) {
                
            }
            
            ObjectInputStream objectInputStream = null;
            
            // load the file from drive
            try (FileInputStream inputStream = new FileInputStream("temp.tm")) {
                
                objectInputStream = new ObjectInputStream(inputStream);
                
               @SuppressWarnings("unchecked")
               MyLinkedList<Item> tempList = (MyLinkedList<Item>)
                       objectInputStream.readObject();
                
                // copy items from loaded list to original one
                list.copyList(tempList);        
            } catch (IOException | ClassNotFoundException e) {
                
            } finally {
                
                if (objectInputStream != null) {
                    
                    try {
                        
                        // always remember to close the stream
                        // so there wont be any bytes in buffer
                        objectInputStream.close();      
                    } catch (IOException ex) {
                    
                    }
                }
            }
        }
    }
}
