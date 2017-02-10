/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MyLinkedList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author Teemu Kaunisto  {@literal (teemu.kaunisto@cs.tamk.fi)}
 * @version 3.0
 * @since 1.8
 *
 * @param <T> Generic type of the class  
 */
public class MyLinkedList<T> implements Serializable {

    /**
     * first element in the list.
     */
    private Element<T> first;
    
    /**
     * element where the "pointer" is pointing.
     */
    private Element<T> current;
    
    /**
     * size of the list.
     */
    private int size;

    /**
     * Class MyLinkedList constructor.
     * 
     * Sets the first element to null and size to zero
     * 
     */
    public MyLinkedList() {
        
        this.first = null;
        this.size = 0;
    }
    
    /**
     * Adds new element to list.
     * 
     * new element will include generic data
     * 
     * @param data generic object
     */
    public void add(T data) {
            
        if (this.first == null) {

            this.first = new Element<>(data);
        } else {

            this.current = this.first;

            while (this.current.getNext() != null) {

                this.current = this.current.getNext();
            }
            
            this.current.setNext(new Element<>(data));
        }
        
        this.size++;  
    }

    /**
     * Deletes all elements from the list.
     */
    public void clear() {
    
        this.first = null;
        this.size = 0;
    }
    
    /**
     * Copies contents from another list to this one.
     * 
     * copies the contents so the memory address stays the same
     * 
     * @param list  list where to copy from
     */
    public void copyList(MyLinkedList<T> list) {
    
        // first have to empty original
        this.clear();
        
        for (int i = 0; i < list.size(); i++) {
            
            T temp = (T)list.get(i);
            this.add(temp);
        }
    }

    /**
     * Returns data from asked element.
     * 
     * @param   index     index of the element
     * @return  Object   data of the element
     */
    public T get(int index) {
        
        if (index > this.size) {
            
            return null;
        } 
        
        this.current = this.first;
        
        for (int i = 0; i < index; i++) {
            
            if (this.current.getNext() == null) {
                
                return null;
            }
            
            this.current = this.current.getNext();
        }
        
        return this.current.getData();
    }

    /**
     * Checks if the list is empty.
     * 
     * @return boolean true if list is empty
     */
    public boolean isEmpty() {
        
        return this.first == null;
    }

    /**
     * Deletes the asked element.
     * 
     * returns the data of the deleted element
     * 
     * @param   index   index of the element that will be deleted
     * @return  T       data of the deleted element
     */
    public T remove(int index) {
        
        if (index >= this.size) {
            
            return null;
        }
        
        this.size--;
        
        if (index == 0) {
            
            T temp = this.first.getData();
            
            this.first = this.first.getNext();
            
            return temp;
        }
        
        this.current = this.first;
        
        for (int i = 0; i < index-1; i++) {
            
            this.current = this.current.getNext();
        }
        
        T temp = this.current.getNext().getData();
        
        if (this.current.getNext() != null) {
        
            this.current.setNext(this.current.getNext().getNext());   
        } else {
            
            this.current.setNext(null);
        }
        
        return temp;
    }

    /**
     * Removes element that includes given data.
     * 
     * @param o             data of the element
     * @return  boolean     true if element was removed
     */
    public boolean remove(T o) {        
        
        this.current = this.first;
        
        while (current.getNext() != null) {
            
            if (this.current.getNext().getData().equals(o)) {
                
                this.current.setNext(this.current.getNext().getNext());
                
                return true;
            }
            
            this.current = this.current.getNext();
        }
        
        return false;
    }

    /**
     * Returns size of the list.
     * 
     * @return int size of the element
     */
    public int size() {
    
        return this.size;
    }
    
    /**
     * Writes the data of elements to file.
     *
     * @param   path            path to file
     * @throws  IOException     if path not found
     * @deprecated              will be corrected later
     */
    public void saveToFile(String path) throws IOException{
        
        this.current = this.first;
        
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(path))) {
            
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
        }
    }
    
    /**
     * Reads from file.
     * 
     * @param   path                    path of the file
     * @throws  IOException             if path not found
     * @throws       ClassNotFoundException  if class is not found in the file
     * @deprecated   will be corrected later
     */
    public void readFromFile(String path) throws IOException, 
            ClassNotFoundException{
        
        this.current = this.first;
        
        try(ObjectInputStream inputStream = 
                new ObjectInputStream(new FileInputStream(path))) {    
        
        @SuppressWarnings("unchecked")
        MyLinkedList<T> temp = (MyLinkedList<T>)inputStream.readObject();
        
        temp.current = temp.first;
                        
            while (temp.current.getNext() != null) {
                
                this.current = temp.current;
                
                temp.current = temp.current.getNext();
                this.current = this.current.getNext();
            }
        }
    }
}
