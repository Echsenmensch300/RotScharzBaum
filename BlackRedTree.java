import java.util.Stack;
import org.w3c.dom.Node;
import org.graphviz.Graphviz;

public class BlackRedTree {
    static BlackRedTree brt = new BlackRedTree();
    static Tree nilTree = brt.new Tree(0, null);
    static StringBuilder strTree = new StringBuilder();
    public static void main(String[] args) {
        Tree T = brt.new Tree(10, nilTree);

        int[] A = {13, 4, 26, 45, 3, 5, 12, 2, 67, 56};

        for(int tmp : A){
            insert(T, tmp);
            System.out.println("Insert ausgeführt mit key: " + tmp);
        }

        inorderTreeWalk(T);
    }

    public static void inorderTreeWalk(Tree T){
        if(T != null){
            inorderTreeWalk(T.left);
            System.out.println(T.key + ", " + T.color);
            inorderTreeWalk(T.right);
        }
    }

    //Vergleicht bei jeder Abzweigung, ob key größer oder kleiner des aktuellen Knotens ist
    public Tree search(Tree T, int k){
        if(T == null || k == T.key){
            return T;
        }
        if(k < T.key){
            return search(T.left, k);
        }
        return search(T.right, k);
    }

    public static void insert(Tree T, int key){
        Tree y = nilTree;
        Tree x = T;
        
        //Y läuft x hinterher, bis y ein Blatt ist (wenn x null ist)
        while(x != null){
            y = x;
            if(key < x.key){
                x = x.left;
            } else {
                x = x.right;
            }
        }

        //Wenn Baum leer war wird z zur root, ansonsten füge z links oder rechts von y ein
        Tree z = brt.new Tree(key, nilTree);
        if(y == null){
            T.root = z;
        } else if(z.key < y.key){
            y.left = z;
        } else {
            y.right = z;
        }
        z.left = null;
        z.right = null;
        z.color = true;
        fixup(T, z);
    }

    public static void fixup(Tree T, Tree z){
        Tree y = null;
        while(z.root.color ==  true){
            if(z.root == z.root.root.left){
                y = z.root.root.right;
                if(y.color == true){
                    z.root.color = false;
                    y.color = false;
                    z.root.root.color = true;
                    z = z.root.root;
                } else if(z == z.root.right){
                    z = z.root;
                    leftRotate(T, z);
                }
                z.root.color= false;
                z.root.root.color = true;
                rightRotate(T, z);
            } else {
                y = z.root.root.left;
                if(y.color == true){
                    z.root.color = false;
                    y.color = false;
                    z.root.root.color = true;
                    z = z.root.root;
                } else if(z == z.root.left){
                    z = z.root;
                    rightRotate(T, z);
                }
                z.root.color= false;
                z.root.root.color = true;
                leftRotate(T, z);
            }
        }
        T.root.color = false;
    }

    public static void leftRotate (Tree T, Tree x){
        Tree y = x.right;
        x.right = y.left;
        if(y.left != null){
            y.left.root = x;
        }
        y.root = x.root;
        if(x.root == null){
            T.root = y;
        } else if(x == x.root.left){
            x.root.left = y;
        } else {
            x.root.right = y;
        }
        y.left = x;
        x.root = y;
    }

    public static void rightRotate (Tree T, Tree x){
        Tree y = x.left;
        x.left = y.right;
        if(y.right != null){
            y.right.root = x;
        }
        y.root = x.root;
        if(x.root == null){
            T.root = y;
        } else if(x == x.root.right){
            x.root.right = y;
        } else {
            x.root.left = y;
        }
        y.right = x;
        x.root = y;
    }

    public static void inorderTreeWalkReturn(Tree T) {
        if (T != null) {
            inorderTreeWalkReturn(T.left);
            inorderTreeWalkReturn(T.right);
            strTree.append(T.key + "," + T.root.key + "," + T.color + ";");
        }
    }

    public static String dotToString(Tree _data) {
        String str = new String();
        inorderTreeWalkReturn(_data);
        String[] x = strTree.toString().split(";");
        String[][] z = new String[x.length][3];

        for (int i = 0; i < x.length; i++) {
            String[] y = x[i].toString().split(",");
            for (int j = 0; j < y.length; j++) {
                z[i][j] = y[j];
            }
            str += z[i][1] + " -> " + z[i][0] + ";\n";
            if (z[i][2].equals("true")) {
                str += z[i][0] + "[fillcolor=red];\n";
            }
        }
        return str;
    }

    public static void output(Tree _data) {
        // Tree dataTree;
        System.out.println(_data);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.dot"));
            writer.write(
                    "digraph G {\ngraph [ratio=.48];\nnode [style=filled, color=black, shape=circle, width=.6 fontname=Helvetica, fontweight=bold, fontcolor=white, fontsize=24, fixedsize=true];\n\n");
            String str = dotToString(_data);
            writer.write(str);
            writer.write("\n}");
            writer.close();

            System.out.println("Done!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public void output(Tree T, Graph graph){

    // // Erstelle einen leeren Stack
    // Stack<Tree> stack = new Stack<Tree>();
    // // Füge den Wurzelknoten des BST dem Stack hinzu
    // stack.push(T.root);

    // // Solange der Stack nicht leer ist
    // while(!stack.isEmpty()) {
    // // Pop einen Knoten vom Stack
    // Tree tree = stack.pop();

    // // Gib dem Knoten einen eindeutigen Namen
    // String nodeName = "Node" + tree.key;

    // // Füge den Knoten dem Graph hinzu
    // graph.addNode(nodeName);

    // // Wenn der Knoten einen linken Kindknoten hat
    // if(node.getLeft() != null) {
    // // Gib dem linken Kindknoten einen eindeutigen Namen
    // String leftChildName = "Node" + node.getLeft().getValue();
    // // Füge den linken Kindknoten dem Graph hinzu
    // graph.addNode(leftChildName);
    // // Füge eine Kante zwischen dem Knoten und seinem linken Kindknoten hinzu
    // graph.addEdge(nodeName, leftChildName, "");
    // // Füge den linken Kindknoten dem Stack hinzu
    // stack.push(node.getLeft());
    // }
    // // Wenn der Knoten einen rechten Kindknoten hat
    // if(node.getRight() != null) {
    // // Gib dem rechten Kindknoten einen eindeutigen Namen
    // String rightChildName = "Node" + node.getRight().getValue();
    // // Füge den rechten Kindknoten dem Graph hinzu
    // graph.addNode(rightChildName);
    // // Füge eine Kante zwischen dem Knoten und seinem rechten Kindknoten hinzu
    // graph.addEdge(nodeName, rightChildName, "");
    // // Füge den rechten Kindknoten dem Stack hinzu
    // stack.push(node.getRight());
    // }
    // // Wenn der Knoten keine Kindknoten hat
    // if(node.getLeft() == null && node.getRight() == null) {
    // // Füge dem Knoten ein Label mit seinem Wert hinzu
    // graph.addLabel(nodeName, String.valueOf(node.getValue()));
    // }
    // }
    // }


    class Tree {
        int key;
        boolean color = false;
        Tree left;
        Tree right;
        Tree root;
        Tree nil;
    
        Tree(int key, Tree root) {
            this.key = key;
            right = null;
            left = null;
            this.nil = nilTree;
            this.root = root;
        }
    }
}


