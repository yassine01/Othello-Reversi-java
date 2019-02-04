package Othello;

public class Deplacement extends Algo.Deplacement{

    private int x;
    private int y;
    private int couleur;

    public Deplacement(int x, int y, int couleur){
        this.x = x;
        this.y = y;
        this.couleur = couleur;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getCouleur(){
        return this.couleur;
    }

    @Override
    public String toString(){
        return (x+1)+","+(y+1)+": "+couleur;
    }

    @Override
    public boolean equals(Object obj){
        if( ! (obj instanceof Deplacement) ){
            return false;
        }
        else{
            Deplacement m = (Deplacement) obj;
            if ( this.hashCode() == m.hashCode() ){
                return true;
            }
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + this.x;
        hash = 13 * hash + this.y;
        hash = 13 * hash + this.couleur;
        return hash;
    }

}
