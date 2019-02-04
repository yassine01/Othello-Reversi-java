package Othello.exceptions;

public class ValeurInvalideException extends Exception{

    private int val;

    public ValeurInvalideException(int val){
        this.val = val;
    }

    @Override
    public String toString(){
        return "Valeur invalide: "+this.val;
    }

}
