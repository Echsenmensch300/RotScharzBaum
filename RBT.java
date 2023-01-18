import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//Erstellen eines Knotens für den Rot-Schwarz-Baum. 
//Jeder Knoten beinhaltet ein linkes (leftChild) und ein rechtes Kind (rightChild), einen Wert (value) und die Farbe des Knotens (color).

class RedBlackNode {
    RedBlackNode leftChild, rightChild;
    int value;
    int color;

    // Kontruktor um den Wert (value) eines Knotens ohne linkes und rechtes Kind zu
    // setzen.
    public RedBlackNode(int value) {
        this(value, null, null);
    }

    // Konstruktor um den Wert (value), linkes (leftChild) und rechtes Kind
    // (rightChild) und Farbe (color) zu setzen
    public RedBlackNode(int value, RedBlackNode leftChild, RedBlackNode rightChild) {
        this.value = value;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        color = 1;
    }
}

// Erstellen des Objektes RotSchwarzBaum
class CreateRedBlackTree {
    private static RedBlackNode nullNode; // Definiere Nullknoten
    private RedBlackNode current; // Definiere aktuellen Knoten
    private RedBlackNode parent; // Definiere Elternknoten
    private RedBlackNode root; // Definiere Wurzelknoten
    private RedBlackNode grand; // Definiere "Großmutter"-Knoten
    private RedBlackNode great; // Definiere "Urgroßmutter"-Knoten

    // Festlegung von Rot (RED) als 0 und Schwarz (BLACK) als 1
    static final int RED = 0;
    static final int BLACK = 1;

    StringBuilder strTree = new StringBuilder();

    // Statischer Initialisierer um Nullknoten zu initialisieren
    static {
        nullNode = new RedBlackNode(0);
        nullNode.leftChild = nullNode;
        nullNode.rightChild = nullNode;

    }

    // Konstruktor um Wurzelknoten zu erstellen
    public CreateRedBlackTree(int root) {
        this.root = new RedBlackNode(root);
        this.root.leftChild = nullNode;
        this.root.rightChild = nullNode;
    }

    // Hinzufügen eines neuen Knoten zum Baum
    public void insertNewNode(int newValue) {
        current = parent = grand = root; // Setze root auf current, parent und grand node
        nullNode.value = newValue; // Neuer Wert (newValue) wird in Nullknoten(nullNode) gespeichert

        // Wiederholung des Ausdrucks bis der Wert des aktuellen Knotens (current.value)
        // nicht gleich dem neuen Wert ist (newValue)
        while (current.value != newValue) {
            great = grand;
            grand = parent;
            parent = current;

            // Wenn der neue Wert (newValue) weniger als der des aktuellen Knotens
            // (current.value) ist, dann zeigt der aktuelle Knoten auf sein linkes Kind
            // (current.leftChild),
            // ansonsten auf sein rechtes (current.rightChild).
            current = newValue < current.value ? current.leftChild : current.rightChild;

            // Überprüfung ob beide Kinder rot (RED) sind, wenn ja werden die Farben mittels
            // handleColors() geändert.
            if (current.leftChild.color == RED && current.rightChild.color == RED)
                handleColors(newValue);
        }

        // Abbruch, falls der neue Knoten schon im Baum ist
        if (current != nullNode)
            return;

        // Speichern eines Knotens ohne linkes oder rechtes Kind im aktuellen Knoten.
        current = new RedBlackNode(newValue, nullNode, nullNode);

        // Verbindung des aktuellen Knotens mit den Eltern
        if (newValue < parent.value)
            parent.leftChild = current;
        else
            parent.rightChild = current;
        handleColors(newValue);
        System.out.println("Insert ausgeführt mit key: " + newValue);
    }

    // Überprüfung und Korrigieren der Farben im Rot-Schwarz-Baums
    private void handleColors(int newValue) {
        // Farben des aktuellen Knotens ändern
        current.color = RED;
        current.leftChild.color = BLACK;
        current.rightChild.color = BLACK;

        // Überprüfung der Farbe des Elterknotens
        if (parent.color == RED) {
            // Rotieren, wenn die Farbe des Elternknotens rot ist
            grand.color = RED;

            if (newValue < grand.value && grand.value != newValue && newValue < parent.value)
                parent = performRotation(newValue, grand);
            current = performRotation(newValue, great);
            current.color = BLACK;
        }

        // Wurzelknoten schwarz färben
        root.rightChild.color = BLACK;
    }

    // Überprüfung in welche Richtung der Baum gedreht werden muss
    private RedBlackNode performRotation(int newValue, RedBlackNode parent) {
        // Überprüfung, ob der neue Wert weniger ist, als der des Elternknotens
        if (newValue < parent.value)
            // wenn ja, rotiere mit dem linken und rechten Kind und setze den return Wert
            // zum linken Kind des Elternknotens
            return parent.leftChild = newValue < parent.leftChild.value ? rotationWithLeftChild(parent.leftChild)
                    : rotationWithRightChild(parent.leftChild);
        else
            // wenn nicht, rotiere mit dem linken und rechten Kind und setze den return Wert
            // zum rechten Kind des Elternknotens
            return parent.rightChild = newValue < parent.rightChild.value ? rotationWithLeftChild(parent.rightChild)
                    : rotationWithRightChild(parent.rightChild);
    }

    // Rotieren des Binärbaums mit dem linken Kind
    private RedBlackNode rotationWithLeftChild(RedBlackNode node2) {
        RedBlackNode node1 = node2.leftChild;
        node2.leftChild = node1.rightChild;
        node1.rightChild = node2;
        return node1;
    }

    // Rotieren des Binärbaums mit dem rechten Kind
    private RedBlackNode rotationWithRightChild(RedBlackNode node1) {
        RedBlackNode node2 = node1.rightChild;
        node1.rightChild = node2.leftChild;
        node2.leftChild = node1.leftChild;
        return node2;
    }

    public int nodesInTree() {
        return nodesInTree(root.rightChild);
    }

    private int nodesInTree(RedBlackNode node) {
        if (node == nullNode) {
            return 0;
        }

        else {
            int size = 1;
            size = size + nodesInTree(node.leftChild);
            size = size + nodesInTree(node.rightChild);
            System.out.println(size);
            return size;
        }

    }

    // inorder-Ausgeben des Baums
    public void inorderTraversal() {
        inorderTraversal(root.rightChild);
    }

    private void inorderTraversal(RedBlackNode node) {
        if (node != nullNode) {

            if (node.leftChild == null && node.rightChild == null) {
                strTree.append("null," + node.value + "," + node.color + ";");
                strTree.append("null," + node.value + "," + node.color + ";");
            } else if (node.leftChild == null && node.rightChild != null) {
                strTree.append("null," + node.value + "," + node.color + ";");
            } else if (node.rightChild == null && node.leftChild != null) {
                strTree.append("null," + node.value + "," + node.color + ";");
            } else if (node.leftChild != null && node.rightChild != null) {
                strTree.append(node.leftChild.value + "," + node.value + "," + node.color + ";");
                strTree.append(node.rightChild.value + "," + node.value + "," + node.color + ";");
            }

            inorderTraversal(node.leftChild);
            if (node.color == BLACK)
                System.out.println(node.value + ", " + "BLACK");
            else {
                System.out.println(node.value + ", " + "RED");
            }

            inorderTraversal(node.rightChild);
            // strTree.append(node.value + "," + node.rightChild.value + "," + node.color + ";");
        }
    }

    public String dotToString() {
        String str = new String();
        inorderTraversal();
        System.out.println(strTree);
        String[] x = strTree.toString().split(";");
        String[][] z = new String[x.length][3];

        for (int i = 0; i < x.length; i++) {
            System.out.println(x[i]);
            String[] y = x[i].toString().split(",");
            for (int j = 0; j < y.length; j++) {
              z[i][j] = y[j];
            }
            if (z[i][0].equals("null")) {
              str += z[i][1] + " -> " + "n" + i + " ;\nn" + i
                  + " [label=\"NIL\", shape=record, width=.4,height=.25, fontsize=16];\n";
            } else {
              str += z[i][1] + " -> " + z[i][0] + ";\n";
              if (z[i][2].equals("1")) {
                str += z[i][0] + "[fillcolor=red];\n";
              }
            }
          }
        return str;
    }

    public void output() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.dot"));
            writer.write(
                    "digraph G {\ngraph [ratio=.48];\nnode [style=filled, color=black, shape=circle, width=.6 fontname=Helvetica, fontweight=bold, fontcolor=white, fontsize=24, fixedsize=true];\n\n");
            String str = dotToString();
            writer.write(str);
            writer.write("\n}");
            writer.close();

            System.out.println("Done!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void execWindowsProgram(String command) // throws Exception
    {
        try {
            Runtime.getRuntime().exec("cmd /c start" + command);
            System.out.println("SVG Datei erstellt!");
        }

        catch (Exception e) {
            System.out.println("exportToSvg.bat nicht gefunden\n\nEXPORT FEHLGESCHLAGEN");
        }
    }
}

class RedBlackTree {
    public static void main(String[] args) {

        CreateRedBlackTree obj = new CreateRedBlackTree(Integer.MIN_VALUE);

        int max = 100;
        int min = 1;
        int range = max - min + 1;
        int[] A = new int[10];

        // for (int i = 0; i < A.length; i++) {
        // int rand = (int) (Math.random() * range) + min;
        // A[i] = rand;
        // }

        int[] b = { 6, 1, 8, 11, 13, 17, 15, 25, 22, 27 };
        // int[] b = { 6, 1, 8 };

        for (int i = 0; i < A.length; i++) {
            A[i] = b[i];
        }

        for (int tmp : A) {
            obj.insertNewNode(tmp);
        }

        obj.inorderTraversal();
        obj.nodesInTree();
        obj.output();
    }
}