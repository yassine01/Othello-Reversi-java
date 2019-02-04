package Othello;

public class EltHistorique {

    private int joueur;
    private Position p;

    public EltHistorique(Position p, int joueur){
        this.p = p;
        this.joueur = joueur;
    }

    public int getJoueur(){
        return this.joueur;
    }

    public Position getPosition(){
        return this.p;
    }

}
