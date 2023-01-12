package RotSchwarzBaum;

public class BaumKnoten {

    public int daten;
    public BaumKnoten links;
    public BaumKnoten rechts;
     
    // einfacher Konstruktor
    public BaumKnoten(int n) {
      daten  = n;
      links  = null;   // zeigen noch nirgendwo hin
      rechts = null;
    }
  
    // Konstruktor mit Zielen
    public BaumKnoten(int n, BaumKnoten l, BaumKnoten r) {
      daten = n;
      links = l;
      rechts = r;
    }
  }