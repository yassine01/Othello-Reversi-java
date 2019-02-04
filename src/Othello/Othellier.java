package Othello;

import Othello.exceptions.DeplacementInvalideException;
import java.awt.Point;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List ;

public class Othellier {
    
    private Position position;
    private int othellierDimension;
    private List <OthellierListener> views;
    private boolean gameOver;

    public Othellier() {
        this.othellierDimension = Outils.OTHELLIER_SIZE;
        this.position = new Position(this.othellierDimension);
        this.initPositionVals();

        this.views = new ArrayList <OthellierListener>();
        this.gameOver = false;

    }

    private void initPositionVals(){
        this.position.set(3,4, Outils.NOIR);
        this.position.set(4,3, Outils.NOIR);
        this.position.set(3,3, Outils.BLANC);
        this.position.set(4,4, Outils.BLANC);
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position p){
        if(p!=null){
            this.position = p;
            this.gameOver = this.verifGameOver();
            this.updateVues();
        }
    }

    public int getOthellierDimension() {
        return this.othellierDimension;
    }

    public int getNoirVal() {
        return Outils.NOIR;
    }

    public int getBlancVal() {
        return Outils.BLANC;
    }

    public int getVideVal() {
        return Outils.VIDE;
    }

    public void met(int x, int y, int couleur) throws DeplacementInvalideException {
        if (this.position.verifPosition(x, y, couleur)) {
            this.position.met(x, y, couleur);
            this.gameOver = this.verifGameOver();
            this.updateVues();            
        } else {
            throw new DeplacementInvalideException(x, y, couleur);
        }
    }

    public Point getScore(){
        return this.position.getScore();
    }

    public boolean estGameOver(){
        this.gameOver = this.verifGameOver();
        return this.gameOver;
    }

    private boolean verifGameOver() {
        return this.position.verifGameOver();
    }

    

    public boolean verifPosition(int x, int y, int couleur) {
        this.position.verifPosition(x,y,couleur);
        return false;
    }

    private void capturer(int x, int y, int couleur) {
        this.position.capturer(x,y,couleur);
        this.updateVues();
    }

    public List <Deplacement> getDepValides(int playerColour) {
        return this.position.getDepValides(playerColour);
    }

    public void addVue(OthellierListener view) {
        this.views.add(view);
    }

    public void removeVue(OthellierListener view) {
        this.views.remove(view);
    }

    public List <OthellierListener> getVues() {
        return this.views;
    }

    private void updateVues() {
        for (Iterator<OthellierListener> it = views.iterator(); it.hasNext();) {
            OthellierListener othellierVue = it.next();
            othellierVue.OthellierChangeNotif(this);
        }
    }

    public void printOthellier(PrintStream out) {
        this.position.afficher(out);
    }
}
