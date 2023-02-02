-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 02 fév. 2023 à 16:57
-- Version du serveur : 10.4.27-MariaDB
-- Version de PHP : 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `dfc_projet`
--

-- --------------------------------------------------------

--
-- Structure de la table `avenant_compte_mar_con_bc`
--

CREATE TABLE `avenant_compte_mar_con_bc` (
  `ID` int(11) NOT NULL,
  `ID_MAR_CON_BC` int(11) NOT NULL,
  `NUMERO` varchar(100) NOT NULL,
  `DATE` date NOT NULL,
  `COMPTE_NUMERO` varchar(20) NOT NULL,
  `AGENCE` varchar(50) NOT NULL,
  `BANK` varchar(50) NOT NULL,
  `CREE_LE` datetime NOT NULL DEFAULT current_timestamp(),
  `UPDATE_LE` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `avenant_compte_mar_con_bc`
--

INSERT INTO `avenant_compte_mar_con_bc` (`ID`, `ID_MAR_CON_BC`, `NUMERO`, `DATE`, `COMPTE_NUMERO`, `AGENCE`, `BANK`, `CREE_LE`, `UPDATE_LE`) VALUES
(12, 3, '001/2023', '2023-01-29', '00112233665544', 'ALG', 'BNA', '2023-01-29 14:58:00', '2023-01-29 14:58:00'),
(13, 3, '002/2023', '2023-01-01', '111233333', 'ALG', 'BNA', '2023-01-30 08:27:30', '2023-01-30 10:07:17');

-- --------------------------------------------------------

--
-- Structure de la table `avenant_cout`
--

CREATE TABLE `avenant_cout` (
  `ID` int(11) NOT NULL,
  `ID_COUT` int(11) NOT NULL,
  `DATE` date NOT NULL,
  `TYPE` varchar(100) NOT NULL,
  `MONTANT` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `avenant_cout`
--

INSERT INTO `avenant_cout` (`ID`, `ID_COUT`, `DATE`, `TYPE`, `MONTANT`) VALUES
(6, 9, '2023-01-02', 'SUPLEMENTAIRE', 105335244.82),
(7, 9, '2022-01-18', 'SUPLEMENTAIRE', 27112307.29),
(16, 10, '2023-01-19', 'SUPLEMENTAIRE', 12000),
(22, 11, '2023-01-19', 'SUPLEMENTAIRE', 12000),
(24, 7, '2023-01-23', 'SUPLEMENTAIRE', 12000000);

-- --------------------------------------------------------

--
-- Structure de la table `avenant_mar_con_bc`
--

CREATE TABLE `avenant_mar_con_bc` (
  `ID` int(11) NOT NULL,
  `ID_MAR_CON_BC` int(11) NOT NULL,
  `TYPE` varchar(100) NOT NULL,
  `DATE` date NOT NULL,
  `MONTANT` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `avenant_mar_con_bc`
--

INSERT INTO `avenant_mar_con_bc` (`ID`, `ID_MAR_CON_BC`, `TYPE`, `DATE`, `MONTANT`) VALUES
(4, 3, 'SUPLEMENTAIRE', '2022-10-13', 1974777.66),
(5, 4, 'SUPLEMENTAIRE', '2022-11-24', 980877.03),
(7, 3, 'SUPLEMENTAIRE', '2022-10-07', 120000),
(9, 3, 'SUPLEMENTAIRE', '2022-10-17', 120000);

-- --------------------------------------------------------

--
-- Structure de la table `cout`
--

CREATE TABLE `cout` (
  `ID` int(11) NOT NULL,
  `ID_PROJET` int(11) NOT NULL,
  `TYPE` varchar(100) NOT NULL,
  `MONTANT` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `cout`
--

INSERT INTO `cout` (`ID`, `ID_PROJET`, `TYPE`, `MONTANT`) VALUES
(7, 4, 'REALISATION', 923618599.99),
(8, 4, 'ETUDE', 23815928),
(9, 4, 'VRD', 42000000),
(10, 5, 'REALISATION', 0),
(11, 5, 'ETUDE', 12000),
(12, 5, 'VRD', 0),
(13, 6, 'REALISATION', 0),
(14, 6, 'ETUDE', 0),
(15, 6, 'VRD', 0);

-- --------------------------------------------------------

--
-- Structure de la table `facture`
--

CREATE TABLE `facture` (
  `ID` int(11) NOT NULL,
  `ID_MAR_CON_BC` int(11) NOT NULL,
  `NUMERO` varchar(100) NOT NULL,
  `DATE` date NOT NULL,
  `MONTANT` double NOT NULL,
  `ARCHIVE` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `facture`
--

INSERT INTO `facture` (`ID`, `ID_MAR_CON_BC`, `NUMERO`, `DATE`, `MONTANT`, `ARCHIVE`) VALUES
(5, 3, '01/14', '2014-05-25', 6093742.34, 0),
(6, 3, '02/14', '2014-06-25', 9913260.57, 0),
(7, 3, '03/14', '2014-07-24', 6837967.68, 0),
(8, 3, '04/14', '2014-12-12', 12549836.23, 0),
(9, 4, '001/2014', '2014-01-01', 1245478963, 1);

-- --------------------------------------------------------

--
-- Structure de la table `mar_con_bc`
--

CREATE TABLE `mar_con_bc` (
  `ID` int(11) NOT NULL,
  `ID_PROJET` int(11) NOT NULL,
  `ID_ORGANISME` int(11) NOT NULL,
  `NOM` varchar(100) DEFAULT NULL,
  `TYPE` varchar(100) NOT NULL,
  `NUMERO` varchar(100) NOT NULL,
  `HT` double NOT NULL,
  `TVA` double NOT NULL,
  `TTC` double NOT NULL,
  `COMPTE_NUMERO` varchar(100) NOT NULL,
  `COMPTE_BANCK` varchar(100) NOT NULL,
  `COMPTE_AGENCE` varchar(100) NOT NULL,
  `NUMBER_LOGTS` int(11) NOT NULL,
  `DATE` date NOT NULL,
  `TYPE_DUREE` varchar(10) NOT NULL,
  `DUREE` int(11) NOT NULL,
  `ODS` date DEFAULT NULL,
  `ARCHIVE` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `mar_con_bc`
--

INSERT INTO `mar_con_bc` (`ID`, `ID_PROJET`, `ID_ORGANISME`, `NOM`, `TYPE`, `NUMERO`, `HT`, `TVA`, `TTC`, `COMPTE_NUMERO`, `COMPTE_BANCK`, `COMPTE_AGENCE`, `NUMBER_LOGTS`, `DATE`, `TYPE_DUREE`, `DUREE`, `ODS`, `ARCHIVE`) VALUES
(3, 6, 4, 'PROJET REALISATION DE 50/350/1500LOGT PQQ A GEUT EL-OUED', 'REALISATION', '012/2014', 117597645, 0, 117597645, '1245789630000', 'BNA', 'TAMANRASSET', 50, '2023-01-01', '', 0, '2023-01-10', 0),
(4, 4, 3, 'REALISATION PROJET DE 50/350/1500LOGT PQQ A GEUT EL-OUED', 'REALISATION', '013/2014', 129310405.5, 0, 129310405.5, '125487963211', 'BDL', 'TAMANRASSET', 50, '2023-01-01', '', 0, '2023-01-10', 0),
(6, 4, 3, 'PROJET REALISATION 20/200/500 LOGTS', 'REALISATION', '003/2022', 1520000, 9, 1656800, '12000000', 'BNA', 'TAM', 20, '2023-01-01', 'MOIS', 5, '2023-01-23', 0);

-- --------------------------------------------------------

--
-- Structure de la table `ods_arret`
--

CREATE TABLE `ods_arret` (
  `ID` int(11) NOT NULL,
  `ID_MAR_CON_BC` int(11) NOT NULL,
  `NUMBER` varchar(50) NOT NULL,
  `DATE` date NOT NULL,
  `RAISON` varchar(300) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `ods_arret`
--

INSERT INTO `ods_arret` (`ID`, `ID_MAR_CON_BC`, `NUMBER`, `DATE`, `RAISON`) VALUES
(1, 6, '002/2023', '2023-02-02', 'SO COOL'),
(2, 6, '002/2023', '2023-03-01', 'SO COOL');

-- --------------------------------------------------------

--
-- Structure de la table `ods_reprise`
--

CREATE TABLE `ods_reprise` (
  `ID` int(11) NOT NULL,
  `ID_MAR_CON_BC` int(11) NOT NULL,
  `NUMBER` varchar(50) NOT NULL,
  `DATE` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `ordre_paiement`
--

CREATE TABLE `ordre_paiement` (
  `ID` int(11) NOT NULL,
  `ID_FACTURE` int(11) NOT NULL,
  `NUMERO` varchar(100) NOT NULL,
  `DATE` date NOT NULL,
  `MONTANT` double NOT NULL,
  `PINALITE_ROUTARD` double NOT NULL DEFAULT 0,
  `RETUNE_GARANTE` double NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `ordre_paiement`
--

INSERT INTO `ordre_paiement` (`ID`, `ID_FACTURE`, `NUMERO`, `DATE`, `MONTANT`, `PINALITE_ROUTARD`, `RETUNE_GARANTE`) VALUES
(8, 5, '001/2014', '2014-05-29', 5789055.223, 0, 304687.12),
(9, 6, '002/2014', '2014-10-19', 9417597.5415, 0, 495663.03),
(10, 7, '003/2014', '2014-12-14', 6496069.296, 0, 341898.38);

-- --------------------------------------------------------

--
-- Structure de la table `organisme`
--

CREATE TABLE `organisme` (
  `ID` int(255) NOT NULL,
  `RAISON_SOCIAL` varchar(100) NOT NULL,
  `ADRESSE` varchar(100) DEFAULT NULL,
  `TEL` varchar(50) DEFAULT NULL,
  `RC` varchar(50) DEFAULT NULL,
  `NIF` varchar(30) DEFAULT NULL,
  `ARCHIVE` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `organisme`
--

INSERT INTO `organisme` (`ID`, `RAISON_SOCIAL`, `ADRESSE`, `TEL`, `RC`, `NIF`, `ARCHIVE`) VALUES
(3, 'ETB SI-SALEM YAHIYA', 'ELSSALAM', '0600000000', '22 A 1032456', '12548796321458745236', 0),
(4, 'ETB BOUILOUTA  HASSINA', 'TAHAGGARET', '0500000000', '12 A 125487', '5689321478532698', 0);

-- --------------------------------------------------------

--
-- Structure de la table `programme`
--

CREATE TABLE `programme` (
  `ID` int(11) NOT NULL,
  `CODE` varchar(100) NOT NULL,
  `NOM_PROGRAMME` varchar(100) NOT NULL,
  `NOMBRE_LOGTS` int(11) NOT NULL,
  `NUMERO_CD` varchar(100) NOT NULL,
  `ANNEE_INSCRIPTION` varchar(20) NOT NULL,
  `ARCHIVE` int(11) NOT NULL DEFAULT 0,
  `ADD_TIME` datetime NOT NULL DEFAULT current_timestamp(),
  `UPDATE_TIME` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `programme`
--

INSERT INTO `programme` (`ID`, `CODE`, `NOM_PROGRAMME`, `NOMBRE_LOGTS`, `NUMERO_CD`, `ANNEE_INSCRIPTION`, `ARCHIVE`, `ADD_TIME`, `UPDATE_TIME`) VALUES
(7, '001/2011', 'PROGRAMME 1500 LOGTS  PQQ Tranche 2011  A LA WILAYA DE TAMANRASSET', 1500, 'PLS/110/81/05/2011', '2011', 0, '2022-09-11 10:00:48', '2022-09-11 10:01:21'),
(8, '002/2022', 'PROGRAMME 1500 LOGTS PQQ', 1500, 'PLS /110/18/20/2022', '2018', 0, '2022-09-11 11:08:54', '2022-09-11 11:08:54'),
(9, '003', 'PROGRAMME 1500 LOGTS  PQQ Tranche 2011  A LA WILAYA DE TAMANRASSET', 1200, 'PLS 1200', '2019', 1, '2023-01-10 09:01:11', '2023-01-10 09:01:11');

-- --------------------------------------------------------

--
-- Structure de la table `projet`
--

CREATE TABLE `projet` (
  `ID` int(11) NOT NULL,
  `ID_PROGRAMME` int(11) NOT NULL,
  `NOM` varchar(100) NOT NULL,
  `SITE` varchar(100) NOT NULL,
  `NUMBER_LOGTS` int(11) NOT NULL,
  `NUMERO_CF` varchar(100) NOT NULL,
  `DATE_INSCRIPTION` varchar(20) DEFAULT NULL,
  `ARCHIVE` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `projet`
--

INSERT INTO `projet` (`ID`, `ID_PROGRAMME`, `NOM`, `SITE`, `NUMBER_LOGTS`, `NUMERO_CF`, `DATE_INSCRIPTION`, `ARCHIVE`) VALUES
(4, 7, '350/1500 LOGTS GUETA-ELOUED', 'GUETA-ELOUED', 350, 'PLS 110 81 05 2011 01 11 01 2013', '2020', 0),
(5, 8, 'aaaaaa', 'aaaaaa', 1500, 'aaaaaaa', '2014', 1),
(6, 7, '300/1500 LOGTS TAHAGGARET', 'TAHAGGARET', 300, 'LPS 001/005/2022', '2022', 0);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `ID` int(11) NOT NULL,
  `USERNAME` varchar(30) NOT NULL,
  `PASSWORD` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`ID`, `USERNAME`, `PASSWORD`) VALUES
(1, 'a', '1');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `avenant_compte_mar_con_bc`
--
ALTER TABLE `avenant_compte_mar_con_bc`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_MAR_CON_BC` (`ID_MAR_CON_BC`);

--
-- Index pour la table `avenant_cout`
--
ALTER TABLE `avenant_cout`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_COUT` (`ID_COUT`);

--
-- Index pour la table `avenant_mar_con_bc`
--
ALTER TABLE `avenant_mar_con_bc`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_MAR_CON_BC` (`ID_MAR_CON_BC`);

--
-- Index pour la table `cout`
--
ALTER TABLE `cout`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_PROJET` (`ID_PROJET`);

--
-- Index pour la table `facture`
--
ALTER TABLE `facture`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FACTURE_ibfk_2` (`ID_MAR_CON_BC`);

--
-- Index pour la table `mar_con_bc`
--
ALTER TABLE `mar_con_bc`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_PROJET` (`ID_PROJET`),
  ADD KEY `ID_ORGANISME` (`ID_ORGANISME`);

--
-- Index pour la table `ods_arret`
--
ALTER TABLE `ods_arret`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_MAR_CON_BC` (`ID_MAR_CON_BC`);

--
-- Index pour la table `ods_reprise`
--
ALTER TABLE `ods_reprise`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_MAR_CON_BC` (`ID_MAR_CON_BC`);

--
-- Index pour la table `ordre_paiement`
--
ALTER TABLE `ordre_paiement`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_FACTURE` (`ID_FACTURE`);

--
-- Index pour la table `organisme`
--
ALTER TABLE `organisme`
  ADD PRIMARY KEY (`ID`) USING BTREE;

--
-- Index pour la table `programme`
--
ALTER TABLE `programme`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `projet`
--
ALTER TABLE `projet`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ID_PROGRAMME` (`ID_PROGRAMME`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `USERNAME` (`USERNAME`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `avenant_compte_mar_con_bc`
--
ALTER TABLE `avenant_compte_mar_con_bc`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT pour la table `avenant_cout`
--
ALTER TABLE `avenant_cout`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT pour la table `avenant_mar_con_bc`
--
ALTER TABLE `avenant_mar_con_bc`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT pour la table `cout`
--
ALTER TABLE `cout`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT pour la table `facture`
--
ALTER TABLE `facture`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `mar_con_bc`
--
ALTER TABLE `mar_con_bc`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `ods_arret`
--
ALTER TABLE `ods_arret`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `ods_reprise`
--
ALTER TABLE `ods_reprise`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `ordre_paiement`
--
ALTER TABLE `ordre_paiement`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT pour la table `organisme`
--
ALTER TABLE `organisme`
  MODIFY `ID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `programme`
--
ALTER TABLE `programme`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `projet`
--
ALTER TABLE `projet`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `avenant_compte_mar_con_bc`
--
ALTER TABLE `avenant_compte_mar_con_bc`
  ADD CONSTRAINT `avenant_compte_mar_con_bc_ibfk_1` FOREIGN KEY (`ID_MAR_CON_BC`) REFERENCES `mar_con_bc` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `avenant_cout`
--
ALTER TABLE `avenant_cout`
  ADD CONSTRAINT `AVENANT_COUT_ibfk_1` FOREIGN KEY (`ID_COUT`) REFERENCES `cout` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `avenant_mar_con_bc`
--
ALTER TABLE `avenant_mar_con_bc`
  ADD CONSTRAINT `AVENANT_MAR_CON_BC_ibfk_1` FOREIGN KEY (`ID_MAR_CON_BC`) REFERENCES `mar_con_bc` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `cout`
--
ALTER TABLE `cout`
  ADD CONSTRAINT `COUT_ibfk_1` FOREIGN KEY (`ID_PROJET`) REFERENCES `projet` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `facture`
--
ALTER TABLE `facture`
  ADD CONSTRAINT `FACTURE_ibfk_2` FOREIGN KEY (`ID_MAR_CON_BC`) REFERENCES `mar_con_bc` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `mar_con_bc`
--
ALTER TABLE `mar_con_bc`
  ADD CONSTRAINT `MAR_CON_BC_ibfk_1` FOREIGN KEY (`ID_PROJET`) REFERENCES `projet` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `MAR_CON_BC_ibfk_2` FOREIGN KEY (`ID_ORGANISME`) REFERENCES `organisme` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `ods_arret`
--
ALTER TABLE `ods_arret`
  ADD CONSTRAINT `ods_arret_ibfk_1` FOREIGN KEY (`ID_MAR_CON_BC`) REFERENCES `mar_con_bc` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `ordre_paiement`
--
ALTER TABLE `ordre_paiement`
  ADD CONSTRAINT `ORDRE_PAIEMENT_ibfk_1` FOREIGN KEY (`ID_FACTURE`) REFERENCES `facture` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `projet`
--
ALTER TABLE `projet`
  ADD CONSTRAINT `PROJET_ibfk_1` FOREIGN KEY (`ID_PROGRAMME`) REFERENCES `programme` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
