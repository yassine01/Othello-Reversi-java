package Othello.gui;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List ;
import javax.swing.JPanel;
import Othello.Othellier;
import Othello.JeuControle;
import Othello.Deplacement;
import Othello.Position;
import Othello.Outils;
import Othello.JeuVue;
import Othello.OthellierListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class OthellierPanel extends JPanel implements OthellierListener, JeuVue, MouseMotionListener, MouseListener {

    private Color borderCouleur;
    private Color othellierBgCouleur;
    private Color gridCouleur;
    private Color CouleurJBlanc;
    private Color CouleurJNoir;
    private Color textCouleur;
    private Color textBgCouleur1;
    private Color textBgCouleur2;
    private Color DepBlancValide;
    private Color DepNoirValide;
    private Color highlightCouleur;
    private Color highlightInvalideCouleur;
    private Font textFont;
    private int minGap;
    private Rectangle r;
    private Othellier othellier;
    private JeuControle controller;
    private Point highlightedSquare;
    private int colJoueurAct;
    private boolean drawValidMoves;
    private boolean ComputerJoue;
    private boolean joueurPasDep;
    private String JoueurSansDep;
    private long lastClickTime;
    MainWindow mw ;
    Chrono  c = new Chrono();


    public OthellierPanel() {
        init();

    }
    
    private void dessinerChrono () {
       
        c.start();
        System.out.println("Le temps: "+c.getDureeTxt());

    }
        
    private void init() {

        this.minGap = 20;
        this.r = null;
        this.highlightedSquare = null;

        this.drawValidMoves = true;
        this.ComputerJoue = false;
        this.joueurPasDep = false;
        this.JoueurSansDep = "";

        this.borderCouleur = new Color(0, 0, 0); //black
        this.othellierBgCouleur = mw.myColor;
        
                this.gridCouleur = new Color(0, 0, 0); //black
            if (MainWindow.bleu){
                this.gridCouleur = new Color(255, 255, 255); //black
            }
            else {
               this.gridCouleur = new Color(0, 0, 0); } //black
            
       // this.gridCouleur = new Color(255, 255, 255); //black
        this.CouleurJBlanc = new Color(255, 255, 255); //white
        this.CouleurJNoir = new Color(0, 0, 0); //black
        this.textCouleur = new Color(0, 31, 63); //bleu bg
        this.textBgCouleur1 = new Color(255, 255, 255); //white
        this.textBgCouleur2 = new Color(200, 200, 200); //light grey
        this.DepBlancValide = new Color(122, 127, 132); //light grey  ou faire new Couleur(96, 195, 103); //light green
        this.DepNoirValide = new Color(49, 51, 53);  //dark Grey  ou faire new Couleur(0, 99, 7); dark green  
        this.highlightCouleur = new Color(255, 255, 0, 155);
        this.highlightInvalideCouleur = new Color(255, 0, 0, 100);

        this.textFont = new Font("SansSerif", Font.BOLD, 12);
        this.othellier = null;

        this.controller = null;
        this.colJoueurAct = Outils.NOIR;

        this.lastClickTime = 0;

        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }

    public void setDessinerDepValides(boolean drawValidMoves) {
        this.drawValidMoves = drawValidMoves;
    }

    public void setComputerJoue(boolean ComputerJoue) {
        this.ComputerJoue = ComputerJoue;
    }

    public void setJoueurDepInvalide(boolean depInvalide, String joueur) {
        this.joueurPasDep = joueurPasDep;
        this.JoueurSansDep = joueur;
    }

    public boolean getComputerJoue() {
        return this.ComputerJoue;
    }

    public void setOthellier(Othellier b) {
        if (this.othellier != null) {
            this.othellier.removeVue(this);
        }
        this.othellier = b;
        if (this.othellier != null) {
            this.othellier.addVue(this);
        }
        this.repaint();
                dessinerChrono();

    }

    public Othellier getOthellier() {
        return this.othellier;
    }

    public void setControle(JeuControle gc) {
        this.init();
        this.controller = gc;
        this.colJoueurAct = gc.getColJoueurAct();
    }

    public void jeuChangeNotif(JeuControle g) {
        this.colJoueurAct = g.getColJoueurAct();
        this.repaint();
    }

    public void forceUpdate() {
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics arg0) {
        super.paintComponent(arg0);
        this.dessiner(arg0);
    }

    private void dessiner(Graphics g) {

        int width = this.getWidth();
        int height = this.getHeight();

        BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) bi.getGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);



        g2d.setFont(this.textFont);
        FontMetrics fm = g2d.getFontMetrics();
        int gap = Math.max(Math.max(this.minGap, fm.getMaxAdvance() + 10), fm.getHeight() + 10);

        int rectDimension = Math.min(width, height) - (2 * gap) - 50;
        int rectLeft = (width - rectDimension) / 2;
        int rectTop = (height - rectDimension) / 2;

        this.r = new Rectangle(rectLeft, rectTop, rectDimension, rectDimension);

        g2d.setBackground(this.textBgCouleur1);
        g2d.setPaint(new GradientPaint(0, (float) (height * 0.5), this.textBgCouleur1, 0, (float) (height * 1.25), this.textBgCouleur2));
        g2d.fillRect(0, 0, width, height);

        this.drawJeuArea(g2d, rectLeft, rectTop, rectDimension, fm);
        this.dessinerBorder(g2d, width, height);
        if (this.othellier != null) {
            this.dessinerPions(g2d, rectLeft, rectTop, rectDimension);
          this.dessinerChrono();
            if (this.drawValidMoves) {
                this.dessinerDepValides(g2d, rectLeft, rectTop, rectDimension);
            }
            if (this.joueurPasDep) {
                this.dessinerNoDepAlert(g2d, this.r, this.JoueurSansDep);
            }
            this.dessinerScoreJeu(g2d, width, height);
            if (this.othellier.estGameOver()) {
                dessinerGameOver(g2d, this.r);
            } else {
                if (this.ComputerJoue) {
                    this.dessinerComputerJoue(g2d, width, height);
                }
            }
        }

        if (this.highlightedSquare != null) {
            this.highlightSquare(this.highlightedSquare.x, this.highlightedSquare.y, g2d, rectLeft, rectTop, rectDimension);
        }

        Graphics2D g2d2 = (Graphics2D) g;
        g2d2.drawImage(bi, null, 0, 0);

        g2d.dispose();
        g2d2.dispose();

    }
    

   

        /*
    JLabel l = new JLabel();
    int sec = 0; 
    int min=0;
    if(this.othellier !=null) { 
        try { 
            Thread.sleep(1000); 
            sec++;

            if (sec == 60){
                min+=1;
                sec=0;}
        l.setText("Temps écoulé : 0"+min+":"+sec);
            System.out.println("Temps écoulé : 0"+min+":"+sec);
        this.add(l);
        }
        catch (InterruptedException e) { 
        e.printStackTrace(); } */
      
        

    
    
    private void dessinerBorder(Graphics2D g2d, int width, int height) {
        g2d.setPaint(this.borderCouleur);
        g2d.drawRect(0, 0, width - 1, height - 1);
    }

    private void dessinerComputerJoue(Graphics2D g2d, int width, int height) {
        if (this.othellier != null) {
            g2d.setPaint(new Color(199,0,57));
            g2d.setFont(this.textFont);
            Point score = this.othellier.getScore();
            String str = "Le computer joue...";
            FontMetrics fm = g2d.getFontMetrics();
            int textHeight = fm.getHeight();
            int textWidth = fm.charsWidth(str.toCharArray(), 0, str.length());
            float textX = width - textWidth - 5;
            float textY = height - 5;
            g2d.drawString(str, textX, textY);
        }
    }

    private void dessinerScoreJeu(Graphics2D g2d, int width, int height) {
        if (this.othellier != null) {
            Point score = this.othellier.getScore();
            String str = "Noir: " + score.x + ", Blanc: " + score.y;
            if (this.othellier.estGameOver()) {
                if (score.x > score.y) {
                    str += " - Le noir gagne! :D ";
                } else if (score.y > score.x) {
                    str += " - Le blanc gagne! :D ";
                } else {
                    str += " - égalité !";
                }
            }
            float textX = 5;
            float textY = height - 5;
            g2d.setPaint(this.textCouleur);
            g2d.setFont(this.textFont);
            g2d.drawString(str, textX, textY);
        }
    }

    private void dessinerNoDepAlert(Graphics2D g2d, Rectangle2D r, String joueur) {
        int margin = 10;
        double x = r.getX() + margin;
        double w = r.getWidth() - 2 * margin;
        double h = w / 2;
        double y = r.getY() + (r.getHeight() - h) / 2;
        String str = joueur + " Pas de déplacements possibles...";
        int fontSize = (int) Math.max(12, (w - 20) / str.length());

        Rectangle2D.Double rect = new Rectangle2D.Double(x, y, w, h);
        g2d.setPaint(new Color(255, 255, 255, 210));
        g2d.fill(rect);
        g2d.setPaint(new Color(0, 0, 0));
        g2d.draw(rect);
        g2d.setFont(new Font("Monospaced", Font.BOLD, fontSize));
        FontMetrics fm = g2d.getFontMetrics();
        int textHeight = fm.getHeight();
        int textWidth = fm.charsWidth(str.toCharArray(), 0, str.length());
        float textY = (float) (y + (h - textHeight) / 2 + fm.getMaxAscent());
        float textX = (float) (x + (w - textWidth) / 2);
        g2d.drawString(str, textX, textY);
    }

    private void drawJeuArea(Graphics2D g2d, int rectLeft, int rectTop, int rectDimension, FontMetrics fm) {

        double squareWidth = rectDimension / 8;
        double squareHeight = squareWidth;

        int dimension = (int) squareWidth * 8;

        Rectangle rRect = new Rectangle(rectLeft, rectTop, dimension, dimension);

        g2d.setBackground(this.othellierBgCouleur);
        g2d.setPaint(this.othellierBgCouleur);
        g2d.fill(rRect);

        g2d.setStroke(new BasicStroke(1));

        g2d.setPaint(this.gridCouleur);
        g2d.draw(rRect);


        double y1 = rectTop;
        double y2 = rectTop + rRect.getHeight();
        for (int i = 1; i < 8; i++) {
            double x = rectLeft + i * squareWidth;
            g2d.draw(new Line2D.Double(x, y1, x, y2));
        }


        double x1 = rectLeft;
        double x2 = rectLeft + rRect.getWidth();
        for (int i = 1; i < 8; i++) {
            double y = rectTop + i * squareHeight;
            g2d.draw(new Line2D.Double(x1, y, x2, y));
        }

        
        g2d.setPaint(this.textCouleur);
        g2d.setFont(this.textFont);
        float y3 = rectTop - 10;
        float y4 = rectTop + dimension + fm.getHeight() + 5;
        char c = 'a';
        for (int i = 0; i < 8; i++) {
            int charWidth = fm.charWidth(c);
            float x = (float) ((rectLeft + i * squareWidth) + (squareWidth - charWidth) / 2);
            g2d.drawString(String.valueOf(c), x, y3);
            g2d.drawString(String.valueOf(c), x, y4);
            c++;
        }

        g2d.setPaint(this.textCouleur);
        g2d.setFont(this.textFont);
        float x3 = rectLeft - 10;
        float x4 = rectLeft + dimension + 10;
        char c2 = '1';
        int charHeight = fm.getHeight();
        for (int i = 0; i < 8; i++) {
            int charWidth = fm.charWidth(c2);
            x3 = rectLeft - 10 - charWidth;
            float y = (float) ((rectTop + i * squareHeight) + (squareHeight + charHeight) / 2);
            g2d.drawString(String.valueOf(c2), x3, y);
            g2d.drawString(String.valueOf(c2), x4, y);
            c2++;
        }
    }

    private void dessinerPions(Graphics2D g2d, int rectLeft, int rectTop, int rectDimension) {

        int black = this.othellier.getNoirVal();
        int white = this.othellier.getBlancVal();
        int empty = this.othellier.getVideVal();

        double squareDimension = rectDimension / 8;
        int margin = 2;
        double boundingBoxDimension = squareDimension - 2 * margin;

        Position p = this.othellier.getPosition();
        int value = empty;
        for (int i = 0; i < this.othellier.getOthellierDimension(); i++) {
            for (int j = 0; j < this.othellier.getOthellierDimension(); j++) {
                value = p.get(i, j);
                if (value != empty) {
                    double x = rectLeft + i * squareDimension + margin;
                    double y = rectTop + j * squareDimension + margin;
                    if (value == black) {
                        this.dessinerPionNoir(g2d, x, y, boundingBoxDimension);
                    } else if (value == white) {
                        this.dessinerPionBlanc(g2d, x, y, boundingBoxDimension);
                    }
                }
            }
        }

    }

    private void dessinerPionNoir(Graphics2D g2d, double x, double y, double dimension) {
        g2d.setPaint(this.CouleurJNoir);
        Ellipse2D disc = new Ellipse2D.Double(x, y, dimension, dimension);
        g2d.fill(disc);
    }

    private void dessinerPionBlanc(Graphics2D g2d, double x, double y, double dimension) {
        g2d.setPaint(this.CouleurJBlanc);
        Ellipse2D disc = new Ellipse2D.Double(x, y, dimension, dimension);
        g2d.fill(disc);
    }

    private void dessinerDepValides(Graphics2D g2d, int rectLeft, int rectTop, int rectDimension) {

        int playerColour = this.colJoueurAct;
        Color highlight = null;
        if (playerColour == Outils.NOIR) {
            highlight = this.DepNoirValide;
        } else {
            highlight = this.DepBlancValide;
        }
        double squareDimension = rectDimension / 8;
        int margin = 2;
        double boundingBoxDimension = squareDimension - 2 * margin;

        List <Deplacement> valid = this.othellier.getDepValides(playerColour);
        for (Iterator<Deplacement> it = valid.iterator(); it.hasNext();) {
            Deplacement move = it.next();
            int i = move.getX();
            int j = move.getY();
            double x = rectLeft + i * squareDimension + margin;
            double y = rectTop + j * squareDimension + margin;
            g2d.setPaint(highlight);
            Rectangle2D rect = new Rectangle2D.Double(x, y, boundingBoxDimension, boundingBoxDimension);
            g2d.fill(rect);
        }

    }

    private void highlightSquare(int i, int j, Graphics2D g2d, int rectLeft, int rectTop, int rectDimension) {
        double squareDimension = rectDimension / 8;
        double x = rectLeft + i * squareDimension + 2;
        double y = rectTop + j * squareDimension + 2;
        g2d.setPaint(this.highlightInvalideCouleur);
        Ellipse2D disc = new Ellipse2D.Double(x, y, squareDimension - 4, squareDimension - 4);
        if (this.othellier != null) {
            List <Deplacement> moves = this.othellier.getDepValides(this.colJoueurAct);
            Deplacement m = new Deplacement(i, j, this.colJoueurAct);
            if ((moves.contains(m)) && (!this.ComputerJoue)) {
                g2d.setPaint(this.highlightCouleur);
            }
        }
        g2d.fill(disc);
    }

    private void dessinerGameOver(Graphics2D g2d, Rectangle2D r) {
        int margin = 10;
        double x = r.getX() + margin;
        double w = r.getWidth() - 2 * margin;
        double h = w / 2;
        double y = r.getY() + (r.getHeight() - h) / 2;
        int fontSize = (int) Math.max(10, (w - 40) / 9);

        Rectangle2D.Double rect = new Rectangle2D.Double(x, y, w, h);
        g2d.setPaint(new Color(255, 255, 255, 210));
        g2d.fill(rect);
        g2d.setPaint(new Color(0, 0, 0));
        g2d.draw(rect);

        String str = "GAME OVER";
        g2d.setFont(new Font("Monospaced", Font.BOLD, fontSize));
        FontMetrics fm = g2d.getFontMetrics();
        int textHeight = fm.getHeight();
        int textWidth = fm.charsWidth(str.toCharArray(), 0, str.length());
        float textY = (float) (y + (h - textHeight) / 2 + fm.getMaxAscent());
        float textX = (float) (x + (w - textWidth) / 2);
        g2d.drawString(str, textX, textY);
    }

    public void OthellierChangeNotif(Othellier b) {
        if (b.equals(this.othellier)) {
            this.colJoueurAct = this.controller.getColJoueurAct();
            this.repaint();
        }
    }

    private Point getSquareUnderCursor(int x, int y, int rectLeft, int rectTop, int rectDimension) {
        int squareDimension = rectDimension / 8;
        int rectRight = rectLeft + rectDimension;
        int rectBottom = rectTop + rectDimension;
        int max = 7;
        if (this.othellier != null) {
            max = this.othellier.getOthellierDimension() - 1;
        }
        if ((x > rectLeft) && (y > rectTop) && (x < rectRight) && (y < rectBottom)) {
            int x1 = x - rectLeft;
            int y1 = y - rectTop;
            int i = Math.min((int) (x1 / squareDimension), max);
            int j = Math.min((int) (y1 / squareDimension), max);
            return new Point(i, j);
        } else {
            return null;
        }
    }

    // <editor-fold defaultstate="collapsed" desc=" MouseMotionListener Implementation ">
    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        if ((this.r != null) && (this.othellier != null)) {
            if ((!this.othellier.estGameOver()) && (!this.joueurPasDep)) {
                Point square = getSquareUnderCursor(e.getX(), e.getY(), this.r.x, this.r.y, this.r.width);
                if (square != this.highlightedSquare) {
                    this.repaint();
                }
                this.highlightedSquare = square;
            } else {
                this.highlightedSquare = null;
                this.repaint();
            }
        } else {
            this.highlightedSquare = null;
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" MouseListener Implementation ">
    public void mouseClicked(MouseEvent e) {

        long currentTime = System.currentTimeMillis();
        long diff = currentTime - this.lastClickTime;
        if (diff > 1000) {
            this.lastClickTime = currentTime;
            if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                if ((this.r != null) && (!this.ComputerJoue) && (!this.joueurPasDep)) {
                    Point square = getSquareUnderCursor(e.getX(), e.getY(), this.r.x, this.r.y, this.r.width);
                    if ((this.controller != null) && (square != null)) {
                        this.controller.jouer(square.x, square.y);
                    }
                }
            }
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
    // </editor-fold>
}
