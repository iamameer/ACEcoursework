/*
 *  Java Program to Implement Red Black Tree
 */
import jdk.nashorn.internal.runtime.JSONFunctions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* Class Node */
//added posi+post Element marking the positioning
class RedBlackNode{
    RedBlackNode left, right;
    int element;
    int color;
    int posi;
    /* Constructor */
    //**default posi 1 **//
    public RedBlackNode(int theElement){
        this( theElement, null, null,1 );
    }
    /* Constructor */
    public RedBlackNode(int theElement, RedBlackNode lt, RedBlackNode rt, int post) {
        left = lt;
        right = rt;
        element = theElement;
        color = 1;
        posi = post;
    }
}

/* Class RBTree */
class RBTree {
    private RedBlackNode current;
    private RedBlackNode parent;
    private RedBlackNode grand;
    private RedBlackNode great;
    private RedBlackNode header;
    private static RedBlackNode nullNode;

    /* static initializer for nullNode */
    static {
        nullNode = new RedBlackNode(0);
        nullNode.left = nullNode;
        nullNode.right = nullNode;
    }

    /* Black - 1  RED - 0 */
    static final int BLACK = 1;
    static final int RED   = 0;

    /* Constructor */
    public RBTree(int negInf) {
        header = new RedBlackNode(negInf);
        header.left = nullNode;
        header.right = nullNode;
    }

    /* Function to check if tree is empty */
    public boolean isEmpty() {
        return header.right == nullNode;
    }

    /* Make the tree logically empty */
    public void makeEmpty() {
        header.right = nullNode;
    }

    /* Function to insert item */
    public void insert(int item) {
        current = parent = grand = header;
        nullNode.element = item;
        while (current.element != item){
            great = grand;
            grand = parent;
            parent = current;
            current = item < current.element ? current.left : current.right;
            // Check if two red children and fix if so
            if (current.left.color == RED && current.right.color == RED)
                handleReorient( item );
        }
        // Insertion fails if already present
        if (current != nullNode)
            return;
        current = new RedBlackNode(item, nullNode, nullNode,0);
        // Attach to parent
        if (item < parent.element){
            parent.left = current;
        }else{
            parent.right = current;
        }
        handleReorient( item );
    }

    private void handleReorient(int item){
        // Do the color flip
        current.color = RED;
        current.left.color = BLACK;
        current.right.color = BLACK;
        if (parent.color == RED)
        {
            // Have to rotate
            grand.color = RED;
            if (item < grand.element != item < parent.element)
                parent = rotate( item, grand );  // Start dbl rotate
            current = rotate(item, great );
            current.color = BLACK;
        }
        // Make root black
        header.right.color = BLACK;
    }

    private RedBlackNode rotate(int item, RedBlackNode parent) {
        if(item < parent.element)
            return parent.left = item < parent.left.element ? rotateWithLeftChild(parent.left) : rotateWithRightChild(parent.left) ;
        else
            return parent.right = item < parent.right.element ? rotateWithLeftChild(parent.right) : rotateWithRightChild(parent.right);
    }

    /* Rotate binary tree node with left child */
    private RedBlackNode rotateWithLeftChild(RedBlackNode k2) {
        RedBlackNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        return k1;
    }

    /* Rotate binary tree node with right child */
    private RedBlackNode rotateWithRightChild(RedBlackNode k1) {
        RedBlackNode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        return k2;
    }

    /* Functions to count number of nodes */
    public int countNodes()
    {
        return countNodes(header.right);
    }
    private int countNodes(RedBlackNode r) {
        if (r == nullNode)
            return 0;
        else
        {
            int l = 1;
            l += countNodes(r.left);
            l += countNodes(r.right);
            return l;
        }
    }

    /* Functions to search for an element */
    public boolean search(int val)
    {
        return search(header.right, val);
    }
    private boolean search(RedBlackNode r, int val) {
        boolean found = false;
        while ((r != nullNode) && !found)
        {
            int rval = r.element;
            if (val < rval)
                r = r.left;
            else if (val > rval)
                r = r.right;
            else
            {
                found = true;
                break;
            }
            found = search(r, val);
        }
        return found;
    }

    //**function to return node**//
    private RedBlackNode searchN(int val){return searchN(header.right,val);}
    private RedBlackNode searchN(RedBlackNode r,int val){
        boolean found = false;
        while ((r != nullNode) && !found){
            int rval = r.element;
            if (val < rval)
                r = r.left;
            else if (val > rval)
                r = r.right;
            else
            {
                found = true;
                break;
            }
           searchN(r, val);
        }
        return r;
    }

    /* Function for inorder traversal */
    public void inorder()
    {
        inorder(header.right);
    }
    private void inorder(RedBlackNode r) {
        if (r != nullNode) {
            inorder(r.left);
            char c = 'B';
            if (r.color == 0)
                c = 'R';
            System.out.print(r.element +""+c+" ");
            inorder(r.right);
        }
    }

    /* Function for preorder traversal */
    public void preorder(){
        preorder(header.right);
    }
    private void preorder(RedBlackNode r) {
        if (r != nullNode){
            header.right.posi=1; //** initialising root posi = 1 **//
            char c = 'B';
            if (r.color == 0)
                c = 'R';

            //**posi for child **//
            if (r.left != null){
                r.left.posi = r.posi*2;
            }
            if (r.right != null){
                r.right.posi = (r.posi*2)+1;
            }

            System.out.print(r.element +""+c+" "+r.posi+" //");
            preorder(r.left);
            preorder(r.right);
        }
    }

    /* Function for postorder traversal */
    public void postorder()
    {
        postorder(header.right);
    }
    private void postorder(RedBlackNode r) {
        if (r != nullNode)
        {
            postorder(r.left);
            postorder(r.right);
            char c = 'B';
            if (r.color == 0)
                c = 'R';
            System.out.print(r.element +""+c+" ");
        }
    }

    //**Function for CASE 6 **//
    public void parse(String input){
        //parsing the string input into array data type [5,B,2]
        //i do admit i copy from stackoverflow, but i DO study and understand the code
        Pattern p = Pattern.compile("[A-Z]+|\\d+");
        Matcher m = p.matcher(input);
        ArrayList<String> allMatches = new ArrayList<>();
        while (m.find()) {
            allMatches.add(m.group());
        }
        //converting arraylist to array
        String[] nInput = allMatches.toArray(new String[allMatches.size()]);
        boolean found = false,color = false,posi = false;

        //checking existence
        if (search(Integer.parseInt(nInput[0]))){
            RedBlackNode r = searchN((Integer.parseInt(nInput[0])));
            System.out.println(nInput[0]+" is found in the tree");
            found = true;

            //check color
            //1.finding color
            int colorNum = r.color;
            String colorText="";
            //2.assigning letters
            if (colorNum ==0){
                colorText= "R";
            }else if(colorNum==1){
                colorText="B";
            }
            //3.compare
            if (nInput[1].toString().equals(colorText)){
                color = true;
                System.out.println(nInput[1]+" color is true");
            }else{
                System.out.println(nInput[1]+" is wrong color!");
                color=false;
            }

            //check posi
            int posiNum = r.posi;
            if (nInput[2].equals((Integer.toString(posiNum)))){
                posi = true;
                System.out.println(nInput[2]+" position is true");
            }else{
                System.out.println(nInput[2]+" is wrong position!");
                posi=false;
            }
        }else{
            //case not found
            System.out.println(nInput[0]+" is not found in the tree");
            found=false;
        }

        //finalize
        if (found && color && posi){
            System.out.println(input+" is TRUE");
        }else{
            System.out.println(input+" is FALSE");
        }
    }
}