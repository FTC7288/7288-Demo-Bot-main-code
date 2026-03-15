# Git

## installation windows

installer git bash pour windows (recherche sur google)
dans un terminal lancé `git --version`

## notions et commandes git

- dépôt (repository) : C'est l'ensemble des fichiers de votre projet et l'historique complet de
  toutes les modifications.
- `git init`: initialiser un repository
- `git status`: etat de notre espace de travail (repository) ; des fichiers ont-ils été modifiés
  depuis le dernier commit
- `git add monFichier` ou `git add .`: ajouter un (ou tous les) fichier(s) au context git
- `git commit -m '<message>'`: enregistrer les modifications
- notion de `remote` : gerer les dépôts distants:
    - Un "remote" (ou dépôt distant) est une référence à une copie de votre dépôt (local) qui est
      hébergée sur un autre ordinateur ou un serveur comme GitHub.
    - `git remote -vv`: liste des remotes
    - `git remote add <name> <url>`: ajouter une remote
    - `git remote rm <name>`: supprimer une remote
- `git fetch`: récupère les dernières modifications depuis le dépôt distant sans les fusionner avec
  votre branche locale
- `git pull`: récupère les dernières modifications depuis le dépôt distant et les fusionne
  automatiquement avec votre branche locale.
    - au final c'est un raccourci `git pull` = `git fetch` + `git merge` (or `git rebase`)
- `git push`: envoie des commits locaux vers le dépôt distant (synchro ascendante)
- `git merge`: fusionne les modifications locales avec les modifications du dépôt distant (synchro
  descendante)
    - `git merge --abort`: Annule une fusion en cours qui a provoqué des conflits.
    - `git merge --no-ff <branch>` : permet de merger avec un commit de merge (no fast-forward)
- notion de `branch` :
    - Une branche représente une ligne de développement indépendante.
      Elle vous permet de travailler sur une nouvelle fonctionnalité ou de corriger un bug sans
      affecter la branche principale
      (généralement main ou master).
      Techniquement, une branche est simplement un pointeur mobile vers l'un de vos commits.
    - `git branch`: liste les branches locales
    - `git branch -a`: liste les branches locales et distantes
    - `git branch -vv`: Affiche la liste de vos branches locales avec des informations détaillées, y
      compris la branche distante qu'elles suivent.
    - `git branch <name>`: crée une nouvelle branche locale
    - `git branch -d <name>` ou `git branch -D <name>` : supprime une branche locale
- notion de `checkout`:
    - "Je veux que mon répertoire de travail se retrouve dans l'état x"
    - `git checkout <branch>`: change de branche locale vers une autre branche locale existante
    - `git checkout -b <new branch> --track origin/master`: Crée une nouvelle branche locale qui
      suit une branche distante.

## alias git

- ajouter un alias en cli: `git alias <alias>='<commande>'` -> `git alias st=status`
- alias utile à rajouter dans `~/.gitconfig`, section `[alias]`
  `notepad ~/.gitconfig`
  ```
  [alias]
    lg = lg1
    lg1 = lg1-specific --all
    lg2 = lg2-specific --all
    lg3 = lg3-specific --all

    lg1-specific = log --graph --abbrev-commit --decorate --format=format:'%C(bold blue)%h%C(reset) - %C(bold green)(%ar)%C(reset) %C(white)%s%C(reset) %C(dim white)- %an%C(reset)%C(auto)%d%C(reset)'
    lg2-specific = log --graph --abbrev-commit --decorate --format=format:'%C(bold blue)%h%C(reset) - %C(bold cyan)%aD%C(reset) %C(bold green)(%ar)%C(reset)%C(auto)%d%C(reset)%n''          %C(white)%s%C(reset) %C(dim white)- %an%C(reset)'
    lg3-specific = log --graph --abbrev-commit --decorate --format=format:'%C(bold blue)%h%C(reset) - %C(bold cyan)%aD%C(reset) %C(bold green)(%ar)%C(reset) %C(bold cyan)(committed: %cD)%C(reset) %C(auto)%d%C(reset)%n''          %C(white)%s%C(reset)%n''          %C(dim white)- %an <%ae> %C(reset) %C(dim white)(committer: %cn <%ce>)%C(reset)'
	co = checkout
	br = branch
	ci = commit
	st = status
  ```

## creer une branche de travail

- toujours dans `~/dev/<monrepo>`
- noter que le terminal affiche la branche en cours, sinon l'afficher via `git branch`
- creer une branche local nommée `work`: `git checkout -b work`
- lors du premier push, il faudra spécifier la remote (branche distante ou poussé):
  `git push -u origin work`
- Advanced :
    - creer la branche `main` et track `origin/main` : `git checkout -b main origin/main`
    - MAIS: il devrait etre interdit de travailler/pousser sur `main`
    - la branche local `main` ne servira normalement plus
    - on va se creer, à partir de `main` une branche de travail `intake` : `git checkout -b intake`
        - comme on était sur main avant, `main` et `intake` sont donc à ce moment identique
    - pour se mettre à jour, lancer `git pull --rebase origin/main` et gérer les conflits
    - par defaut, un `git push` poussera sur la branche du meme nom que local
    - si besoin on peut specifier : `git push <remote> <branch-local>:<branch-distante>`

## Tuning du power shell

- install power shell 7 : winget install Microsoft.PowerShell
    - install oh-my-posh :
        - winget install JanDeDobbeleer.OhMyPosh -s winget
        - oh-my-posh font install
        - New-Item -Path "C:\oh-my-posh-themes" -ItemType Directory
        - Invoke-WebRequest
          -Uri "https://github.com/JanDeDobbeleer/oh-my-posh/releases/latest/download/themes.zip"
          -OutFile "$env:USERPROFILE\themes.zip"
        - Expand-Archive -Path "$env:USERPROFILE\themes.zip" -DestinationPath "C:\oh-my-posh-themes"
          -Force
        - notepad $PROFILE
        - $env:POSH_THEMES_PATH = "C:\oh-my-posh-themes"
          oh-my-posh init pwsh --config "$env:POSH_THEMES_PATH\jandedobbeleer.omp.json" |
          Invoke-Expression
        - themes :
            - https://ohmyposh.dev/docs/themes
            - pour tester un theme :
              `oh-my-posh init pwsh --config "C:\oh-my-posh-themes\robbyrussell.omp.json" | Invoke-Expression`
            - up arrow
                - `notepad $PROFILE`
                - ```
          # Use Up/Down arrows to search through history based on what you've already typed
          Set-PSReadLineKeyHandler -Key UpArrow -Function HistorySearchBackward
          Set-PSReadLineKeyHandler -Key DownArrow -Function HistorySearchForward*
          ```
