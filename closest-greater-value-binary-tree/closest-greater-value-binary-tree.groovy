class Solution {


    class Node {
        public Node left, right, parent;
        public int data;

        public Node(int newData) {
            left = right = parent = null;
            data = newData;
        }
    }

    static Node insert(Node node, int data) {
        if (node == null) {
            node = new Node(data);
        } else {
            if (data <= node.data) {
                node.left = insert(node.left, data);
                node.left.parent = node;
            } else {
                node.right = insert(node.right, data);
                node.right.parent = node;
            }
        }
        return (node);
    }

    static void Main(String[] args) {
        Node _root;
        int root_i = 0, root_cnt = 0, root_num = 0;
        root_cnt = Convert.ToInt32(Console.ReadLine());
        _root = null;
        for (root_i = 0; root_i < root_cnt; root_i++) {
            root_num = Convert.ToInt32(Console.ReadLine());
            if (root_i == 0)
                _root = new Node(root_num);
            else
                insert(_root, root_num);
        }

        int _x = Convert.ToInt32(Console.ReadLine());
        Console.WriteLine(nextIntegerGreaterThan(_root, _x));
        return;
    }

    static int nextIntegerGreaterThan(Node root, final int val) {
        /* For your reference
           class Node {
           Node left, right, parent;
           int data;
           Node(int newData) {
           left = right = parent = null;
           data = newData;
           }
           }
           */
        int smallestGreaterValue = (root.data > val) ? root.data : -1;
        if (root.left != null) {
            int fromChild = nextIntegerGreaterThan(root.left, val);
            if(fromChild > val && (smallestGreaterValue == -1 || fromChild < smallestGreaterValue)) {
                smallestGreaterValue = fromChild;
            }
        }
        if (root.right != null) {
            int fromChild = nextIntegerGreaterThan(root.right, val);
            if(fromChild > val && (smallestGreaterValue == -1 || fromChild < smallestGreaterValue)) {
                smallestGreaterValue = fromChild;
            }
        }
        return smallestGreaterValue;
    }
}