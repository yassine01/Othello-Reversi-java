package Othello;


public class Outils {

    public static final int OTHELLIER_SIZE = 8;
    public static final int NOIR = 1;
    public static final int BLANC = -1;
    public static final int VIDE = 0;
    public static final int MAX_PROFONDEUR = 4;
    public static final long WAIT_FOR_MILLIS = 250;

    public static int getColAdversaire(int colour) {
        if (colour == Outils.NOIR) {
            return Outils.BLANC;
        } else {
            return Outils.NOIR;
        }
    }

    public static String getNomJoueur(int playerColour) {
        String playerName = "Noir";
        if (playerColour == Outils.BLANC) {
            playerName = "Blanc";
        }
        return playerName;
    }
}
