Jeu : Othello 
-------- Auteur ----------
par  : MJARDI Yassine

--------- COMPILATION ----------
* Pour la compilation du jeu:
  java -jar "Othello_MJARDI.jar"


---------- DOCUMENTATION ----------

** PACKAGE - Algo :
Il permet de gérer la partie algorithmique du jeu (jouer contre Computer optimal), j'ai utilisé l'algorithme de recherche : ' ALPHA BETA PRUNING ' 
avec trois méthodes d'évaluation :
VALID_MOVES_AND_TOTAL_SCORE 
VALID_MOVES_AND_SIDES_COUNT  
VALID_MOVES_AND_CORNERS_COUNT

** PACKAGE - OthelloAlgo :
l'implémentation des classes abstraites du premier package (Algo)

** PACKAGE - Othello :
Il contient toutes les parties importantes du jeu :
- les régles du jeu  (déplacement possibles, capturation des pions, calcul du score, identifier la position, verification instantanée du jeu (Début de jeu / GameOver).
- identifier les joueurs.
- identifier les tours.
- l'enregistrement de l'historique  d'une partie.

** PACKAGE - Othello.exceptions :
Il contient deux exceptions du jeu :
- DeplacementInvalideException
- ValeurInvalideException

** PACKAGE - Othello.gui :
Il contient la partie graphique du jeu :
- dessin de l'othellier / les pions 
- l'affichage des instructions :Nouvelle partie / GameOver / passer le tour à l'autre joueur / l'aide (les déplacements possibles) /  

 
------ CODE SOURCE ------

*** Les Classes ***
- MiniMax.java
- ComputerJoueur.java
- Deplacement.java
- EltHistorique.java
- IdleTemps.java
- JeuControle.java
- Othellier.java
- Outils.java
- Position.java
- OthelloEval.java
- OthelloNoeud.java
- Main.java
- MainWindow.java
- OthellierPanel.java
- Chrono.java

*** Les Classes abstraites ***
- Deplacement.java
- Eval.java
- Noeud.java

*** Les Interfaces *** 
- HistoriqueJeu.java
- IdleRep.java
- JeuVue.java
- OthellierListener.java

*** Les Exceptions ***
- DeplacementInvalideException.java
- ValeurInvalideException.java