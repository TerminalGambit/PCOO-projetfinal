Voici une version révisée et enrichie du rapport avec une explication plus détaillée de l’UML et des design patterns utilisés :

# Rapport de Projet - Développement du Jeu **Solo Chess** avec LibGDX

## Introduction

### Contexte du projet

Ce projet, intitulé **Solo Chess**, a pour objectif de développer un jeu de réflexion stratégique basé sur des règles spécifiques simplifiées des échecs. L'objectif principal est de réduire l'échiquier à une seule pièce à la fin du jeu. Si un roi est présent, il doit impérativement être la dernière pièce restante. Si aucun roi n'est présent, toute pièce restante valide peut conclure la partie.

Le développement de ce projet s'inscrit dans un cadre d'apprentissage individuel, avec toutes les étapes réalisées par **MasséJack**.

### Règles du jeu

Les règles principales de **Solo Chess** sont les suivantes :

1. L'échiquier est initialisé avec plusieurs pièces placées aléatoirement ou selon une configuration prédéfinie.
2. Chaque pièce peut se déplacer en respectant ses mouvements traditionnels aux échecs.
3. Une pièce ne peut effectuer que **deux mouvements maximum**. Après son deuxième mouvement, elle devient une **pièce noire**, immobile et inutilisable.
4. Les pièces peuvent capturer d'autres pièces.
5. Le jeu se termine lorsque **une seule pièce reste** :
    - Si un roi est présent, il doit être la dernière pièce restante.
    - Sinon, toute pièce restante conclut la partie.
6. Si toutes les pièces restantes deviennent noires avant la fin, le joueur perd immédiatement.

---

## Présentation Technique du Projet

### Design Patterns Utilisés

Le projet repose sur plusieurs design patterns bien établis afin de garantir une architecture extensible, modulaire et maintenable.

#### 1. **Observer/Observable**

- Le pattern **Observer/Observable** est utilisé pour permettre à différentes parties du système (comme le `Renderer`) de réagir aux changements d'état du jeu.
- Le `Board` implémente l'interface `Observable` et notifie ses observateurs lorsque l'état de l'échiquier change, par exemple après un déplacement ou une capture.

```java
interface Observable {
    void addObserver(Observer observer);
    void notifyObservers();
}

interface Observer {
    void update();
}

2. Factory Pattern
	•	La classe PieceFactory est utilisée pour créer dynamiquement des instances de pièces (King, Queen, etc.) en fonction de leur type et couleur.
	•	Ce pattern facilite l’extension du projet en permettant l’ajout de nouvelles pièces sans modifier directement la logique de création.

class PieceFactory {
    Piece createPiece(String type, String color, Point position) {
        switch (type.toLowerCase()) {
            case "king": return new King(color, position);
            case "queen": return new Queen(color, position);
            // Autres types...
        }
    }
}

3. State Pattern
	•	Le pattern State est utilisé pour gérer les transitions entre différents états du jeu (en cours, en pause, terminé).
	•	Les états implémentent une interface commune GameStateInterface, ce qui garantit une gestion uniforme des différentes phases du jeu.

interface GameStateInterface {
    void update(float deltaTime);
    void render(SpriteBatch batch);
}

class PlayingState implements GameStateInterface { /* ... */ }
class PausedState implements GameStateInterface { /* ... */ }
class GameOverState implements GameStateInterface { /* ... */ }

4. Model-View-Controller (MVC)
	•	L’architecture globale suit le pattern MVC :
	•	Model : La classe Board gère l’état du jeu (pièces, positions, captures).
	•	View : La classe Renderer affiche l’état du jeu à l’écran.
	•	Controller : La classe Controller gère les entrées utilisateur et orchestre les interactions entre le modèle et la vue.

Explications des Classes UML

Classes Clés
	1.	Point
	•	Représente les coordonnées d’une case sur l’échiquier.
	•	Contient des méthodes utilitaires comme isWithinBounds() pour valider les déplacements.
	2.	Tile (Abstraite)
	•	Base pour EmptyTile (case vide) et OccupiedTile (case contenant une pièce).
	•	Facilite la gestion des interactions avec les cases de l’échiquier.
	3.	Piece (Abstraite)
	•	Définit les propriétés communes à toutes les pièces, comme la couleur et la position.
	•	Les classes concrètes (King, Queen, etc.) implémentent des méthodes spécifiques comme getPossibleMoves().
	4.	Board
	•	Gère la logique principale du jeu, y compris les déplacements et captures de pièces.
	•	Utilise des instances de Tile pour modéliser l’échiquier.
	5.	Renderer
	•	Responsable de l’affichage des éléments du jeu (pièces, échiquier).
	6.	Controller
	•	Gère les entrées utilisateur et coordonne les actions entre les composants (ex. déplacer une pièce, vérifier l’état du jeu).
	7.	GameStateInterface
	•	Fournit une abstraction pour les différents états du jeu.
	•	Les implémentations (PlayingState, GameOverState, etc.) définissent un comportement spécifique pour chaque phase.
