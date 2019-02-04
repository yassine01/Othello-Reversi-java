package Algo;

import java.util.ArrayList;
import java.util.List;

public abstract class Noeud {

    public enum TypeNOEUD {
        MAX, MIN
    }
    private boolean visite;
    private boolean etendu;
    private Noeud parent;
    private List<Noeud> lesFils;
    private int IndexFilsSuivant;
    private Deplacement DeplacementPrio;
    private Deplacement DeplacementSuivant;
    private TypeNOEUD type;
    private int profondeur;
    private Integer val;
    public static long IdNoeudSuivant;
    private long IdNoeud;

    public Noeud() {
        init(null, null);
    }

    public Noeud(Noeud parent, Deplacement m) {
        init(parent, m);
    }

    private void init(Noeud parent, Deplacement m) {
        this.setParent(parent);
        if (parent == null) {
            this.setType(TypeNOEUD.MAX);
            this.lesFils = new ArrayList<Noeud>();
            this.setDeplacementPrio(null);
            this.setDeplacementSuivant(null);
            this.setProfondeur(0);
            this.setVal(null);
            this.setVisite(false);
            this.setEtendu(false);
            Noeud.IdNoeudSuivant = 0;

        } else {
            this.setParent(parent);
            this.lesFils = new ArrayList<Noeud>();
            this.setDeplacementPrio(m);
            this.setDeplacementSuivant(null);
            this.setProfondeur(this.parent.getProfondeur() + 1);
            this.setVal(null);
            this.setVisite(false);
            this.setEtendu(false);
            this.setType(this.inverserType(parent.getType()));
        }

        this.IndexFilsSuivant = 0;
        this.IdNoeud = Noeud.IdNoeudSuivant;
        Noeud.IdNoeudSuivant++;
    }

    public Noeud getParent() {
        return this.parent;
    }

    public void setParent(Noeud parent) {
        this.parent = parent;
    }

    public Deplacement getDeplacementPrio() {
        return this.DeplacementPrio;
    }

    public void setDeplacementPrio(Deplacement DeplacementPrio) {
        this.DeplacementPrio = DeplacementPrio;
    }

    public boolean estRacine() {
        return (this.parent == null);
    }

    public boolean aEteVisite() {
        return this.visite;
    }

    public void setVisite(boolean visite) {
        this.visite = visite;
    }

    public boolean aEteEtendu() {
        return this.etendu;
    }

    public void setEtendu(boolean etendu) {
        this.etendu = etendu;
    }

    public TypeNOEUD getType() {
        return this.type;
    }

    public void setType(TypeNOEUD type) {
        this.type = type;
    }

    public int getProfondeur() {
        return this.profondeur;
    }

    public void setProfondeur(int profondeur) {
        this.profondeur = profondeur;
    }

    public void addFils(Noeud fils){
        this.lesFils.add(fils);
    }

    public abstract void etendre();

    public List<Noeud> getLesFils() {
        if (!this.aEteEtendu()) {
            this.etendre();
        }
        return this.lesFils;
    }

    public Noeud getFilsSuivant() {
        Noeud result = null;
        List<Noeud> FilsNoued = this.getLesFils();
        int maxFilsIndex = FilsNoued.size() - 1;
        if (this.IndexFilsSuivant <= maxFilsIndex) {
            result = FilsNoued.get(this.IndexFilsSuivant);
            this.IndexFilsSuivant++;
        }
        return result;
    }

    public boolean aPlusDeFils() {
        if (!this.aEteEtendu()) {
            this.etendre();
        }
        return (this.IndexFilsSuivant <= this.getLesFils().size() - 1);
    }

    public Deplacement getDeplacementSuivant() {
        return this.DeplacementSuivant;
    }

    public void setDeplacementSuivant(Deplacement m) {
        this.DeplacementSuivant = m;
    }

    public abstract boolean estFinDeJeu();


    public boolean estTerminale(int maxProfondeur) {
        if ((estFinDeJeu()) || (getProfondeur() == maxProfondeur)) {
            return true;
        }
        return false;
    }

    public TypeNOEUD inverserType(TypeNOEUD type) {
        if (type == TypeNOEUD.MAX) {
            return TypeNOEUD.MIN;
        }
        return TypeNOEUD.MAX;
    }

    public Integer getVal() {
        return this.val;
    }

    public boolean setVal(Integer newval) {
        Integer currentval = this.getVal();
        if (currentval == null) {
            this.val = newval;
            return true;
        } else {
            if (this.getType() == TypeNOEUD.MAX) {
                if (newval > currentval) {
                    this.val = newval;
                    return true;
                } else {
                    return false;
                }
            } else {
                if (newval < currentval) {
                    this.val = newval;
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    @Override
    public String toString() {
        String T = "MAX";
        if (this.getType() == TypeNOEUD.MIN) {
            T = "MIN";
        }
        Integer mmv = this.getVal();
        String V = "null";
        if (mmv != null) {
            V = mmv.toString();
        }
        return this.IdNoeud + " (V: " + V + ",T: " + T + ", D: " + this.getProfondeur() + ")";
    }
}