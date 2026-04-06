CREATE TABLE IF NOT EXISTS categorie (
  id INT PRIMARY KEY,
  nom VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS produit (
  id INT PRIMARY KEY,
  nom VARCHAR(100) NOT NULL,
  prix DOUBLE NOT NULL,
  categorie_id INT,
  FOREIGN KEY (categorie_id) REFERENCES categorie(id)
);
