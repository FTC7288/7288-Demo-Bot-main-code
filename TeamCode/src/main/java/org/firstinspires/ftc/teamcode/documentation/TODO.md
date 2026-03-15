A faire avec Raf :

- rappel github
- cree Intake, Feeder, Shooter, Viseur, Gyro, Vision en se basant sur le Driver
- cree une classe Robot qui les encapsulent tous
- declarer/utiliser dans le EulerTeleop
- jouer ensuite avec le mode autonome (avec plusieurs position de démarrage/imu/cam....)

# Git / Github

Le projet actuel contient deja 2 remotes github (`git remote -vv`)

- origin = le repo github robolyon
- upstream = le repo ftc pour le sdk
- j'ai rajouté ma remote (chtibob69) sur le projet (=mon projet robot sur github)
  -> `git push origin` pour poussé la branch actuelle sur le github de robolyon
- il pourrait etre bien de rajouter votre repo perso si jamais vous voulez sauvez ce travail plus
  loin que robolyon
    - donc cree un projet sur votre github perso (decode2025 par exemple)
    - rajouter la remote
    - pousse dessus

Pour passé d'une branche à une autre :

- depuis l'IDE (android studio), en haut (marqué `nath`), on deroule le menu, et on voit `seb` puis
  checkout et inversement
- en ligne de commande
    - `git checkout seb` ou `git checkout nath` (si les branches existent deja)
    - `git checkout -b <nom branche locale voulue> <remote>/<nom branche distance>`
- ATTENTION : il faut avoir un repertoire de travail sans modif pour pouvoir changé de branche (cf
  la vue commit ou `git status`)

# Branche `main`

Une bonne chose serait de poussé la branche `nath` actuelle en tant que `main` (en gros un autre nom
pour master)

- `git checkout -b main` : cree une branche nommée `main` depuis la branche courante (nath)
- `git push origin` : pour envoyer sur github une nouvelle branche (main est ici sous-entendu)
- ainsi raf de son cote pourra fetch le projet (`git fetch origin`) puis checkout `main` aussi de
  son coté `git checkout -b main origin/main`
