package Algo;

import java.util.ArrayDeque;
import Algo.Noeud.TypeNOEUD;

public class MiniMax {

    public enum SearchAlgorithm{
    ALPHA_BETA_PRUNING
    }
    
    public void appliquer(Noeud n, int maxDepth, SearchAlgorithm algorithm, Eval eval){
        if(algorithm == SearchAlgorithm.ALPHA_BETA_PRUNING){
            alphaBetaPruning(n, maxDepth, eval);
        }
    }

    private void alphaBetaPruning(Noeud n, int maxDepth, Eval eval) {
        if (n.estFinDeJeu()) {
            System.out.println("H1");
            return;
        }
        ArrayDeque<Noeud> searchStack = new ArrayDeque<Noeud>();
        searchStack.push(n);

        while (searchStack.isEmpty() == false) {
            Noeud noeud = searchStack.pop();

            Deplacement nextMove = noeud.getDeplacementSuivant();
            boolean estRacine = noeud.estRacine();

            if (noeud.estTerminale(maxDepth)) {
                noeud.setVal(eval.evaluer(noeud));
                if (!estRacine) {
                    Noeud parent = noeud.getParent();
                    if (parent.setVal(noeud.getVal())) {
                        parent.setDeplacementSuivant(noeud.getDeplacementPrio());
                    }
                }
            } else {
                if (noeud.aPlusDeFils()) {
                    if (!estRacine) {
                        Integer nodeValue = noeud.getVal();
                        if(nodeValue!=null){
                            Noeud parent = noeud.getParent();
                            Integer parentValue = parent.getVal();
                            if(parentValue==null){
                                searchStack.push(noeud);
                                searchStack.push(noeud.getFilsSuivant());
                            }else{
                                TypeNOEUD type = parent.getType();
                                if(type == TypeNOEUD.MAX){
                                    //Pruning:
                                    if(noeud.getVal()>=parentValue){
                                        searchStack.push(noeud);
                                        searchStack.push(noeud.getFilsSuivant());
                                    }
                                }else{
                                    //Pruning:
                                    if(noeud.getVal()<=parentValue){
                                        searchStack.push(noeud);
                                        searchStack.push(noeud.getFilsSuivant());
                                    }
                                }
                            }
                        }
                        else{
                            searchStack.push(noeud);
                            searchStack.push(noeud.getFilsSuivant());
                        }
                    }else{
                        searchStack.push(noeud);
                        searchStack.push(noeud.getFilsSuivant());
                    }
                } else {
                    if (nextMove != null) {
                        if (!estRacine) {
                            Noeud parent = noeud.getParent();
                            if (parent.setVal(noeud.getVal())) {
                                parent.setDeplacementSuivant(noeud.getDeplacementPrio());
                            }
                        }
                    }
                }
            }

            noeud.setVisite(true);
        }
    }

    

    private void afficher(Noeud n) {
        System.out.println(n.toString());
    }
}
