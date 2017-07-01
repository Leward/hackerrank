import java.util.HashSet;
import java.util.Set;

public class CycleDetection {

    boolean hasCycle(Node head) {
        return hasCycle(head, new HashSet<Node>());
    }

    boolean hasCycle(Node head, Set<Node> visitedNodes) {
        if(head == null) {
            return false;
        }
        if(visitedNodes.contains(head)) {
            return true;
        }
        if(head.next == null) {
            return false;
        }
        visitedNodes.add(head);
        return hasCycle(head.next, visitedNodes);
    }
}

class Node {
    int data;
    Node next;
}
