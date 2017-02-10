/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MyLinkedList;

import java.io.Serializable;

/**
 * @author Teemu Kaunisto  {@literal (teemu.kaunisto@cs.tamk.fi)}
 * @version 3.0
 * @since 1.8
 */
public class Element<T> implements Serializable{
    
    /**
     * next element.
     */
    Element<T> next;
    
    /**
     * data of the element.
     */
    private final T data;

    /**
     * Works as class constructor.
     * 
     * initializes the object that this element includes. Also sets the next
     * element to null
     * 
     * @param o data of the element
     */
    public Element(T o) {
    
       data = o;
       next = null;
    }
    
    /**
     * Gets next element.
     * 
     * @return  next element
     */
    public Element<T> getNext() {
        
        return next;
    }
    
    /**
     * Sets next element.
     * 
     * @param next next element
     */
    public void setNext(Element<T> next) {
        
        this.next = next;
    }
    
    /**
     * Gets data.
     * 
     * @return  data that current element includes
     */
    public T getData() {
        
        return data;
    }
}
