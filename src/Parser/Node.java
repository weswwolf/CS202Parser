package Parser;

public class Node {
        Node left;
        Node right;

        Node()
        {
            left = right = null;
        }
        Node getLeft()
        {
            return left;
        }
        Node getRight()
        {
            return right;
        }
        void setLeft(Node set_to)
        {
            left = set_to;
        }
        void setRight(Node set_to)
        {
            right = set_to;
        }
}
