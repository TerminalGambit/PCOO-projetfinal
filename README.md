# Rapport de Projet – Développement du Jeu Solo Chess avec LibGDX

*Auteur : **Jack Massey**, projet individuel*

---

## Section 1. Introduction

### 1.1 Contexte du Projet

Ce projet, intitulé **Solo Chess**, a pour objectif de proposer un jeu de réflexion stratégique inspiré des échecs, mais avec des règles simplifiées et une mécanique centrée sur la capture de pièces jusqu’à ce qu’il n’en reste plus qu’une seule.  
Le développement a été réalisé **en solo** par Jack Massey, ce qui implique que tous les rôles (conception, programmation, design, etc.) ont été assurés par une seule personne.

### 1.2 Objectifs

- **Objectif principal** :  
  Développer un jeu en 2D, basé sur LibGDX, permettant de jouer à une variante des échecs appelée “Solo Chess”.  
  [Solo Chess - Lien du jeu sur chess.com](https://www.chess.com/solo-chess)

- **Concevoir un code de qualité** :  
  Appliquer plusieurs **design patterns** (Observer, Factory, State, MVC) afin de produire un code propre, modulable et maintenable. Cela implique une réflexion constante sur l’architecture et la capacité à **s’adapter aux difficultés** rencontrées (ex. création ou suppression de classes inutiles, ajustements suite à l’évolution des besoins).

- **Extensibilité et amélioration continue** :
    - Mettre en place un **moteur de génération ou de configuration de positions** permettant d’ajouter aisément de nouvelles configurations/puzzles.
    - Prévoir des fonctionnalités d’**amélioration de l’interface** (ex. possibilité de revenir en arrière, ajout de boutons, améliorations graphiques…).
    - Faciliter la maintenance à long terme en veillant à ce que chaque module (moteur de jeu, affichage, contrôleur) reste **indépendant** et **extensible**.

- **Gestion de projet et développement en solo** :
    - Acquérir et démontrer la **capacité à piloter un projet** de bout en bout (depuis la phase de conception jusqu’à l’implémentation).
    - Développer un **savoir-faire** en gestion de code (choix de technologies, organisation du référentiel, documentation) et en conduite de projet (gestion du temps, prise de décisions, résolution de problèmes).
### 1.3 Règles du Jeu

Les règles principales de **Solo Chess** sont les suivantes :

1. L’échiquier est initialisé avec plusieurs pièces, placées aléatoirement ou selon une configuration prédéfinie.
2. Chaque pièce peut se déplacer en respectant ses mouvements traditionnels aux échecs.
3. Une pièce ne peut effectuer que **deux mouvements** maximum. Après son deuxième mouvement, elle devient “noire”, immobile et inutilisable.
4. Les pièces peuvent **capturer** d’autres pièces.
5. Le jeu se termine lorsqu’il ne reste plus qu’une pièce :
    - Si un **roi** est présent au départ, il doit être la dernière pièce restante.
    - Sinon, toute pièce restante peut conclure la partie.
6. Si toutes les pièces restantes sont “noires” avant d’en avoir une seule pièce libre à la fin, le joueur perd immédiatement.

### 1.4 Design Patterns Utilisés

Pour structurer ce projet, plusieurs *design patterns* de la programmation orientée objet ont été utilisés.

1. **Observer / Observable**
    - Permet la communication entre le modèle (Board) et les différentes vues (par ex. Renderer).
    - La classe `Board` implémente l’interface `Observable` et notifie les `Observer` (ex. le renderer) lorsqu’il y a un changement d’état (déplacement de pièce, capture, etc.).

2. **Factory Pattern**
    - La classe `PieceFactory` est utilisée pour créer dynamiquement les différentes pièces (Roi, Dame, etc.).
    - Cela facilite l’ajout de nouveaux types de pièces sans modifier la logique principale de création.

3. **State Pattern**
    - Gère les différents états du jeu : en cours (`PlayingState`), en pause (`PausedState`), terminé (`GameOverState`).
    - Chaque état implémente une interface commune (ex. `GameStateInterface`) pour uniformiser leur gestion (mise à jour, rendu).

4. **Model-View-Controller (MVC)**
    - **Modèle (`Board`)** : gère les données et la logique métier (position des pièces, captures…).
    - **Vue (`Renderer`)** : affiche l’état du jeu (pieces, échiquier).
    - **Contrôleur (`Controller`)** : gère les interactions utilisateur, orchestre la communication entre modèle et vue.

---

## Section 2. Présentation du Projet

Cette section présente les aspects techniques du projet, notamment l’utilisation de LibGDX, la gestion de l’affichage, des tuiles et l’organisation globale. *(Vous pourrez enrichir cette partie selon vos besoins : expliquer la configuration Tiled, la gestion des cartes, etc.)*

### 2.1 Technologies et Outils Utilisés

- **LibGDX** : Moteur de jeu pour la 2D, permettant une gestion aisée des sprites, du rendu et des entrées.
- **Tiled** : (si applicable) outil de conception de cartes 2D.
- *Autres* : (Gestion de version GIT, IDE IntelliJ, etc.)

### 2.2 Fonctionnalités Implémentées

- **Déplacements de pièces** : chaque pièce suit ses règles de mouvement propres (roi, dame, tour, fou, cavalier, pion…).
- **Captures** : possibilité de prendre une pièce adverse (ou alliée, selon le puzzle) pour la retirer de l’échiquier.
- **Limitation à deux mouvements** : après deux déplacements, la pièce devient “noire” et ne peut plus bouger.
- **Condition de fin de partie** : vérification en continu si le puzzle est résolu (une seule pièce restante) ou si le joueur est bloqué (toutes les pièces restantes sont noires).

### 2.3 Configuration et Ajout de Contenu (Tiled)

*(Section à développer si vous utilisez Tiled)*

---

## Section 3. Architecture Générale du Moteur de Jeu (Aperçu)

Le jeu est organisé selon une architecture modulaire, exploitant les patterns décrits ci-dessus. Les classes clés incluent :
- **`Point`** : représente les coordonnées sur l’échiquier.
- **`Tile`** (abstraite), dérivée en `EmptyTile` et `OccupiedTile`.
- **`Piece`** (abstraite), dont héritent `King`, `Queen`, `Bishop`, etc.
- **`Board`** : gère les `Tile`, les déplacements, captures, et notifie les observateurs via l’interface `Observable`.
- **`Renderer`** : se charge d’afficher l’échiquier et les pièces.
- **`Controller`** : gère l’entrée utilisateur et valide les actions.
- **`GameStateInterface`** : interface pour les différents états du jeu (`PlayingState`, `PausedState`, `GameOverState`).

*(Vous pouvez inclure un diagramme UML ici si nécessaire.)*

---

## Section 4. Conclusion et Perspectives

- **Bilan** :  
  Le projet **Solo Chess** démontre l’application de concepts clés de la POO et de patterns avancés en Java (LibGDX). Réalisé individuellement, il montre comment créer un jeu extensible et maintenable, même si certaines fonctionnalités (rendu avancé, IA) restent à peaufiner.

- **Perspectives d’Amélioration** :
    1. **Surbrillance des coups valides** pour faciliter l’ergonomie.
    2. **Implémentation d’une IA** permettant de générer des puzzles ou de jouer contre l’ordinateur.
    3. **Mode multijoueur** ou **défis en ligne**.
    4. **Intégration plus poussée avec Tiled**, pour personnaliser encore plus la configuration du plateau.

---

## Section Annexe

- Lien vers le dépôt Git : *(À insérer)*
- Références documentaires LibGDX : <https://libgdx.com/>
- Tutoriels Tiled : <https://doc.mapeditor.org/en/stable/manual/introduction/>

*(Fin du rapport)*
