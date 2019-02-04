package Othello;

public interface JeuVue {

    public void jeuChangeNotif(JeuControle jc);
    public void forceUpdate();
    public void setControle(JeuControle jc);
    public Othellier getOthellier();
    public void  setOthellier(Othellier o);
    public void setComputerJoue(boolean cj);
    public boolean getComputerJoue();
    public void setJoueurDepInvalide(boolean JoueurDepInvalide, String joueur);

}
