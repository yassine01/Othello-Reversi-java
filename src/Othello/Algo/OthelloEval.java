package Othello.Algo;

import Algo.Eval;
import Algo.Noeud;
import Othello.Position;
import Othello.Outils;

    /*
     *  Les methodes d'evaluation 
     */

public class OthelloEval extends Eval{

    public enum EvaluationMethode {
        VALID_MOVES_AND_TOTAL_SCORE, VALID_MOVES_AND_SIDES_COUNT, VALID_MOVES_AND_CORNERS
    }

     private int joueur;
     private EvaluationMethode evalMethod;

     public OthelloEval(){
         joueur = Outils.NOIR;
         evalMethod = OthelloEval.EvaluationMethode.VALID_MOVES_AND_CORNERS;
     }

     public OthelloEval(int joueur, EvaluationMethode evalMethod){
         this.joueur = joueur;
         this.evalMethod = evalMethod;
     }


    public int evaluer(Noeud n) {
        
        int val;

        OthelloNoeud node = (OthelloNoeud) n;
        Position p = node.getPosition();

        if (evalMethod == EvaluationMethode.VALID_MOVES_AND_TOTAL_SCORE) {
            val = countDepValides(p, joueur) + totalScore(p, joueur);
        } else if (evalMethod == EvaluationMethode.VALID_MOVES_AND_SIDES_COUNT) {
            val = countDepValides(p, joueur)+ countCotes(p, joueur);
        } else {
            val = countDepValides(p, joueur) + countCoins(p, joueur)*100;
        }
        return val;
    }

    private int joueursScore(Position p, int joueur) {
        int dimension = p.getDimension();

        int total = 0;
        int val = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                val = p.get(i, j);
                if (val == joueur) {
                    total++;
                }
            }
        }

        return total;
    }

    private int totalScore(Position p, int joueur) {
        int dimension = p.getDimension();
        int adversaire = Outils.getColAdversaire(joueur);

        int total = 0;
        int val = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                val = p.get(i, j);
                if (val == joueur) {
                    total++;
                } else if (val == adversaire) {
                    total--;
                }
            }
        }

        return total;
    }

    private int countDepValides(Position p, int joueur) {
        int mobility = p.getDepValides(joueur).size();

        return mobility;
    }

    private int countCoins(Position p, int joueur) {
        int total = 0;
        int adversaire = Outils.getColAdversaire(joueur);
        int val = 0;
        int max = p.getDimension() - 1;

        val = p.get(0, 0);
        if (val == joueur) {
            total++;
        } else if (val == adversaire) {
            total--;
        }

        val = p.get(max, 0);
        if (val == joueur) {
            total++;
        } else if (val == adversaire) {
            total--;
        }

        val = p.get(0, max);
        if (val == joueur) {
            total++;
        } else if (val == adversaire) {
            total--;
        }

        val = p.get(max, max);
        if (val == joueur) {
            total++;
        } else if (val == adversaire) {
            total--;
        }

        return total;
    }

    private int countCotes(Position p, int joueur) {
        int total = 0;
        int adversaire = Outils.getColAdversaire(joueur);
        int max = p.getDimension() - 1;
        int val = 0;

        for (int i = 1; i < max; i++) {

            val = p.get(i, 0);
            if (val == joueur) {
                total++;
            } else if (val == adversaire) {
                total--;
            }

            val = p.get(0, i);
            if (val == joueur) {
                total++;
            } else if (val == adversaire) {
                total--;
            }

            val = p.get(i, max);
            if (val == joueur) {
                total++;
            } else if (val == adversaire) {
                total--;
            }

            val = p.get(max, i);
            if (val == joueur) {
                total++;
            } else if (val == adversaire) {
                total--;
            }
        }

        return total;
    }

}
