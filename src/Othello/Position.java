package Othello;

import java.awt.Point;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Position {

    private int[][] position;
    private int dimension;

    public Position(){
        this.dimension = Outils.OTHELLIER_SIZE;
        position = new int[this.dimension][this.dimension];
        
        this.position[3][4] = Outils.NOIR;
        this.position[4][3] = Outils.NOIR;
        this.position[3][3] = Outils.BLANC;
        this.position[4][4] = Outils.BLANC;       
    }

    public Position(int dimension){
        this.dimension = dimension;
        position = new int[this.dimension][this.dimension];
    }

    public Position(Position original){
        this.dimension = original.getDimension();
        position = new int[this.dimension][this.dimension];
        for(int i=0; i<this.dimension; i++){
            for(int j=0; j<this.dimension; j++){
                this.position[i][j] = original.get(i, j);
            }
        }
    }
/*
    //utilisé pour le test:
    public void remplir(){
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                double rand = Math.random();
                int val = -1;
                //if(rand>=0.5){val=1;}
                //else{val=-1;}
                if((i!=7)||(j!=0)){
                    this.position[i][j] = val;
                }
            }
        }
        this.position[3][4]=1;
    }

    }*/

    public int getDimension(){
        return this.dimension;
    }

    public int get(int x, int y){
        return this.position[x][y];
    }

    public void set(int x, int y, int val){
        this.position[x][y] = val;
    }

    public void met(int x, int y, int val){
        this.position[x][y] = val;
        this.capturer(x, y, val);
    }

    public Point getScore(){
        int noirTotal = 0;
        int blancTotal = 0;
        int val = 0;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                val = this.position[i][j];
                if (val == Outils.NOIR) {
                    noirTotal++;
                } else if (val == Outils.BLANC) {
                    blancTotal++;
                }
            }
        }
        return new Point(noirTotal, blancTotal);
    }

    public boolean verifGameOver() {
        int noir = 0;
        int blanc = 0;
        int vide = 0;
        int noirLegal = 0;
        int blancLegal = 0;
        int val = 0;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                val = this.position[i][j];
                if (val == Outils.NOIR) {
                    noir++;
                } else if (val == Outils.BLANC) {
                    blanc++;
                }
                else{
                    vide++;
                }
                if (verifPosition(i, j, Outils.NOIR)) {
                    noirLegal++;
                }
                if (verifPosition(i, j, Outils.BLANC)) {
                    blancLegal++;
                }
            }
        }
        if( (vide==0) || (noir==0) || (blanc==0) || (      (noirLegal==0)&&(blancLegal==0)      ) ){
            return true;
        }
        return false;
    }

    public List<Deplacement> getDepValides(int joueurCol) {
        int val = 0;
        List<Deplacement> reslt = new ArrayList<Deplacement>();
        for (int j = 0; j < this.dimension; j++) {
            for (int i = 0; i < this.dimension; i++) {
                if (verifPosition(i, j, joueurCol)) {
                    Deplacement m = new Deplacement(i, j, joueurCol);
                    reslt.add(m);
                }
            }
        }
        return reslt;
    }

    public Position getFilsPosition(Deplacement dep){
        if(verifPosition(dep.getX(), dep.getY(), dep.getCouleur())){
            Position fils = new Position(this);
            fils.met(dep.getX(), dep.getY(), dep.getCouleur());
            return fils;
        }
        return null;
    }

    private boolean verifAGaucheHaut(int x, int y, int couleur) {
        if ((y == 0) || (x == 0)) {
            return false;
        }

        int colAdversaire = Outils.getColAdversaire(couleur);

        if (this.position[x - 1][y - 1] != colAdversaire) {
            return false;
        } else {
            int j = y - 2;
            int i = x - 2;
            int val = 0;
            boolean contigu = true;
            while ((j >= 0) && (i >= 0) && (contigu)) {
                val = this.position[i][j];
                if (val != colAdversaire) {
                    contigu = false;
                    if (val == couleur) {
                        return true;
                    }
                } else {
                    j--;
                    i--;
                }
            }
            return false;
        }

    }

    private boolean verifAGaucheBas(int x, int y, int couleur) {
        int max = this.dimension - 1;
        if ((y == max) || (x == 0)) {
            return false;
        }

        int colAdversaire = Outils.getColAdversaire(couleur);

        if (this.position[x - 1][y + 1] != colAdversaire) {
            return false;
        } else {
            int j = y + 2;
            int i = x - 2;
            int val = 0;
            boolean contigu = true;
            while ((j <= max) && (i >= 0) && (contigu)) {
                val = this.position[i][j];
                if (val != colAdversaire) {
                    contigu = false;
                    if (val == couleur) {
                        return true;
                    }
                } else {
                    j++;
                    i--;
                }
            }
            return false;
        }

    }

    private boolean verifADroiteHaut(int x, int y, int couleur) {
        int max = this.dimension - 1;
        if ((y == 0) || (x == max)) {
            return false;
        }

        int colAdversaire = Outils.getColAdversaire(couleur);

        if (this.position[x + 1][y - 1] != colAdversaire) {
            return false;
        } else {
            int j = y - 2;
            int i = x + 2;
            int val = 0;
            boolean contigu = true;
            while ((j >= 0) && (i <= max) && (contigu)) {
                val = this.position[i][j];
                if (val != colAdversaire) {
                    contigu = false;
                    if (val == couleur) {
                        return true;
                    }
                } else {
                    j--;
                    i++;
                }
            }
            return false;
        }

    }

    private boolean verifADroiteBas(int x, int y, int couleur) {
        int max = this.dimension - 1;
        if ((y == max) || (x == max)) {
            return false;
        }

        int colAdversaire = Outils.getColAdversaire(couleur);

        if (this.position[x + 1][y + 1] != colAdversaire) {
            return false;
        } else {
            int j = y + 2;
            int i = x + 2;
            int val = 0;
            boolean contigu = true;
            while ((j <= max) && (i <= max) && (contigu)) {
                val = this.position[i][j];
                if (val != colAdversaire) {
                    contigu = false;
                    if (val == couleur) {
                        return true;
                    }
                } else {
                    j++;
                    i++;
                }
            }
            return false;
        }

    }

    private boolean verifHaut(int x, int y, int couleur) {
        if (y == 0) {
            return false;
        }

        int colAdversaire = Outils.getColAdversaire(couleur);

        if (this.position[x][y - 1] != colAdversaire) {
            return false;
        } else {
            int j = y - 2;
            int val = 0;
            boolean contigu = true;
            while ((j >= 0) && (contigu)) {
                val = this.position[x][j];
                if (val != colAdversaire) {
                    contigu = false;
                    if (val == couleur) {
                        return true;
                    }
                } else {
                    j--;
                }
            }
            return false;
        }

    }

    private boolean verifBas(int x, int y, int couleur) {
        int max = this.dimension - 1;
        if (y == max) {
            return false;
        }

        int colAdversaire = Outils.getColAdversaire(couleur);

        if (this.position[x][y + 1] != colAdversaire) {
            return false;
        } else {
            int j = y + 2;
            int val = 0;
            boolean contigu = true;
            while ((j <= max) && (contigu)) {
                val = this.position[x][j];
                if (val != colAdversaire) {
                    contigu = false;
                    if (val == couleur) {
                        return true;
                    }
                } else {
                    j++;
                }
            }
            return false;
        }

    }

    private boolean verifAGauche(int x, int y, int couleur) {
        if (x == 0) {
            return false;
        }

        int colAdversaire = Outils.getColAdversaire(couleur);

        if (this.position[x - 1][y] != colAdversaire) {
            return false;
        } else {
            int i = x - 2;
            int val = 0;
            boolean contigu = true;
            while ((i >= 0) && (contigu)) {
                val = this.position[i][y];
                if (val != colAdversaire) {
                    contigu = false;
                    if (val == couleur) {
                        return true;
                    }
                } else {
                    i--;
                }
            }
            return false;
        }

    }

    private boolean verifADroite(int x, int y, int couleur) {
        int max = this.dimension - 1;
        if (x == max) {
            return false;
        }

        int colAdversaire = Outils.getColAdversaire(couleur);

        if (this.position[x + 1][y] != colAdversaire) {
            return false;
        } else {
            int i = x + 2;
            int val = 0;
            boolean contigu = true;
            while ((i <= max) && (contigu)) {
                val = this.position[i][y];
                if (val != colAdversaire) {
                    contigu = false;
                    if (val == couleur) {
                        return true;
                    }
                } else {
                    i++;
                }
            }
            return false;
        }

    }

    private boolean verifHorizontal(int x, int y, int couleur) {
        if (verifAGauche(x, y, couleur)) {
            return true;
        }
        if (verifADroite(x, y, couleur)) {
            return true;
        }
        return false;
    }

    private boolean verifVertical(int x, int y, int couleur) {
        if (verifHaut(x, y, couleur)) {
            return true;
        }
        if (verifBas(x, y, couleur)) {
            return true;
        }
        return false;
    }

    private boolean verifDiagonal(int x, int y, int couleur) {
        if (verifAGaucheHaut(x, y, couleur)) {
            return true;
        }
        if (verifAGaucheBas(x, y, couleur)) {
            return true;
        }
        if (verifADroiteHaut(x, y, couleur)) {
            return true;
        }
        if (verifADroiteBas(x, y, couleur)) {
            return true;
        }
        return false;
    }

    public boolean verifPosition(int x, int y, int couleur) {
        if (this.position[x][y] != Outils.VIDE) {
            return false;
        }
        if (this.verifHorizontal(x, y, couleur)) {
            return true;
        }
        if (this.verifVertical(x, y, couleur)) {
            return true;
        }
        if (this.verifDiagonal(x, y, couleur)) {
            return true;
        }
        return false;
    }

    private void essayerCapturerHaut(int x, int y, int couleur) {
        if (this.verifHaut(x, y, couleur)) {
            int i = y - 1;
            int val = 0;
            boolean contigu = true;
            while ((i >= 0) && (contigu)) {
                val = this.position[x][i];
                if (val == couleur) {
                    contigu = false;
                } else {
                    this.position[x][i] = couleur;
                    i--;
                }
            }
        }
    }

    private void essayerCapturerBas(int x, int y, int couleur) {
        int max = this.dimension - 1;
        if (this.verifBas(x, y, couleur)) {
            int i = y + 1;
            int val = 0;
            boolean contigu = true;
            while ((i <= max) && (contigu)) {
                val = this.position[x][i];
                if (val == couleur) {
                    contigu = false;
                } else {
                    this.position[x][i] = couleur;
                    i++;
                }
            }
        }
    }

    private void essayerCapturerADroite(int x, int y, int couleur) {
        int max = this.dimension - 1;
        if (this.verifADroite(x, y, couleur)) {
            int i = x + 1;
            int val = 0;
            boolean contigu = true;
            while ((i <= max) && (contigu)) {
                val = this.position[i][y];
                if (val == couleur) {
                    contigu = false;
                } else {
                    this.position[i][y] = couleur;
                    i++;
                }
            }
        }
    }

    private void essayerCapturerAGauche(int x, int y, int couleur) {
        if (this.verifAGauche(x, y, couleur)) {
            int i = x - 1;
            int val = 0;
            boolean contigu = true;
            while ((i >= 0) && (contigu)) {
                val = this.position[i][y];
                if (val == couleur) {
                    contigu = false;
                } else {
                    this.position[i][y] = couleur;
                    i--;
                }
            }
        }
    }

    private void essayerCapturerAGaucheHaut(int x, int y, int couleur) {
        if (this.verifAGaucheHaut(x, y, couleur)) {
            int j = y - 1;
            int i = x - 1;
            int val = 0;
            boolean contigu = true;
            while ((j >= 0) && (i >= 0) && (contigu)) {
                val = this.position[i][j];
                if (val == couleur) {
                    contigu = false;
                } else {
                    this.position[i][j] = couleur;
                    j--;
                    i--;
                }
            }
        }
    }

    private void essayerCapturerAGaucheBas(int x, int y, int couleur) {
        if (this.verifAGaucheBas(x, y, couleur)) {
            int max = this.dimension - 1;
            int j = y + 1;
            int i = x - 1;
            int val = 0;
            boolean contigu = true;
            while ((j <= max) && (i >= 0) && (contigu)) {
                val = this.position[i][j];
                if (val == couleur) {
                    contigu = false;
                } else {
                    this.position[i][j] = couleur;
                    j++;
                    i--;
                }
            }
        }
    }

    private void essayerCapturerADroiteHaut(int x, int y, int couleur) {
        if (this.verifADroiteHaut(x, y, couleur)) {
            int max = this.dimension - 1;
            int j = y - 1;
            int i = x + 1;
            int val = 0;
            boolean contigu = true;
            while ((j >= 0) && (i <= max) && (contigu)) {
                val = this.position[i][j];
                if (val == couleur) {
                    contigu = false;
                } else {
                    this.position[i][j] = couleur;
                    j--;
                    i++;
                }
            }
        }
    }

    private void essayerCapturerADroiteBas(int x, int y, int couleur) {
        if (this.verifADroiteBas(x, y, couleur)) {
            int max = this.dimension - 1;
            int j = y + 1;
            int i = x + 1;
            int val = 0;
            boolean contigu = true;
            while ((j <= max) && (i <= max) && (contigu)) {
                val = this.position[i][j];
                if (val == couleur) {
                    contigu = false;
                } else {
                    this.position[i][j] = couleur;
                    j++;
                    i++;
                }
            }
        }
    }

    public void capturer(int x, int y, int couleur) {
        this.essayerCapturerAGauche(x, y, couleur);
        this.essayerCapturerADroite(x, y, couleur);
        this.essayerCapturerHaut(x, y, couleur);
        this.essayerCapturerBas(x, y, couleur);
        this.essayerCapturerAGaucheHaut(x, y, couleur);
        this.essayerCapturerAGaucheBas(x, y, couleur);
        this.essayerCapturerADroiteHaut(x, y, couleur);
        this.essayerCapturerADroiteBas(x, y, couleur);
    }

    public void afficher(PrintStream out) {
        String c = "    | ";
        int val = 0;
        int blancTotal = 0;
        int noirTotal = 0;
        for (int j = 0; j < this.dimension; j++) {
            for (int i = 0; i < this.dimension; i++) {
                val = this.position[i][j];
                if (val == Outils.NOIR) {
                    c = " n  | ";
                    noirTotal++;
                } else if (val == Outils.BLANC) {
                    c = " b  | ";
                    blancTotal++;
                } else {

                    c = "    | ";

                }
                if (i == 0) {
                    c = "|" + c;
                }
                out.print(c);
            }
            out.println("");
            out.println("-----------------------------------------------");
        }
        out.println("Noir a : " + noirTotal + " Blanc a : " + blancTotal);
    }

}
