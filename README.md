# TP Simple — JPA / EntityManager (CRUD) + DAO

Ce dépôt contient une **correction guidée** du TP “Découverte de `EntityManager` et JPA” (table `produit`) dans le module Maven `demo/`.

L’objectif est de comprendre comment **JPA + EntityManager + persistence.xml + DAO** forment un écosystème simple mais **robuste** pour faire du CRUD (Create/Read/Update/Delete).

## Prérequis

- **Java 17**
- **Maven**
- **MySQL** (en local dans un premier temps)

> Une solution MySQL via Docker est fournie, mais elle est **optionnelle** et décrite plus bas.

## Structure (module `demo/`)

- **Entité** : `demo/src/main/java/com/example/tp/j2ee/Produit.java`
- **DAO** :
  - `demo/src/main/java/com/example/tp/j2ee/ProduitDAO.java`
  - `demo/src/main/java/com/example/tp/j2ee/ProduitDAOImpl.java`
- **Point d’entrée** : `demo/src/main/java/com/example/tp/j2ee/Main.java`
- **Config JPA** : `demo/src/main/resources/META-INF/persistence.xml`

## Petite conception de l’application (TP)

### Vue d’ensemble (responsabilités)

- **`Main` (couche “application”)** : initialise l’infrastructure JPA (`EntityManagerFactory`, `EntityManager`), instancie le DAO, puis exécute un petit scénario (CRUD).
- **`Produit` (couche “domaine / modèle”)** : représente un produit en Java, mappé sur la table `produit`.
- **`ProduitDAO` (contrat)** : définit les opérations CRUD que l’application attend, sans dépendre des détails JPA.
- **`ProduitDAOImpl` (couche “accès données”)** : implémente le contrat via `EntityManager`, et **encapsule les transactions** d’écriture.
- **MySQL (couche “persistance”)** : stocke réellement les lignes de la table `produit`.

### Schéma simple (architecture)

```text
Main
  |
  v
ProduitDAO  <----- contrat
  |
  v
ProduitDAOImpl  -----> EntityManager <---- EntityManagerFactory <---- persistence.xml (magasinPU)
  |
  v
MySQL (DB magasin / table produit)
```

### Flux d’exécution (exemple CRUD)

1. `Main` crée `EntityManagerFactory` via le nom **`magasinPU`** (défini dans `persistence.xml`).
2. `Main` crée un `EntityManager` (contexte de persistance).
3. `Main` instancie `ProduitDAOImpl(em)`.
4. **Create** : `dao.create(p)` → `begin` → `persist` → `commit`
5. **Read** : `dao.findById(id)` / `dao.findAll()` → `find` / JPQL (pas de transaction obligatoire en lecture)
6. **Update** : `dao.updatePrice(id, prix)` → `begin` → `find` → modification objet *managed* → `commit`
7. **Delete** : `dao.deleteById(id)` → `begin` → `find` → `remove` → `commit`
8. `Main` ferme `EntityManager` puis `EntityManagerFactory`.

Cette séparation (application / DAO / modèle) rend le TP plus **propre** : `Main` reste lisible, et la logique d’accès aux données (transactions incluses) est au bon endroit.

## Correction du TP — step by step (sans Docker)

### 1) Créer la base de données et la table

Exécute ces commandes dans MySQL :

```sql
CREATE DATABASE magasin;
USE magasin;
CREATE TABLE produit (
  id INT PRIMARY KEY,
  nom VARCHAR(100) NOT NULL,
  prix DOUBLE NOT NULL
);
```

### 2) Créer l’entité `Produit`

Le rôle de l’entité est de **mapper** la table `produit` en objet Java.

- `@Entity` : déclare une entité JPA
- `@Table(name="produit")` : nom réel de la table
- `@Id` : clé primaire
- **Constructeur vide** : requis par JPA

Fichier : `demo/src/main/java/com/example/tp/j2ee/Produit.java`

### 3) Configurer JPA avec `persistence.xml`

Le fichier `persistence.xml` est le “point de configuration” de JPA :

- **nom de l’unité de persistance** : `magasinPU`
- **type de transactions** : `RESOURCE_LOCAL` (on gère les transactions en Java via `EntityManager`)
- **connexion JDBC** : driver, URL, user, password
- **options Hibernate** : `hibernate.show_sql=true` (affiche les SQL générés)

Fichier : `demo/src/main/resources/META-INF/persistence.xml`

> Si ton MySQL local n’utilise pas `root` / mot de passe vide, modifie `jakarta.persistence.jdbc.user` et `jakarta.persistence.jdbc.password`.

### 4) Comprendre le rôle de `EntityManager`

`EntityManager` est l’objet central de JPA :

- **persist** : insérer un objet (Create)
- **find** : récupérer par identifiant (Read)
- **createQuery (JPQL)** : faire des requêtes orientées *entités* (Read)
- **remove** : supprimer (Delete)
- **transactions** : `begin()` / `commit()` / `rollback()`

Point important : une fois récupéré avec `find()`, l’objet est en général **managed** (attaché au contexte de persistance).
Si tu modifies un objet managed **avant** `commit()`, JPA détecte les changements et exécute l’UPDATE correspondant.

### 5) Décharger `Main` avec un DAO

Le pattern **DAO (Data Access Object)** sert à :

- **isoler** l’accès aux données (SQL/JPA) du reste de l’application
- **centraliser** les transactions et la gestion d’erreurs
- rendre le code plus **lisible**, **testable** et **maintenable**

Ici :

- `ProduitDAO` définit le “contrat” (CRUD) : `create`, `findById`, `findAll`, `updatePrice`, `deleteById`
- `ProduitDAOImpl` contient l’implémentation JPA (JPQL + `EntityManager`)
- `ProduitDAOImpl` exécute les écritures dans une méthode utilitaire `inTransaction(...)` pour garantir :
  - `commit()` si tout va bien
  - `rollback()` en cas d’exception

### 6) `Main` devient un scénario simple

`Main` ne fait plus le CRUD “à la main” : il **instancie** `EntityManagerFactory`, `EntityManager`, le DAO, puis appelle des méthodes métier simples.

Fichier : `demo/src/main/java/com/example/tp/j2ee/Main.java`

### 7) Compiler et exécuter

Compiler :

```bash
mvn -f "demo/pom.xml" -DskipTests package
```

Exécuter :

```bash
mvn -f "demo/pom.xml" -Dexec.mainClass=com.example.tp.j2ee.Main org.codehaus.mojo:exec-maven-plugin:3.5.0:java
```

> Attention : le scénario utilise des `id` fixes (1 et 2). Si tu relances sans nettoyer la table, tu peux avoir une erreur de clé primaire. Solution simple : `TRUNCATE TABLE produit;`.

## Comment tout s’assemble (l’“écosystème”)

- **`persistence.xml`** fournit les paramètres nécessaires à JPA pour créer l’unité `magasinPU`.
- **`EntityManagerFactory`** est créé une fois (coûteux) via `Persistence.createEntityManagerFactory("magasinPU")`.
- **`EntityManager`** est créé à partir de la factory et sert à toutes les opérations JPA (persist/find/JPQL/remove).
- **DAO** encapsule les opérations et les transactions : le reste de l’application n’a pas besoin de connaître les détails JPA.

Résultat : un code **fonctionnel**, **séparé par responsabilités**, et plus **robuste** (transactions/rollback gérés au bon endroit).

## (Optionnel) Lancer MySQL via Docker

Si tu préfères éviter une installation locale, un `docker-compose.yml` est fourni dans `demo/`.

- Démarrer :

```bash
docker compose -f "demo/docker-compose.yml" up -d
```

- La base `magasin` + la table `produit` sont créées automatiquement via :
  - `demo/docker/mysql-init/01-magasin.sql`

Si tu utilises Docker, vérifie que `persistence.xml` pointe vers le bon user/password et le bon port.
