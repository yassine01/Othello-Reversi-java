package Othello;

public interface HistoriqueJeu {

    public enum JeuEnregEvent{
        COMMENCER_JEU, GAME_OVER, NEW_DEP
    }

    public void newGameStarted();
    public void gameOver();
    public void newDep(Deplacement d);

}
