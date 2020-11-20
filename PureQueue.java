/**
 * PureQueue creates a queue using the ArrayList data structure  
 *
 * @author Zach 'TG' Thoroughgood
 */

//package packageName

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class PureQueue <E extends Object> {

    /* constants */

    /* fields */
	private ArrayList<E> mylist;
    
    /* constructors */
    
    /**
     * Creates a new instance of EmptyClass.
     */
    public PureQueue() {
		mylist = new ArrayList<E>(); 
    }
    
    /* public methods */
    
    /**
     * Returns a String version of this.
     *
     * @return  A String description of this.
     */
    public String toString() {
		String a = "Top";
		String b = "Bottom";
        return b + " --->" + mylist + "<---" + a + "\n" + a + " --->" + mylist + "<---" + b ;   
    }
    
	
	/*
	From PureStack.java for reference
	 
	public void push(E item){
		mylist.add(item);
	}
    
	*/
	
	public boolean add(E item){
		mylist.add(item);
		return true;
	}
	
	public E remove(){
		int item = mylist.size();
		if (item <= 0){
			throw new NoSuchElementException("There is no such item");
		}
		return mylist.remove(0);
	}
	
	public E element(){
		int item = mylist.size();
		if (item <= 0){
			throw new NoSuchElementException("There is no such item");
		}else{
		return mylist.get(0);

		}
	}
	
	public boolean isEmpty(){
		return mylist.isEmpty();
	}
	
	public boolean equals(PureQueue<E> other) {
        return mylist.equals(other.mylist);
    }
    /*
	public boolean equals(PureQueue<E> item){
		if(mylist.size() != item.size()){
			return false;
		}else if(item == null && mylist == null){
			return true;
		}
		return mylist.equals(item);
	}
	*/
	
	public boolean equals(Object obj){
		PureQueue<E> other = (PureQueue<E>) obj;
		try{ 
			return mylist.equals(other.mylist);
		} catch (ClassCastException e){
			return false;
		}
	}
	
	
	
    /* hidden methods (private/protected) (JavaDoc not necessary) */
    
    /* main method for testing */
    
    /** 
     * Unit test for EmptyClass
     
     * @param args  Arguments used to test this class.
     */
    public static void main(String[] args) {
    
    }

} //end of EmptyClass