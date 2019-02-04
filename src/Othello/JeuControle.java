package Othello;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.List ;
import Algo.MiniMax;
import Othello.Algo.OthelloEval;
import Othello.exceptions.DeplacementInvalideException;
import Othello.Algo.OthelloNoeud;
import java.util.ArrayList;

public class JeuControle implements IdleRep {
    
    private int colJoueurAct;
    private Othellier othellier;
    private JeuVue vue;
    private int joueur1Couleur;
    private boolean JoueurIndiv;
    private long waitFor;
    private int maxProfondeur;
    private MiniMax.SearchAlgorithm algorithm;
    private OthelloEval.EvaluationMethode evalMethod;
    private ArrayDeque<EltHistorique> historique;
    private ArrayDeque<EltHistorique> future;
    private List <HistoriqueJeu> jeuEnreg;

    public JeuControle() {
        this.colJoueurAct = Outils.NOIR;
        this.othellier = null;
        this.vue = null;
        this.joueur1Couleur = this.colJoueurAct;
        this.waitFor = Outils.WAIT_FOR_MILLIS;
        this.maxProfondeur = Outils.MAX_PROFONDEUR;
        this.JoueurIndiv = true;
        this.algorithm = MiniMax.SearchAlgorithm.ALPHA_BETA_PRUNING;
        this.evalMethod = OthelloEval.EvaluationMethode.VALID_MOVES_AND_SIDES_COUNT;

        this.historique = new ArrayDeque<EltHistorique>();
        this.future = new ArrayDeque<EltHistorique>();
        this.jeuEnreg = new ArrayList <HistoriqueJeu>();
    }

    public JeuControle(int joueurCouleur, int maxProfondeur, long waitForMillis) {
        if ((joueurCouleur == Outils.NOIR) || (joueurCouleur == Outils.BLANC)) {
            this.joueur1Couleur = joueurCouleur;
        } else {
            this.joueur1Couleur = Outils.NOIR;
        }
        this.colJoueurAct = Outils.NOIR;
        this.waitFor = waitForMillis;
        this.maxProfondeur = maxProfondeur;
        this.othellier = null;
        this.vue = null;
        this.JoueurIndiv = true;
        this.algorithm = MiniMax.SearchAlgorithm.ALPHA_BETA_PRUNING;
        this.evalMethod = OthelloEval.EvaluationMethode.VALID_MOVES_AND_SIDES_COUNT;

        this.historique = new ArrayDeque<EltHistorique>();
        this.future = new ArrayDeque<EltHistorique>();

        this.jeuEnreg = new ArrayList <HistoriqueJeu>();
    }

    public JeuControle(boolean JoueurIndiv, int joueurCouleur, Othellier oth, JeuVue vue, int maxProfondeur, MiniMax.SearchAlgorithm algorithm, OthelloEval.EvaluationMethode evalMethod, long waitForMillis) {
        if ((joueurCouleur == Outils.NOIR) || (joueurCouleur == Outils.BLANC)) {
            this.joueur1Couleur = joueurCouleur;
        } else {
            this.joueur1Couleur = Outils.NOIR;
        }
        this.colJoueurAct = Outils.NOIR;
        this.maxProfondeur = maxProfondeur;
        this.algorithm = algorithm;
        this.evalMethod = evalMethod;
        this.waitFor = waitForMillis;
        this.JoueurIndiv = JoueurIndiv;
        this.jeuEnreg = new ArrayList <HistoriqueJeu>();
        this.setOthellier(oth);
        this.setJeuVue(vue);
    }

    public void setOthellier(Othellier oth) {
        this.othellier = oth;

        this.historique = new ArrayDeque<EltHistorique>();
        this.future = new ArrayDeque<EltHistorique>();

        if (this.vue != null) {
            this.vue.setOthellier(oth);
        }
    }

    public void setJeuVue(JeuVue v) {
        this.vue = v;
        if (this.vue != null) {
            this.vue.setControle(this);
            this.vue.setOthellier(this.othellier);
        }
    }

    public int getColJoueurAct() {
        return this.colJoueurAct;
    }

    public void CommencerJeu() {
        notifJeuEnregs(HistoriqueJeu.JeuEnregEvent.COMMENCER_JEU, null);
        if ((this.JoueurIndiv) && (this.joueur1Couleur == Outils.BLANC)) {
            this.ComputerCommencerDep();
        }
    }

    private void ComputerCommencerDep() {
        this.vue.setComputerJoue(true);
        OthelloNoeud n = new OthelloNoeud(this.othellier.getPosition(), this.colJoueurAct);
        ComputerJoueur tmp = new ComputerJoueur(this, n, this.maxProfondeur, this.algorithm, this.evalMethod, this.waitFor);
        tmp.start();

    }

    public void ComputerTerminerDep(OthelloNoeud n) throws DeplacementInvalideException {
        Deplacement suivant= (Deplacement)n.getDeplacementSuivant();
        if (suivant!= null) {
            this.jouer(suivant.getX(), suivant.getY());            
        }
        this.vue.setComputerJoue(false);
    }

    public void jouer(int x, int y) {
        if (this.othellier != null) {
            if (!this.othellier.estGameOver()) {
                Position prev = new Position(this.othellier.getPosition());

                try {
                    this.othellier.met(x, y, this.colJoueurAct);
                    this.historique.push(new EltHistorique(prev, this.colJoueurAct));
                    this.future.clear();
                    this.notifJeuEnregs(HistoriqueJeu.JeuEnregEvent.NEW_DEP, new Deplacement(x, y, this.colJoueurAct));

                    this.changerJoueur();
                    this.vue.jeuChangeNotif(this);

                    if((this.othellier.getDepValides(colJoueurAct).size() == 0)&&(!this.othellier.estGameOver())){
                        this.PasserTour();
                    }

                    if ((this.JoueurIndiv) && (this.colJoueurAct != joueur1Couleur)&&(!this.othellier.estGameOver())) {
                        this.ComputerCommencerDep();
                    }

                } catch (DeplacementInvalideException ime) {
                    System.err.println(ime.toString());
                }
            }
        }
    }

    private void changerJoueur() {
        this.colJoueurAct = Outils.getColAdversaire(this.colJoueurAct);
    }

    private void PasserTour() {
        String joueurNom = Outils.getNomJoueur(this.colJoueurAct);
        this.afficherNonDeplacement(joueurNom);
    }

    public void resume() {
        this.vue.setJoueurDepInvalide(false, "");
        this.changerJoueur();
        this.vue.jeuChangeNotif(this);
    }

    private void afficherNonDeplacement(String joueurNom) {
        this.vue.setJoueurDepInvalide(true, joueurNom);
        IdleTemps p = new IdleTemps(this, 2500);
        p.start();
    }


    public void addJeuEnreg(HistoriqueJeu logger) {
        this.jeuEnreg.add(logger);
    }

    public void removeJeuEnreg(HistoriqueJeu logger) {
        this.jeuEnreg.remove(logger);
    }

    private void notifJeuEnregs(HistoriqueJeu.JeuEnregEvent evt, Object param) {

        for (Iterator<HistoriqueJeu> it = jeuEnreg.iterator(); it.hasNext();) {
            HistoriqueJeu gameLogger = it.next();
            if (evt.equals(HistoriqueJeu.JeuEnregEvent.COMMENCER_JEU)) {
                gameLogger.newGameStarted();
            } else if (evt.equals(HistoriqueJeu.JeuEnregEvent.GAME_OVER)) {
                gameLogger.gameOver();
            } else if (evt.equals(HistoriqueJeu.JeuEnregEvent.NEW_DEP)) {
                gameLogger.newDep((Deplacement) param);
            }
        }

    }

    private void afficherHistorique() {
        for (Iterator<EltHistorique> it = historique.iterator(); it.hasNext();) {
            Position pos = it.next().getPosition();
            pos.afficher(System.out);
        }
    }
}
