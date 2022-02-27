
package linkee;

import java.util.*;

public class LinkedFileAllocation  {
     
    
   public static void main(String[] args) {

       Scanner input = new Scanner(System.in);
 
       System.out.println("This is Linked File Allocation program \nwith memory size 10 ");
       System.out.println("***************************************");
         
        LinkedList list = new LinkedList(); //create list 
        int[] unused = new int[10]; //array stores avilable blocks 
        int[] file = new int[10]; //array stores start point for each file 
        int count = 0; //counter used to determine number of allocated blocks

       //create an empty linked list with 10 blocks
       for (int i = 0; i < 10; i++) {
            list.add(i, -1);
            unused[i] = i;
            file[i] = -1;
        }
       
        // user enter already allocated blocks 
        System.out.print("Enter how many blocks already allocated: ");
        int a = input.nextInt(); //  store the allocated blocks  
        
        while (a>10){//if user enter number that is larger then memory size 
         System.out.print("file length is large ,please select smaller length :  ");
         a = input.nextInt();
        }
        
  
        System.out.printf("Enter which %d blocks already allocated: ",a);
        
        for (int i = 0; i < a; i++) {  //store which blocks already allocated .
            int b = input.nextInt();
            Block n = list.searchBlock(b);
            n.setPointer(b);
            unused[b] = -1;
            count++;
        }
        
        // user enter file to allocated 
        System.out.print("\nIf you want to allocated file,\nEnter starting index and length of block(or -1 to end): ");
        int start = input.nextInt();
        int length = 0;
        int ind = 0;
        
        do {
            length = input.nextInt();
            
            if (search(unused, start) < 0) { //check if the start block is already used
                System.out.println("Block already allocated, please select another starting block(or -1 to end): ");
                start = input.nextInt();
                continue;
            }
            
            if (length > Math.abs(count - 10)) { //check if the length input is large, and cannot be stored in the list
                System.out.print("Blockes already allocated = " + count + " cannot add this file, file length is large.\n"
                        + "Enter starting block and smaller length(or -1 to end): ");
                start = input.nextInt();
                continue;
            }
            //store the file using the starting block, and then use random available blocks .
            int random = 0;
            Block n;
            n = list.searchBlock(start);
            file[ind] = start;
            ind++;
            unused[start] = -1;
            count++;
            length--;
            while (length > 0) { //store the blocks using random numbers
                random = (int) (Math.random() * 10);
                if (search(unused, random) < 0) {
                    continue;
                }
                unused[random] = -1;
                count++;
                n.setPointer(random);
                n = list.searchBlock(random);
                length--;
            }
            
            System.out.printf("File %d : start index = %d , end index = %d \n\n" , ind , start,random);
           
            
            //check if all blocks have been allocated
            if(count>=10){
                System.out.println("All blocks have been allocated.");
                break;
            }
            
            System.out.print("If you want to allocated another file ,\nEnter starting index and length of block(or -1 to end):  ");
            start = input.nextInt();
        } while (start != -1);
        
        //print the allocated blocks for all files
        for (int i = 0; file[i] != -1; i++) {
            System.out.print("File " + (i + 1) + " : ");
            list.searchFile(file[i]);
        }
    }

    public static int search(int[] array, int ind) {// search in array if blocks available or used
        for (int i = 0; i < array.length; i++) {
            if (array[i] == ind) {
                return ind;
            }
        }
        return -1;
    }
}

class Block { // node 

    private int index;
    private int pointer;
    private Block next;

    public Block(int index, int pointer, Block next) {
        this.index = index;
        this.pointer = pointer;
        this.next = next;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public int getPointer() {
        return this.pointer;
    }

    public void setNext(Block next) {
        this.next = next;
    }

    public Block getNext() {
        return this.next;
    }
}

class LinkedList {//list

    private Block head;

    public LinkedList() {
        this.head = null;
    }

    public LinkedList(Block head) {
        this.head = head;
    }

    public void setHead(Block head) {
        this.head = head;
    }

    public Block getHead() {
        return this.head;
    }

    public void add(int index, int pointer) {
        Block n = new Block(index, pointer, null);
        if (getHead() == null) {
            setHead(n);
        } else {
            Block x = getHead();
            while (x.getNext() != null) {
                x = x.getNext();
            }
            x.setNext(n);
        }
    }

    public Block searchBlock(int ind) {//find Block
        Block n = getHead();
        while (n != null) {
            if (n.getIndex() == ind) {
                return n;
            }
            n = n.getNext();
        }
        return null;
    }

    public void searchFile(int start) { //find Blocks that belong to each file 
        System.out.print(start);
        Block n = searchBlock(start);
        while (n.getPointer() != -1) {
            System.out.print(" -- " + n.getPointer());
            n = searchBlock(n.getPointer());
        }
        System.out.println();
    }
}
