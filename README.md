# PocketImperium 🚀

## Description 📌
Ce projet a été réalisé dans le cadre de l'UE LO02 à l'Université de Technologie de Troyes.
Pocket Imperium est un jeu de société de conquête spatiale, qui mélange des éléments de stratégie, d’attaques et d'exploration. Il est conçu pour 3 joueurs, où chacun d’eux cherche à protéger ses groupements de vaisseaux en cherchant à étendre son pouvoir.

Une implémentation garphique permet au joueur de joueur plus facilement au jeu.

## Lancer le jeu 🏁 :
La classe à lancer pour le programme est la classe Partie

## Organisation du projet 🎯 :
- Dossier src : classes en .java
- Dossier bin : classes compilées en .class
- Dossier AUTRES RESSOURCES : Ensembles des assets dont a besoin le projet pour fonctionner (à ne pas changer de place)

## Technologies utilisées 🛠
Java : Moteur du jeu

## Auteurs 👥
Mazuet Maya
Musset Aurélien

## État actuel du projet ✨:
### Ce qui a été implémenté et qui fonctionne :
- Interface graphique pour la majorité de l’application
    L’exploration et l’attaque se font par ligne de commande une fois le premier hexagone sélectionné par interface. (une explication de comment repérer les zones est disponible dans le fichier implementation_map.pdf du dossier doc)
- Choix du pseudo des joueurs réels et de la stratégie des joueurs virtuels
- Plateau aléatoire
- Choix des ordres
- Sauvegarde (entre les tours) et chargement (dans le menu du début) de parties
- Affichage dynamique du numéro de tour actuel et de la phase actuelle
- Affichage dynamique du nombre de vaisseaux de chaque joueur
- Affichage dynamique des points de chaque joueur
- Sélection des secteurs et hexagones lorsque cela est nécessaire
- Fin du jeu (dernier scoring avec les points doublés et affichage du classement)
- Possibilité de relancer une partie depuis la fenêtre de classement
- Possibilité d’ouvrir un PDF listant les sources utilisées et les droits d’exploitation lié au projet

### Ce qui reste bogué :
- L’exploration, si on décide de déplacer plusieurs vaisseaux
- L’attaque peut être bogué

## 📜 Licence  
Ce projet est protégé par des droits d’auteur. Toute utilisation, modification, reproduction ou distribution sans autorisation est interdite.  

🔗 Consultez le fichier [LICENCE.txt](LICENCE.txt) pour plus de détails.  

