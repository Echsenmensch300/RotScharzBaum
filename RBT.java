package RotSchwarzBaum;

public class RBT {

    public int daten;
    public RBT links;
    public RBT rechts;
     
    // einfacher Konstruktor
    public RBT(int n) {
      daten  = n;
      links  = null;   // zeigen noch nirgendwo hin
      rechts = null;
    }
  
    // Konstruktor mit Zielen
    public RBT(int n, RBT l, RBT r) {
      daten = n;
      links = l;
      rechts = r;
    }
  }