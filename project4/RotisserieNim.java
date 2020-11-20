/**
 * Models a Rotisserie Nim position.
 *
 * @author Kyle Burke <paithanq@gmail.com>
 */
 
import java.lang.*;
import java.io.*;
import java.util.*;

public class RotisserieNim extends CombinatorialGame {

    //instance variables
    //the piles in this.
    private PureQueue<Integer> piles; 

    /**
     * Class constructor.
     * 
     * @param piles     An array of the pile sizes for this game.  The zeroeth element is the first pile that will be played on.
     */
    public RotisserieNim(int[] piles) {
        //cheating here, using a built-in class for testing
        this.piles = new PureQueue<Integer>();  
        for (int i = 0; i < piles.length; i++) {
            int pileSize = piles[i];
            if (pileSize > 0) {
                this.piles.add(new Integer(pileSize));
            } else {
                System.err.println("Tried to add a new pile with " + pileSize + " sticks.");
            }
        }
    }
    
    /**
     * Class constructor.
     * 
     * @param piles     An array of the pile sizes for this game.  The zeroeth element is the first pile that will be played on.
     */
    public RotisserieNim(Integer[] piles) {
        this(RotisserieNim.unboxArray(piles));
    }
    
    /**
     * Class constructor.
     *
     * @param piles  A Queue of the piles sizes for this game.
     */
    public RotisserieNim(PureQueue<Integer> piles) {
        this.piles = this.copyQueue(piles);
    }
    
    /**
     * Gets a String representation.
     *
     * @return  A String representation of this.
     */
    public String toString() {
        String string = "A Rotisserie Nim position: Next Pile -->";
        List<Integer> pileSizes = getListFromQueue(this.getPiles());
        for (Integer pileSize : pileSizes) {
            string += " " + pileSize;
        }
        string += " <-- Last Pile";
        return string;
    }
    
    /**
     * Gets the piles.
     *
     * @return  A copy of the piles, as a Queue.
     */
    public PureQueue<Integer> getPiles() {
        return this.copyQueue(this.piles);
    }
    
    /**
     * Clones this.
     *
     * @return  A deep clone of this.
     */
    public RotisserieNim clone() {
        return new RotisserieNim(this.piles);
    }
    
    @Override
    public boolean equals(CombinatorialGame game) {
        try {
            RotisserieNim otherNim = (RotisserieNim) game;
            return this.piles.equals(otherNim.piles);
        } catch (ClassCastException cce) {
            return false;
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        RotisserieNim otherNim;
        try {
            otherNim = (RotisserieNim) obj;
            return this.piles.equals(otherNim.piles);
        } catch (ClassCastException cce) {
            return false;
        }
    }
    
    //@override
    public Collection<CombinatorialGame> getOptions(int player) {
        Collection<CombinatorialGame> options = new Vector<CombinatorialGame>();
        int firstPileSize;
        try {
            firstPileSize = this.piles.element().intValue();
        } catch (NoSuchElementException nsee) {
            //there are no piles; there is no first pile!
            return options;
        }
        //create the option that removes all 
        RotisserieNim takeAll = this.clone();
        takeAll.piles.remove();
        options.add(takeAll);
        for (int optionPileSize = 1; optionPileSize < firstPileSize; optionPileSize++) {
            //add an option with the new pile on the end
            RotisserieNim newOption = takeAll.clone();
            newOption.piles.add(new Integer(optionPileSize));
            options.add(newOption);
        }
        return options;
    }
    
    /**
     * Unit test for RotisserieNim.
     */
    public static void main(String[] args) {
        int[] piles = new int[] {3, 5, 7};
        RotisserieNim cycleNim = new RotisserieNim(piles);
        System.out.println("Position: " + cycleNim);
        System.out.println("Clone: " + cycleNim.clone());
        System.out.println("Options:");
        for (CombinatorialGame option: cycleNim.getOptions(CombinatorialGame.LEFT)) {
            System.out.println("    " + option);
        }
    }
    
    /* Private methods */
    
    //"unboxes" a list of integers.  More specifically, returns a primitive copy of a list of Integer objects.
    private static int[] unboxArray(Integer[] integers) {
        int[] integersUnboxed = new int[integers.length];
        for (int i = 0; i < integers.length; i++) {
            integersUnboxed[i] = integers[i].intValue();
        }
        return integersUnboxed;
    }
    
    //copies a Queue of Integers
    private <T> PureQueue<T> copyQueue(PureQueue<T> elements) {
        PureQueue<T> copyToReturn = new PureQueue<T>();
        PureQueue<T> copyToReuse = new PureQueue<T>();
        //remove all elements of integers and add to both copies
        while (true) {
            T nextElement;
            try {
                nextElement = elements.remove();
                copyToReturn.add(nextElement);
                copyToReuse.add(nextElement);
            } catch (NoSuchElementException nsee) {
                break; //escape the while loop; we've removed all elements
            }
        }
        //elements is now empty!  Leave copyToReturn as is, but let's put the things from copyToReuse back into elements.
        while (true) {
            T nextElement;
            try {
                nextElement = copyToReuse.remove();
                elements.add(nextElement);
            } catch (NoSuchElementException nsee) {
                break; //escape the loop; we've remove everything from copyToReuse
            }
        }
        return copyToReturn;
    }
    
    //returns an ArrayList version of a Queue
    private <T> ArrayList<T> getListFromQueue(PureQueue<T> queue) {
        PureQueue<T> queueCopy = this.copyQueue(queue);
        ArrayList<T> list = new ArrayList<T>();
        while (true) {
            T nextElement;
            try {
                nextElement = queueCopy.remove();
                list.add(nextElement);
            } catch (NoSuchElementException nsee) {
                break; //escape the loop; we've remove everything from copyToReuse
            }
        }
        return list;
    }
    
    /**
     * A generator of RotisserieNim positions.
     */
    public static class RotisserieFactory implements PositionFactory<RotisserieNim> {
    
        //maximum number of piles it will generate
        private int numPiles;
        
        //maximum piles size
        private int maxPileSize;
        
        /**
         * Class constructor.
         *
         * @param numPiles  The maximum number of piles.
         * @param maxPileSize  The maximum size of a pile.
         */
        public RotisserieFactory(int numPiles, int maxPileSize) {
            this.numPiles = numPiles;
            this.maxPileSize = maxPileSize;
        }
        
        //@override
        public RotisserieNim getPosition() {
            PureQueue<Integer> piles = new PureQueue<Integer>();
            Random randomGenerator = new Random();
            for (int i = 0; i < numPiles; i++) {
                int pileSize = randomGenerator.nextInt(this.maxPileSize + 1);
                if (pileSize > 0) {
                    piles.add(new Integer(pileSize));
                }
            }
            return new RotisserieNim(piles);
        }
        
    } //end of RotisserieFactory

}  //end of RotisserieNim