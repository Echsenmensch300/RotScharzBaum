package RotSchwarzBaum;

import java.io.*;

/**
 * Quelle: http://www.peter-junglas.de/fh/vorlesungen/algorithmen/html/
 */

public class Baum {
  
  public BaumKnoten wurzel;
  
  public Baum() {
    // Konstruktor erzeugt leeren Baum
    wurzel = null;
  }
  
  public void einfügen(BaumKnoten neu) {
    // fügt Knoten neu an die richtige Stelle ein
    
    // 1. Fall: Baum ist noch leer
    if (wurzel == null) {
      wurzel = neu;
    } else {
      // sonst rekursiv durch
      einfügen(wurzel, neu);
    }
  }
  
  private void einfügen(BaumKnoten spitze, BaumKnoten neu) {
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
  
  private String toString(BaumKnoten b) {
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
    
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    
    // Anzahl der Werte abfragen
    System.out.println("Anzahl der Listenelemente eingeben:");
    String s = in.readLine();
    int anzahl = Integer.parseInt(s);
    
    // starte mit leerem Baum
    Baum sortierBaum = new Baum();
    
    // fülle den Baum
    for (int i = 0; i < anzahl; i++) {
      int wert =  (int)(Math.random()*10);
      BaumKnoten neu = new BaumKnoten(wert);
      sortierBaum.einfügen(neu);
    }
    
    // gib den Baum aus
    System.out.println(sortierBaum);
  }
}
