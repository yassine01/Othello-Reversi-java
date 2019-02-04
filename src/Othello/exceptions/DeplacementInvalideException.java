package Othello.exceptions;

import java.awt.Point;

public class DeplacementInvalideException extends Exception{

    private int x;
    private int y;
    private int couleurJoueur;

    public DeplacementInvalideException(int x, int y, int couleurJoueur){
        this.x = x;
        this.y = y;
        this.couleurJoueur = couleurJoueur;
    }

    public Point getPoint(){
        return new Point(this.x, this.y);
    }

    @Override
    public String toString(){
        String result = "Deplacement invalide: ";
        if(couleurJoueur==1){
            result = result + "Noir ";
        }
        else{
            result = result + "Blanc ";
        }
        result = result + "ne peut pas placer le pion sur ("+ (x+1) +", "+(y+1)+")!";
        return result;
    }

}
