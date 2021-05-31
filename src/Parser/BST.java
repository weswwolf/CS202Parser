package Parser;

import javax.swing.*;

// a red-black tree
// this is a type of self balancing tree that uses colors to decide how to balance the tree
// the idea is that each path through the tree from root to leaf should have the same number of black nodes
// rule: there cannot be 2 red nodes in a row. the root must be black. null nodes are considered black.
//       new nodes start as red.

// how to determine rotate or color change
// black aunt -- rotate
// red aunt -- color flip

public class BST {

    private class Node
    {
        // Node members
        private int data;
        private boolean is_red, is_left_child;
        private Node left, right, parent;
        // default constructor
        Node()
        {
            data = 0;
            left = right = parent = null;
            is_red = true;
            is_left_child = false;
        }

        // constructor with data arg
        Node(int data)
        {
            this.data = data;
        }

        // getter functions for pointer
        Node get_left()
        {
            return left;
        }
        Node get_right()
        {
            return right;
        }
        Node get_parent() { return parent; }

        // setter functions for pointer
        void connect_left(Node connect_to)
        {
            left = connect_to;
        }
        void connect_right(Node connect_to)
        {
            right = connect_to;
        }
        void connect_parent(Node connect_to) { parent = connect_to;}

        void display()
        {
            System.out.print(data + " ");
            if (parent == null)
                System.out.println("I am the root of the BST");
            else if (is_left_child)
                System.out.println("I am a left child");
            else
                System.out.println("I am a right child");

            if (is_red)
                System.out.println("I am red node\n");
            else
                System.out.println("I am black node\n");


        }

        // set is_red to the provided argument
        void recolor(boolean red)
        {
            this.is_red = red;
        }

        // set is_left to the provided arg
        void set_is_left(boolean set_to)
        {
           is_left_child = set_to;
        }

        public boolean red()
        {
            return is_red;
        }

        public boolean is_left_child()
        {
            return is_left_child;
        }

        public boolean greater_than(int arg)
        {
            return data > arg;
        }
    }

    private Node root;

    BST()
    {
       root = null;
    }

    public boolean is_empty()
    {
        return (root == null);
    }

    public void delete_all()
    {
        root = null;
    }

    // return the larger of two values
    /*
    private int larger(int left, int right)
    {
        if (left < right)
            return right;
        return left;
    }
     */

    // add the data to the tree
    public void add(int data)
    {
        if (this.root == null) // empty BST
        {
            this.root = new Node(data);
            this.root.recolor(false); // make the root node black
            return;
        }
        // recursive call to add the data to the BST
        add(data, this.root);
        this.root.recolor(false);
    }
    // add the node to the tree and balance
    private void add(int data, Node parent)
    {
        if (parent.greater_than(data)) // add this data to the left
        {
           if (parent.get_left() == null) // this is the place to add it
           {
               System.out.println("adding to the left");
               parent.connect_left(new Node(data));
               Node child = parent.get_left();
               child.connect_parent(parent);
               child.set_is_left(true); // this is a left child
               child.recolor(true);
               // leave the color set to red initially
               check_color(child); // makes sure the rules are not being violated
               //parent;
           }
           else
           {

               add(data, parent.get_left()); // add it somewhere else down the tree
               //check_color(parent);
               // parent;
           }

        } // else, data is greater than or equal to parent

        else if (parent.get_right() == null) // this is the place to add the new node
        {
            System.out.println("adding to the right");
            parent.connect_right(new Node(data));
            Node child = parent.get_right();
            child.connect_parent(parent);
            child.set_is_left(false); // this is a right child
            child.recolor(true); //
            // leave the color set to red initially
            check_color(child); // makes sure the rules are not being violated
            // parent;
        }
        else
        {
            add(data, parent.get_right()); // add the new node somewhere else down the tree
            check_color(parent);
            //parent.connect_right(add(data, parent.get_right())); // add the new node somewhere else down the tree
            // parent;
        }
    }


    // rules for the tree
    // root is black
    // new nodes are red
    // null nodes are black
    // no two consecutive red nodes
    // same number of black nodes on every path from root to leaf

    // black aunt -- rotate
    // red aunt -- color flip

    private void check_color(Node check)
    {
        // if we are violating any of the rules, we will have to fix it!
        if (check == this.root)
        {
            this.root.recolor(false);
            return;
        }

        if (check.red() && check.get_parent().red()) // rule violated
        {
            if (check.get_parent().get_parent() != null)
            correctTree(check); // fix the tree
        }
    }

    //
    public void correctTree(Node node)
    {
        //if (node.get_parent() != null && node.get_parent().get_parent() != null)
        System.out.println("correct tree function called");
        // figure out if the aunt is red or black so we can apply the necessary rule
        if(node.get_parent().is_left_child())
        {
            // the grandparents right child is the aunt
            // it is the node to_correct's parent's parent's right child
            // black aunt -- rotate
            // red aunt -- color flip
            if (node.get_parent().get_parent().get_right() == null // its considered black node
                    || !(node.get_parent().get_parent().get_right().red())) // Aunt is black node
            {
               rotate(node); // do a rotation on this node
                return;
            }
            // the aunt is red
            // we are going to do a color flip
            // We set the parent to black, grandparent to red, and the aunt to black. ask if aunt is null
            else //if (node.get_parent().get_parent().get_right() != null)
            {
                node.get_parent().get_parent().get_right().recolor(false);
                node.get_parent().get_parent().recolor(true);
                node.get_parent().recolor(false);
                return;
            }
            // aunt is grandparent.get_left()

        }
        else
        // the parent is a right child
        // the aunt is the grandparents left child
        // it is the node to_correct's parent's parent's left child
        {
            // the grandparents left child is the aunt
            // it is the node to_correct's parent's parent's left child
            // black aunt -- rotate
            // red aunt -- color flip
            if (node.get_parent().get_parent().get_left() == null // its considered black node
                    || !(node.get_parent().get_parent().get_left().red())) // Aunt is black node
            {
                rotate(node); // do a rotation on this node
                return;
            }
            // the aunt is red
            // we are going to do a color flip
            // We set the parent to black, grandparent to red, and the aunt to black. ask if aunt is null
            //if (node.get_parent().get_parent().get_left() != null)
            else
            {
                node.get_parent().get_parent().get_left().recolor(false);
                node.get_parent().get_parent().recolor(true);
                node.get_parent().recolor(false);
                return;
            }
        }
    }

    // the rules for determining which rotate we need to use
    // if the parent is a left child and we are a left child, we need to do a right rotation
    // if the parent is a right child and we are a right child, we need to do a left rotation
    // if the parent is a left child and we are a right child, we need to do a left-right rotation
    // if the parent is a right child and we are a left child, we need to do a right-left rotation
    public void rotate(Node node)
    {
        if (node.is_left_child())
        {
            if (node.get_parent().is_left_child())// we are going to do a right rotation
            {
                right_rotate(node.get_parent().get_parent()); // pass in the grandparent
                node.recolor(true); // after the rotation, fix the colors
                node.get_parent().recolor(false);
                if (node.get_parent().get_right() != null)
                    node.get_parent().get_right().recolor(true);
                return;
            }
            else // the parent is a right child
            {
                right_left_rotate(node.get_parent().get_parent());
                // after the rotation, fix the colors
                node.recolor(false);
                node.get_right().recolor(true);
                node.get_left().recolor(true);
                return;
            }
        }
        else // node is right child
        {
            // nodes parent is a right child
            if (!node.get_parent().is_left_child())// we are going to do a left rotation
            {
                left_rotate(node.get_parent().get_parent()); // pass in the grandparent
                node.recolor(true); // after the rotation, fix the colors
                node.get_parent().recolor(false);
                if (node.get_parent().get_left() != null)
                    node.get_parent().get_left().recolor(true);
            }
            else // the parent is a left child
            {
                left_right_rotate(node.get_parent().get_parent());
                // after the rotation, fix the colors
                node.recolor(false);
                node.get_right().recolor(true);
                node.get_left().recolor(true);
            }
        }
    }

    public void left_rotate(Node node)
    {
        Node temp = node.get_right();
        node.connect_right(temp.get_left());
        if (node.get_right() != null)
        {
            // connect up the parent pointer
            node.get_right().connect_parent(node);
            // it's not a left child anymore because it was moved
            node.get_right().set_is_left(false);
        }
        if (node.get_parent() == null)
        {
            // we are the root node
            this.root = temp;
            temp.connect_parent(null);
        }
        else // set temp's parent to our parent
        {
            temp.connect_parent(node.get_parent());
            if (node.is_left_child())
            {
                // set the new 'parent' to be a left child as well
                temp.set_is_left(true);
                temp.get_parent().connect_left(temp);
            }
            else
            {
                temp.set_is_left(false);
                temp.get_parent().connect_right(temp);
            }
        }
        temp.connect_left(node);
        node.set_is_left(true);
        node.connect_parent(temp);
    }

    // perhaps refactor left and right rotate to be one rotate with
    // different arg for left or right?

    public void right_rotate(Node node)
    {
        Node temp = node.get_left();
        node.connect_left(temp.get_right());
        if (node.get_left() != null)
        {
            // connect up the parent pointer
            node.get_left().connect_parent(node);
            // it's not a right child anymore because it was moved
            node.get_left().set_is_left(true);
        }
        if (node.get_parent() == null)
        {
            // we are the root node
            this.root = temp;
            temp.connect_parent(null);
        }
        else // set temp's parent to our parent
        {
            temp.connect_parent(node.get_parent());
            if (node.is_left_child())
            {
                // set the new 'parent' to be a left child as well
                temp.set_is_left(true);
                temp.get_parent().connect_left(temp);
            }
            else
            {
                temp.set_is_left(false);
                temp.get_parent().connect_right(temp);
            }
        }
        temp.connect_right(node);
        node.set_is_left(false);
        node.connect_parent(temp);
    }

    // the node passed in is the grandparent
    public void right_left_rotate(Node node)
    {
        // the parent is the nodes right child
        right_rotate(node.get_right());
        left_rotate(node);
    }

    // the node passed in is the grandparent
    public void left_right_rotate(Node node)
    {
        // the parent is the nodes left child
        // do left rotate on parent
        left_rotate(node.get_left());
        right_rotate(node);
    }
    /*
    private Node add(int data, Node root)
    {
        if (root == null)
        {
            root = new Node(data, false);
            return root;
        }
        if (root.greater_than(data))
            root.connect_left(add(data,root.get_left()));
        else
            root.connect_right(add(data,root.get_right()));


        return root;
    }
    */

    public void display()
    {
        int i = 1; // the level of the tree node
        display(root,i);
    }
    // display the contents of the BST in order
    private void display(Node current, int i)
    {
        if (current == null)
        {
            return;
        }
        display(current.get_left(),i+1);
        System.out.println("Level: " + i);
        current.display();
        display(current.get_right(),i+1);
    }
}
