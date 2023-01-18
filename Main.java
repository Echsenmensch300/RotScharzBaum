// package ROTSCHW;

import java.io.*;
import java.util.Random;

/**
 * Quelle: http://www.peter-junglas.de/fh/vorlesungen/algorithmen/html/
 */

public class Main {

  static StringBuilder strTree = new StringBuilder();
  public int last = 0;
  public Tree wurzel;

  public Main() {
    // Konstruktor erzeugt leeren Baum
    wurzel = null;
  }

  public void einfügen(Tree neu) {
    // fügt Knoten neu an die richtige Stelle ein

    // 1. Fall: Baum ist noch leer
    if (wurzel == null) {
      wurzel = neu;
    } else {
      // sonst rekursiv durch
      RB_Insert(wurzel, neu);
    }
  }

  private void RB_Insert(Tree spitze, Tree neu) {
    // fügt Knoten neu an die richtige Stelle unter Teilbaum spitze ein

    if (spitze.key > neu.key) {
      // links einfügen
      if (spitze.left == null) {
        // neu einfach anhängen
        spitze.left = neu;
      } else {
        // neu im linken Unterbaum unterbringen
        RB_Insert(spitze.left, neu);
      }
    } else {
      // rechts einfügen
      if (spitze.right == null) {
        // neu einfach anhängen
        spitze.right = neu;
      } else {
        // neu im rechten Unterbaum unterbringen
        RB_Insert(spitze.right, neu);
      }
    }
  }

  public String toString() {
    if (wurzel != null) {
      return toString(wurzel);
    } else {
      return "<leerer Baum>";
    }
  }

  private String toString(Tree b) {
    // Ausdrucken des Teilbaums ab b, Reihenfolge inorder
    String s = "";

    if (b.left == null && b.right == null) {
      s += "null," + b.key + "," + b.color + ";";
      s += "null," + b.key + "," + b.color + ";";
    } else if (b.left == null && b.right != null) {
      s += "null," + b.key + "," + b.color + ";";
    } else if (b.right == null && b.left != null) {
      s += "null," + b.key + "," + b.color + ";";
    } else if (b.left != null && b.right != null) {
      s += b.left.key + "," + b.key + "," + b.color + ";";
      s += b.right.key + "," + b.key + "," + b.color + ";";
    }

    if (b.left != null) {
      s += toString(b.left);
      strTree.append(toString(b.left));
    }

    if (b.right != null) {
      s += toString(b.right);
      strTree.append(toString(b.right));
    }

    return s;
  }

  public static String dotToString(Main data) {
    String str = new String();
    // inorderTreeWalkReturn();
    // String[] x = strTree.toString().split(";");
    String[] x = data.toString().split(";");

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

  public static void output(Main data) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter("output.dot"));
      writer.write(
          "digraph G {\ngraph [ratio=.48];\nnode [style=filled, color=black, shape=circle, width=.6 fontname=Helvetica, fontweight=bold, fontcolor=white, fontsize=24, fixedsize=true];\n\n");
      String str = dotToString(data);
      writer.write(str);
      writer.write("\n}");
      writer.close();

      System.out.println("Done!");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Testprogramm mit Zufallszahlen
  public static void main(String[] args) throws IOException {

    // Anzahl der Werte festlegen
    int anzahl = 10;

    // starte mit leerem Baum
    Main sortierBaum = new Main();

    // fülle den Baum
    for (int i = 0; i < anzahl; i++) {

      Random x = new Random(i);
      int[] y = { 13, 8, 11, 6, 1, 17, 15, 25, 22, 27 };
      int wert;
      // Erzugt einen Festen Random wert
      x.setSeed(i);
      // wert = x.nextInt(100);
      wert = y[i];
      Tree neu = new Tree(wert);
      sortierBaum.einfügen(neu);

    }

    // gib den Baum aus
    System.out.println(sortierBaum);
    // output("2 -> 3 [fillcolor=red];\n 3 -> 2 ;\n 2 -> n1");
    // output(neu);
    output(sortierBaum);
  }
}

class Tree {

  public int key;
  public Tree left;
  public Tree right;
  public Tree parent;
  public int color;

  // einfacher Konstruktor
  public Tree(int n) {
    key = n;
    left = null; // zeigen noch nirgendwo hin
    right = null;
  }

  // Konstruktor mit Zielen
  public Tree(int n, Tree l, Tree r) {
    key = n;
    left = l;
    right = r;
    color = 0;
  }
}
