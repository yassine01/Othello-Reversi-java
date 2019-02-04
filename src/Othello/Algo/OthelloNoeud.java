package Othello.Algo;

import Algo.Noeud;
import java.util.Iterator;
import java.util.List ;
import Othello.Deplacement;
import Othello.Position;
import Othello.Outils;

public class OthelloNoeud extends Noeud {

    private Position position;
    private int actuJoueur;

    public OthelloNoeud(Position p, int actuJoueur) {
        super();
        init(p, actuJoueur);
    }

    public OthelloNoeud(OthelloNoeud parent, Deplacement d) {
        super(parent, d);
        init(parent.getPosition().getFilsPosition(d), Outils.getColAdversaire(parent.getActuJoueur()));
    }

    private void init(Position p, int actuJoueur) {
        this.setPosition(p);
        this.setActuJoueur(actuJoueur);
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getActuJoueur() {
        return this.actuJoueur;
    }

    public void setActuJoueur(int actuJoueur) {
        this.actuJoueur = actuJoueur;
    }

    public void etendre() {
        List <Deplacement> availableMoves = this.getPosition().getDepValides(this.actuJoueur);
        for (Iterator<Deplacement> it = availableMoves.iterator(); it.hasNext();) {
            Deplacement move = it.next();
            OthelloNoeud fils = new OthelloNoeud(this, move);
            this.addFils(fils);
        }
        this.setEtendu(true);
    }

    public boolean estFinDeJeu() {
        return this.getPosition().verifGameOver();
    }
}
