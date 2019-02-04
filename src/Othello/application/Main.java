package Othello.application;

import Othello.gui.MainWindow;

public class Main {

   
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

}
