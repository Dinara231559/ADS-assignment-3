import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree <K extends Comparable<K>, V> {
    private Node root;

    private class Node {
        private K key;
        private V value;
        private Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node put(Node current, K key, V value) {
        if (current == null) {
            return new Node(key, value);
        }
        int cmp = key.compareTo(current.key);
        if (cmp < 0) {
            current.left = put(current.left, key, value);
        } else if (cmp > 0) {
            current.right = put(current.right, key, value);
        } else {
            current.value = value;
        }
        return current;
    }

    public V get(K key) {
        Node node = get(root, key);
        return node == null ? null : node.value;
    }

    private Node get(Node current, K key) {
        if (current == null) {
            return null;
        }
        int cmp = key.compareTo(current.key);
        if (cmp < 0) {
            return get(current.left, key);
        } else if (cmp > 0) {
            return get(current.right, key);
        } else {
            return current;
        }
    }

    public void delete(K key) {
        root = delete(root, key);
    }

    private Node delete(Node current, K key) {
        if (current == null) {
            return null;
        }
        int cmp = key.compareTo(current.key);
        if (cmp < 0) {
            current.left = delete(current.left, key);
        } else if (cmp > 0) {
            current.right = delete(current.right, key);
        } else {
            if (current.left == null && current.right == null) {
                return null;
            }
            if (current.left == null) {
                return current.right;
            }
            if (current.right == null) {
                return current.left;
            }
            Node temp = current;
            Node minNode = min(temp.right);
            current.key = minNode.key;
            current.value = minNode.value;
            current.right = delete(temp.right, minNode.key);
        }
        return current;
    }

    private Node min(Node current) {
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public Iterable<K> iterator() {
        List<K> keys = new ArrayList<>();
        inOrder(root, keys);
        return keys;
    }

    private void inOrder(Node node, List<K> keys) {
        if (node != null) {
            inOrder(node.left, keys);
            keys.add(node.key);
            inOrder(node.right, keys);
        }
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
        bst.put(5, "Five");
        bst.put(3, "Three");
        bst.put(7, "Seven");
        bst.put(2, "Two");
        bst.put(4, "Four");
        bst.put(6, "Six");
        bst.put(8, "Eight");

        for (Integer key : bst.iterator()) {
            System.out.println(key + " -> " + bst.get(key));
        }

        bst.delete(3);
        System.out.println("After deleting 3:");
        for (Integer key : bst.iterator()) {
            System.out.println(key + " -> " + bst.get(key));
        }
    }
}