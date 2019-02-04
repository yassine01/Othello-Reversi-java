package Othello;

import Algo.Eval;
import Algo.MiniMax;
import Othello.exceptions.DeplacementInvalideException;
import Algo.MiniMax.SearchAlgorithm;
import Othello.Algo.OthelloEval;
import Othello.Algo.OthelloEval.EvaluationMethode;
import Othello.Algo.OthelloNoeud;

public class ComputerJoueur extends Thread {

    private JeuControle jc;
    private long waitFor;
    private int d;
    private OthelloNoeud n;
    private SearchAlgorithm algorithm;
    private EvaluationMethode evalMethod;

    public ComputerJoueur(JeuControle jc, OthelloNoeud n, int maxDepth, SearchAlgorithm algorithm, EvaluationMethode evalMethod, long waitForMillis) {
        this.jc = jc;
        this.waitFor = waitForMillis;
        this.d = maxDepth;
        this.n = n;
        this.algorithm = algorithm;
        this.evalMethod = evalMethod;
    }

    @Override
    public void run() {
        long time1 = System.currentTimeMillis();
        MiniMax m = new MiniMax();
        Eval eval = new OthelloEval(n.getActuJoueur(), evalMethod);
        m.appliquer(n, d, algorithm, eval);

        long time2 = System.currentTimeMillis();
        long remaining = waitFor - (time2 - time1);

        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException ie) {
            }
        }

        try {
            jc.ComputerTerminerDep(n);
        } catch (DeplacementInvalideException ime) {
        }
    }
}
