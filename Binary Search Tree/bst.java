package task3_data_structure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class BinarySearchTree {

    private static class Node {

        int data;
        Node left, right;

        Node(int value) {
            data = value;
            left = right = null;
        }
    }

    private Node root;

    BinarySearchTree() {
        root = null;
    }

    // Function to insert a new number into the tree
    void insert(int value) {
        root = insertRec(root, value);
    }

    private Node insertRec(Node root, int value) {
        if (root == null) {
            root = new Node(value);
            return root;
        }

        if (value < root.data) {
            root.left = insertRec(root.left, value);
        } else if (value > root.data) {
            root.right = insertRec(root.right, value);
        }
        /*ele vai fazendo isso até que o ultimo nó não tenha valores a direita ou esquerda(depende do caso) e com isso,
vai ter o valor igual a null e volta ao primeiro if, adicionando assim o valor naquela posição
         */

        return root;
    }

    void delete(int value) {
        root = deleteRec(root, value);
    }

    private Node deleteRec(Node root, int value) {
        if (root == null) {
            System.out.println("The chosen number doesn't exist in the tree.");
            return root;
        }

        if (value < root.data) {
            root.left = deleteRec(root.left, value);
        } else if (value > root.data) {
            root.right = deleteRec(root.right, value);
        } else {
            // Node with only one child or no child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;

            }

            // Node with two children
            root.data = minValue(root.right);

            // Delete the in-order successor
            root.right = deleteRec(root.right, root.data);
        }

        return root;
    }

    private int minValue(Node root) {
        int minValue = root.data;
        while (root.left != null) {
            minValue = root.left.data;
            root = root.left;
        }
        return minValue;
        /* Encontra o sucessor na ordem (o menor valor na subárvore direita 
        do nó a ser removido).
        Substitui o valor do nó atual pelo valor do sucessor na ordem.*/
    }

    // Function to search for a number in the tree
    boolean search(int value) {
        return searchRec(root, value);
    }

    private boolean searchRec(Node root, int value) {
        if (root == null) {
            return false;
        }/*significa que no nó atual que estamos o valor é nulo então quer dizer
        que estamos numa folha e  o valor buscado não existe na árvore*/

        if (value == root.data) {
            return true;
        }

        if (value < root.data) {
            return searchRec(root.left, value);
        } else {
            return searchRec(root.right, value);
        }
    }

    // Function to find the number of elements in the set within the interval [a, b]
    int countNumbersInRange(int a, int b) {
        return countNumbersInRangeRec(root, a, b);
    }

    private int countNumbersInRangeRec(Node root, int a, int b) {
        if (root == null) {
            return 0;
        }/*Se o nó atual  a raiz é nula, não há números nesta parte da árvore.
         Retorna 0, indicando que não há números no intervalo.*/
        int count = 0;
        if (root.data >= a && root.data <= b) {
            count++;
            //System.out.println( "Number(s) in this interval: "+ root.data );
        }

        if (root.data > a) {
            count += countNumbersInRangeRec(root.left, a, b);
        }

        if (root.data < b) {
            count += countNumbersInRangeRec(root.right, a, b);
        }

        return count;
    }

    // Function to print the tree structure on the screen
    void printTree() {
        printTreeRec(root, 0);
    }

    private void printTreeRec(Node root, int level) {
        //essa é a condição de parada da recursão, ela quer dizer que quando o root for nulo ele para de imprimir a árvore pois não faz sentido continuar
        if (root != null) {
            printTreeRec(root.right, level + 1);
            for (int i = 0; i < level; i++) {
                System.out.print("\t");
            }

            System.out.println(root.data);
            printTreeRec(root.left, level + 1);
        }
    }

    void constructTreeFromFile(String fileName) {
        Scanner scanner = new Scanner(System.in);

        try {
            Scanner fileScanner = new Scanner(new File(fileName));

            while (fileScanner.hasNext()) {
                String operation = fileScanner.next();

                switch (operation) {
                    case "ADD":
                        if (fileScanner.hasNextInt()) {
                            int addValue = fileScanner.nextInt();
                            insert(addValue);
                        } else {
                            System.out.println("Invalid format for ADD operation in the file.");
                        }
                        break;

                    case "SEA":
                        if (fileScanner.hasNextInt()) {
                            int searchValue = fileScanner.nextInt();
                            boolean isFound = search(searchValue);
                            System.out.println("SEA " + searchValue + ": " + (isFound ? "yes" : "no"));
                        } else {
                            System.out.println("Invalid format for SEA operation in the file.");
                        }
                        break;

                    case "DEL":
                        int deleteValue = fileScanner.nextInt();
                        delete(deleteValue);
                        break;

                    case "INT":
                        int intervalStart = fileScanner.nextInt();
                        int intervalEnd = fileScanner.nextInt();
                        int count = countNumbersInRange(intervalStart, intervalEnd);
                        System.out.println("INT [" + intervalStart + ", " + intervalEnd + "]: " + count);
                        break;
                    default:
                        System.out.println("Invalid operation: " + operation);
                        break;
                }
            }

            fileScanner.close();
            System.out.println("Tree constructed from file: " + fileName);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }

    public class Task3_Data_Structure {

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            BinarySearchTree bst = new BinarySearchTree();

            while (true) {
                System.out.println("\nMenu of Program:");
                System.out.println("1. Insert new number");
                System.out.println("2. Delete a number");
                System.out.println("3. Search for a number");
                System.out.println("4. Find how many numbers in the set are from interval <a, b>");
                System.out.println("5. Print tree structure on screen");
                System.out.println("6. Construct a tree from file");
                System.out.println("7. Exit");

                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Enter number to insert: ");
                        int insertValue = scanner.nextInt();
                        bst.insert(insertValue);
                        break;

                    case 2:
                        System.out.print("Enter number to delete: ");
                        int deleteValue = scanner.nextInt();
                        bst.delete(deleteValue);
                        break;

                    case 3:
                        System.out.print("Enter number to search: ");
                        int searchValue = scanner.nextInt();
                        boolean found = bst.search(searchValue);
                        System.out.println("Number " + searchValue + " found: " + found);
                        break;

                    case 4:
                        System.out.print("Enter interval boundaries (a b): ");
                        int a = scanner.nextInt();
                        int b = scanner.nextInt();
                        int count = bst.countNumbersInRange(a, b);
                        System.out.println("Number of elements in the set from interval <" + a + ", " + b + ">: " + count);
                        break;

                    case 5:
                        System.out.println("Tree structure:");
                        bst.printTree();
                        break;

                    case 6:
                        System.out.print("Enter the file name: ");
                        String fileName = scanner.next();
                        bst.constructTreeFromFile(fileName);
                        break;

                    case 7:
                        System.out.println("Exiting program");
                        scanner.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        }
    }
}