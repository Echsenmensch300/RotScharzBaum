package RotSchwarzBaum;

import java.io.*;
import java.util.Random;

/**
 * Quelle: http://www.peter-junglas.de/fh/vorlesungen/algorithmen/html/
 */

public class Main {
  
  public RBT wurzel;
  
  public Main() {
    // Konstruktor erzeugt leeren Baum
    wurzel = null;
  }
  
  public void einfügen(RBT neu) {
    // fügt Knoten neu an die richtige Stelle ein
    
    // 1. Fall: Baum ist noch leer
    if (wurzel == null) {
      wurzel = neu;
    } else {
      // sonst rekursiv durch
      einfügen(wurzel, neu);
    }
  }
  
  private void einfügen(RBT spitze, RBT neu) {
    // fügt Knoten neu an die richtige Stelle unter Teilbaum spitze ein
    
    if (spitze.daten > neu.daten) {
      // links einfügen
      if (spitze.links == null) {
        // neu einfach anhängen
        spitze.links = neu;
      } else {
        // neu im linken Unterbaum unterbringen
        einfügen(spitze.links, neu);
      }
    } else {
      // rechts einfügen
      if (spitze.rechts == null) {
        // neu einfach anhängen
        spitze.rechts = neu;
      } else {
        // neu im rechten Unterbaum unterbringen
        einfügen(spitze.rechts, neu);
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
  
  private String toString(RBT b) {
    // Ausdrucken des Teilbaums ab b, Reihenfolge inorder
    String s = "";
    
    if (b.links != null) {
      s += toString(b.links);
    }
    s += b.daten + "\n";
    if (b.rechts != null) {
      s += toString(b.rechts);
    }
    
    return s;
  }
  
  // Testprogramm mit Zufallszahlen
  public static void main(String[] args) throws IOException {
    
    // Anzahl der Werte festlegen
    int anzahl = 15;
    
    // starte mit leerem Baum
    Main sortierBaum = new Main();
    
    // fülle den Baum
    for (int i = 0; i < anzahl; i++) {

      Random x = new Random(i);

      int wert;
      // Erzugt einen Festen Random wert
      x.setSeed(i);
      wert = x.nextInt(100);
      RBT neu = new RBT(wert);
      sortierBaum.einfügen(neu);
    }
    
    // gib den Baum aus
    System.out.println(sortierBaum);
  }
}
