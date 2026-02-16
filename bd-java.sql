-- =====================================================
-- SCRIPT DE CORRECTION BASE DE DONNÉES
-- GestionRestaurant
-- =====================================================

-- Supprimer l'ancienne base si elle existe (ATTENTION : perte de données)
DROP DATABASE IF EXISTS GestionRestaurant;

-- Créer la base de données
CREATE DATABASE GestionRestaurant;
USE GestionRestaurant;

-- =====================================================
-- TABLE UTILISATEUR (email au lieu de login)
-- =====================================================
CREATE TABLE Utilisateur(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- =====================================================
-- TABLE CATEGORIE
-- =====================================================
CREATE TABLE Categorie(
    idCat VARCHAR(36) NOT NULL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);

-- =====================================================
-- TABLE PRODUIT
-- =====================================================
CREATE TABLE Produit(
    idProd VARCHAR(36) NOT NULL PRIMARY KEY,
    nom VARCHAR(150) NOT NULL,
    prixDeVente DOUBLE NOT NULL CHECK(prixDeVente > 0),
    stockActuel INT NOT NULL CHECK(stockActuel >= 0),
    seuilAlerte INT NOT NULL CHECK(seuilAlerte >= 0),
    idCat VARCHAR(36) NOT NULL,
    FOREIGN KEY (idCat) REFERENCES Categorie(idCat) ON DELETE RESTRICT
);

-- =====================================================
-- TABLE COMMANDE (Correction: ANNULEE au lieu de ANULEE)
-- =====================================================
CREATE TABLE Commande(
    idCom VARCHAR(36) NOT NULL PRIMARY KEY,
    dateCommande DATETIME DEFAULT NOW(),
    etat ENUM('EN_COURS', 'VALIDEE', 'ANNULEE') NOT NULL DEFAULT 'EN_COURS',
    total DOUBLE NOT NULL DEFAULT 0 CHECK(total >= 0)
);

-- =====================================================
-- TABLE LIGNE COMMANDE
-- =====================================================
CREATE TABLE LigneCommande(
    idCom VARCHAR(36) NOT NULL,
    idProd VARCHAR(36) NOT NULL,
    quantite INT NOT NULL CHECK(quantite > 0),
    prixUnitaire DOUBLE NOT NULL CHECK(prixUnitaire > 0),
    montantLigne DOUBLE GENERATED ALWAYS AS (quantite * prixUnitaire) STORED,
    PRIMARY KEY (idCom, idProd),
    FOREIGN KEY (idCom) REFERENCES Commande(idCom) ON DELETE CASCADE,
    FOREIGN KEY (idProd) REFERENCES Produit(idProd) ON DELETE RESTRICT
);

-- =====================================================
-- TABLE MOUVEMENT STOCK
-- =====================================================
CREATE TABLE MouvementStock(
    idMouv VARCHAR(36) NOT NULL PRIMARY KEY,
    typeMouv ENUM('ENTREE', 'SORTIE') NOT NULL,
    idProd VARCHAR(36) NOT NULL,
    quantite INT NOT NULL CHECK(quantite > 0),
    dateMouv DATETIME DEFAULT NOW(),
    motif VARCHAR(500),
    FOREIGN KEY (idProd) REFERENCES Produit(idProd) ON DELETE RESTRICT
);

-- =====================================================
-- DONNÉES DE TEST
-- =====================================================

-- Utilisateur test
INSERT INTO Utilisateur (id, email, password) VALUES 
('user-001', 'admin@iai.tg', 'admin123'),
('user-002', 'gerant@iai.tg', 'gerant123');

-- Catégories
INSERT INTO Categorie (idCat, libelle) VALUES 
('cat-001', 'Boissons'),
('cat-002', 'Plats'),
('cat-003', 'Desserts'),
('cat-004', 'Entrées');

-- Produits
INSERT INTO Produit (idProd, nom, prixDeVente, stockActuel, seuilAlerte, idCat) VALUES 
('prod-001', 'Coca-Cola', 500, 50, 10, 'cat-001'),
('prod-002', 'Eau Minérale', 300, 100, 20, 'cat-001'),
('prod-003', 'Riz Sauce Arachide', 1500, 30, 5, 'cat-002'),
('prod-004', 'Poulet Braisé', 2000, 20, 5, 'cat-002'),
('prod-005', 'Salade Composée', 800, 15, 3, 'cat-004'),
('prod-006', 'Gâteau Chocolat', 1200, 10, 2, 'cat-003');

-- Commandes de test
INSERT INTO Commande (idCom, dateCommande, etat, total) VALUES 
('cmd-001', '2026-02-12 10:30:00', 'VALIDEE', 4500),
('cmd-002', '2026-02-12 12:15:00', 'EN_COURS', 0);

-- Lignes de commande
INSERT INTO LigneCommande (idCom, idProd, quantite, prixUnitaire) VALUES 
('cmd-001', 'prod-003', 2, 1500),
('cmd-001', 'prod-001', 3, 500);

-- Mouvements de stock
INSERT INTO MouvementStock (idMouv, typeMouv, idProd, quantite, dateMouv, motif) VALUES 
('mouv-001', 'ENTREE', 'prod-001', 50, '2026-02-01 08:00:00', 'Achat initial'),
('mouv-002', 'ENTREE', 'prod-002', 100, '2026-02-01 08:00:00', 'Achat initial'),
('mouv-003', 'SORTIE', 'prod-003', 5, '2026-02-10 14:00:00', 'Perte - produit périmé');

-- =====================================================
-- VUES UTILES (OPTIONNEL)
-- =====================================================

-- Vue des produits avec leur catégorie
CREATE VIEW v_produits_details AS
SELECT 
    p.idProd,
    p.nom,
    p.prixDeVente,
    p.stockActuel,
    p.seuilAlerte,
    c.libelle AS categorie,
    CASE 
        WHEN p.stockActuel <= p.seuilAlerte THEN 'ALERTE'
        WHEN p.stockActuel = 0 THEN 'RUPTURE'
        ELSE 'OK'
    END AS statutStock
FROM Produit p
JOIN Categorie c ON p.idCat = c.idCat;

-- Vue du chiffre d'affaires par jour
CREATE VIEW v_ca_par_jour AS
SELECT 
    DATE(dateCommande) AS date,
    COUNT(*) AS nbCommandes,
    SUM(total) AS chiffreAffaires
FROM Commande
WHERE etat = 'VALIDEE'
GROUP BY DATE(dateCommande)
ORDER BY date DESC;

