# Encheres

Application Web d'enchères de biens d'occasion utilisant un système de points.

## Fonctionnalités

- Consultation des ventes
- Mise en vente d'un article
- Participation aux enchères via un compte à points
- Authentification et gestion des utilisateurs avec Spring Security

## Prérequis

- JDK 17
- Base de données SQL Server (voir `application.properties`)

## Lancement de l'application

```bash
./gradlew bootRun
```

Les tests se lancent avec :

```bash
./gradlew test
```

## Structure du projet

```
src/
  main/
    java/fr/eni/tp/encheres/      # code source principal
    resources/                    # configuration et templates
  test/                           # tests unitaires
```

Le projet suit l'architecture Spring Boot et est organisé par couches :
- `bo` : objets métiers
- `dal` : accès aux données
- `bll` : logique métier
- `controller` : contrôleurs web
- `dto` : objets de transfert
- `security` et `configuration` : sécurité et configuration
