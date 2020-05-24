# LearningBattle

BISSON Clément BOURDIN Maxence // GROUPE F

## Présentation de LearningBattle

Pour le projet de programmation d’un jeu vidéo ludique il a été décidé de s'inspirer des règles de la bataille navale.

Pour rappel, voici les règles originales du jeu :
Chaque joueur dispose de deux grilles quadrillées de 1 à 10 horizontalement et de A à J verticalement. (une avec ses bateaux, et une avec l'historique de ses attaques sur la grille adverse)
Chaque joueur dispose de "bateaux" de différentes tailles à placer sur sa grille comme il l'entend en le cachant à l'autre joueur.
Le premier joueur à jouer indique au deuxième les coordonnées du point qu'il souhaite attaquer sur la grille de celui-ci. (exemple : Attaque en B5) Le deuxième joueur indique alors si il a "touché" un de ses bateaux, et, le cas échéant, lui indique "dans l'eau".
 Un joueur ayant réussi son attaque rejoue jusqu'à ce qu'il rate son coup. 
Si un bateau voit toutes ses cases "touchées", le joueur attaqué signale alors "touché coulé" lors de l'attaque de la dernière case non-touchée de son bateau. 
Le premier joueur à couler tous les bateaux de son adversaire gagne la partie. 

Pour la création de ce jeu devant être à but ludique mais aussi pédagogique, on a alors pris la décision de modifier les règles de celle-ci.
Intégrer un système de questions/réponses lors des attaques effectuées sur les bateaux sur différentes thématiques éducatives comme le calcul mental, le français (grammaire, orthographe etc..), la culture générale, l'histoire etc... si le joueur répond correctement à la question dans le temps imparti son attaque est comptabilisée et le bateau est touché. 
Si une attaque est "dans l'eau" aucune question n'est posée. 
On a donc une interaction joueur VS machine et non joueur VS joueur.
Le joueur dispose d'un nombre de vies. Il en perd une à chaque fois qu'il répond mal à une question.
Le joueur perd si son nombre de vies tombe à zéro.
Le joueur gagne si il coule tous les bateaux et qu'il a encore au moins une vie.

Pour finir, nous avons pris soin d'intégrer un petit "gage" pour ceux qui s'aventurent dans les règles avant de commencer... 

## Utilisation de LearningBattle

Afin d'utiliser le projet, il est suffisant d'utiliser ces commandes :
./compile.sh
./run.sh Bataille


