# Github

NOTE : ne pas mettre le projet public si on ne veut pas que toute la terre le voit
NOTE : ne pas forker depuis le repo FIRST, mais cloner, puis track la remote
NOTE : la methode ci-dessous est la méthodologie à suivre pour faire une configuration type linux
la methode windows utilise le Credential Manager et il semble que ce soit ce que vous avez deja
utilisé pour vous connecté à votre espace robolyon

## se creer un compte perso

se creer un compte github avec sa vrai addresse
attention ce compte pourra vous suivre toute votre vie, donc ne pas mettre trop de betises

## cree un repo github prive

https://github.com/new

## utiliser les clés ssh

la clé ssh permet de s'authenfier sur github, sans avoir besoin d'un github desktop, ou d'un
identifiant/mot de passse

1. generation de la clé : `ssh-keygen -t ed25519 -C "your_email@example.com"`
   source https://docs.github.com/en/authentication/connecting-to-github-with-ssh/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent

2. mise en place de l'agent via le fichier `~/.profile`
   source https://docs.github.com/en/authentication/connecting-to-github-with-ssh/working-with-ssh-key-passphrases

`notepad ~/.profile` et copier dedans

```
env=~/.ssh/agent.env

agent_load_env () { test -f "$env" && . "$env" >| /dev/null ; }

agent_start () {
(umask 077; ssh-agent >| "$env")
. "$env" >| /dev/null ; }

agent_load_env

# agent_run_state: 0=agent running w/ key; 1=agent w/o key; 2=agent not running

agent_run_state=$(ssh-add -l >| /dev/null 2>&1; echo $?)

if [ ! "$SSH_AUTH_SOCK" ] || [ $agent_run_state = 2 ]; then
agent_start
ssh-add
elif [ "$SSH_AUTH_SOCK" ] && [ $agent_run_state = 1 ]; then
ssh-add
fi

unset env
```

3. ajout de sa clé publique dans github
   source https://docs.github.com/en/authentication/connecting-to-github-with-ssh/adding-a-new-ssh-key-to-your-github-account

- le but étant de pouvoir se connecter à notre compte github via un échange de clé (transparent et
  en arriere plan) plutot qu'un login/mdp
- aller sur `https://github.com/settings/profile` puis `SSH and GPG keys` puis `New SSH key`
- coller le contenu de la clé publique : `cat ~/.ssh/id_ed25519.pub`

## parametrer son repo

- https://github.com/chtibob69/FtcRobotController/settings
- pas de merge commit, que des rebase merging (pour le code TeamCode)
    - mais pour la mise à jour SDK, preferer les merge commit
- ? mettre le repo en forward merge: git config --global merge.ff only ?
- branches, protection rule (require merge request, linear history)
- collaborators : ajouter les collabs

## clone local

- se positionner sur notre systeme de fichier ou l'on souhaite (idealement `~/dev`:

```
mkdir ~/dev
cd ~/dev
```

- depuis le repo github, vue <Code> aller sur le bouton vert `<>Code`, choisir `SSH` et copier le
  lien proposé (exemple: `git@github.com:chtibob69/FtcRobotController.git`)
- cloner le repo github en local: `git clone git@github.com:chtibob69/FtcRobotController.git`

- aller ensuite sur le repo `cd <repo>`
- `git lg1`

## Branche principale

L'équipe doit définir la branche qui sera LA branche de référence
Classiquement il s'agit de `master` ou `main`
Cette branche devrait etre protégée (pas de push force, que des MR, ...)

## Tag

Quand une version du projet nous convient, on doit tagger
C'est à dire marqué un commit (donc un moment particulier de la branche) pour s'en rappeler
Classiquement un tag est un numero de version `v1.0`
Mais ça pourrait aussi etre `vClermont` ou `vLyon` pour se rappeler de ce qu'on a utilisé lors des
compet Clermont/Lyon
`git tag -a v1.0 -m 'version blabla'`
`git push origin v1.0`
Cela permet de revenir facilement le jour d'une competition à une version précise du code
`git checkout v1.0` (bien lire les messages)

Lister les tags :
`git fetch --tags`
`git tag` ou `git tag -l "v1.*"`

## Branche de travail

- ensuite chacun doit travailler sur sa branche locale :
    - `git checkout -b intake --track origin/main`
        - cree la branche locale intake à partir de la branche actuelle
        - traque (suivre) ce qui se passe sur `main` afin de savoir si qqlq a rajouté des choses sur
          la branche `main`
        - un `git fetch` permet de savoir ou on est par rapport à `main` puisqu'on la traque
    - `git status` permet de savoir ce qu'on a modifié depuis le dernier commit
    - `git add .` puis `git commit -m 'message'` pour cree un commit
    - `git push` pour le mettre sur github (mais pas sur `main`)
        - normalement, quand on pousse via la ligne de commande on a un lien pour ouvrir une MR en
          retour
        - c'est cette MR qui se chargera de merge sur la branche voulue (`main`)
        - sinon via l'interface github, on va dans `Pull Request` et on en cree une, en paramétrant
          bien la branche source et cible

## Travail en equipe

- si nath fait l'intake, et raf le feeder, il faudra mettre en commun (sur `main`) à moment
- RAPPEL : on pourrait direct mergé sur main, mais ce n'est pas une bonne pratique
    - on ne devrait pas avoir le droit de poussé dessus
    - on zap l'étape de relecture
- ici on a donc 2 MR (raf et nath)
- du coup si on merge raf en premier, nath ne pourra plus etre mergé en l'état (puisqu'il est en
  retard par rapport à main qui contiendra le commit de raf)
- nath devra donc `rebase` : c'est à dire intégrer le commit de raf AVANT le sien :
- `git pull --rebase origin/main`
    - il se peut qu'il y ait des conflits, à résoudre via l'IDE
- puis terminé le rebase avec `git rebase --continue`
- verifier via `git lg1`
    - on verra donc le commit de raf AVANT celui de nath
- à partir de là nath doit re-pousser sa branche `git push --force` (-f pour force car on a re-ecrit
  l'historique en ajoutant le commit de raf),
- sa MR pourra maintenant etre aussi merge
- NOTE : une fois merge, les branches locale (et distante) de travail ne sont normalement plus utile
    - la branche distante devrait etre suppr auto par la mr (voir checkbox)
    - la branche locale est à suppr `git branch -d <branch>`
- si on doit travailler sur un nouveau truc : on repart à l'étape `Branche de travail`

## Se baser sur le repo ftc (SDK))

Lors de l'init du projet, il faut se baser sur le SDK fourni

-

fork ? https://ftc-docs.firstinspires.org/en/latest/programming_resources/tutorial_specific/android_studio/fork_and_clone_github_repository/Fork-and-Clone-From-GitHub.html ->
bof car pas de projet privé possible si on fork

- rajout de la remote ftc sur un projet vide pour les mise à jour du sdk :
    - `git remote add ftc https://github.com/FIRST-Tech-Challenge/FtcRobotController.git`
    - fetch : `git fetch ftc`
    - recupere en local le contenu de la branch master sur la remote ftc :
        - en clair, récuperer en local le sdk
        - `git checkout -b ftclocal ftc/master`
        - pousse sur notre origin : `git push ftclocal origin main`
- mise à jour sdk
    - le sdk se met à jour regulierement, et il faut traquer cela
    - `git fetch ftc`
    - si des nouveautés sont là, se remetre sur `main` et `git pull ftc`
    - poussé les changements :
        - version bourrin : `git push ftclocal origin main -f`
            - NOTE: le -f (force) est tres dangereux car re-ecrit l'historique
        - version soft : `git merge --no-ff main` puis `git push` de la branche main
