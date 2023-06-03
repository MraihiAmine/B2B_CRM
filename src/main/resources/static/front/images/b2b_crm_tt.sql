-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 28, 2023 at 09:36 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `b2b_crm_tt`
--

-- --------------------------------------------------------

--
-- Table structure for table `average_deal`
--

CREATE TABLE `average_deal` (
  `id` bigint(20) NOT NULL,
  `average_dealq1` double NOT NULL,
  `average_dealq2` double NOT NULL,
  `average_dealq3` double NOT NULL,
  `average_dealq4` double NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `year_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `conversion_rate`
--

CREATE TABLE `conversion_rate` (
  `id` bigint(20) NOT NULL,
  `conversion_rateq1` double NOT NULL,
  `conversion_rateq2` double NOT NULL,
  `conversion_rateq3` double NOT NULL,
  `conversion_rateq4` double NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `year_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `customer_retention_rate`
--

CREATE TABLE `customer_retention_rate` (
  `id` bigint(20) NOT NULL,
  `retention_rateq1` decimal(19,2) DEFAULT NULL,
  `retention_rateq2` decimal(19,2) DEFAULT NULL,
  `retention_rateq3` decimal(19,2) DEFAULT NULL,
  `retention_rateq4` decimal(19,2) DEFAULT NULL,
  `returning_customers_q1` decimal(19,2) NOT NULL,
  `returning_customers_q2` decimal(19,2) NOT NULL,
  `returning_customers_q3` decimal(19,2) NOT NULL,
  `returning_customers_q4` decimal(19,2) NOT NULL,
  `total_customers_q1` decimal(19,2) NOT NULL,
  `total_customers_q2` decimal(19,2) NOT NULL,
  `total_customers_q3` decimal(19,2) NOT NULL,
  `total_customers_q4` decimal(19,2) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `year_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(35);

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE `messages` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `receiver_id` int(11) DEFAULT NULL,
  `sender_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `nps`
--

CREATE TABLE `nps` (
  `id` bigint(20) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `nps` int(11) DEFAULT NULL,
  `product` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `description`, `name`, `price`) VALUES
(1, 'test', 'Samsung', 500);

-- --------------------------------------------------------

--
-- Table structure for table `prospection`
--

CREATE TABLE `prospection` (
  `id` bigint(20) NOT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `valueq1` double NOT NULL,
  `valueq2` double NOT NULL,
  `valueq3` double NOT NULL,
  `valueq4` double NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `prospection_status_id1` bigint(20) NOT NULL,
  `prospection_status_id2` bigint(20) NOT NULL,
  `prospection_status_id3` bigint(20) NOT NULL,
  `prospection_status_id4` bigint(20) NOT NULL,
  `year_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `prospection_statistics`
--

CREATE TABLE `prospection_statistics` (
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `prospection_status`
--

CREATE TABLE `prospection_status` (
  `id` bigint(20) NOT NULL,
  `status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`role_id`, `role`) VALUES
(1, 'SUPERADMIN'),
(22, 'ADMIN'),
(32, 'Sales man');

-- --------------------------------------------------------

--
-- Table structure for table `sales_growth_rate`
--

CREATE TABLE `sales_growth_rate` (
  `id` bigint(20) NOT NULL,
  `actual_sales_q1` decimal(19,2) NOT NULL,
  `actual_sales_q2` decimal(19,2) NOT NULL,
  `actual_sales_q3` decimal(19,2) NOT NULL,
  `actual_sales_q4` decimal(19,2) NOT NULL,
  `sales_growth_rateq1` decimal(19,2) DEFAULT NULL,
  `sales_growth_rateq2` decimal(19,2) DEFAULT NULL,
  `sales_growth_rateq3` decimal(19,2) DEFAULT NULL,
  `sales_growth_rateq4` decimal(19,2) DEFAULT NULL,
  `product_id` bigint(20) NOT NULL,
  `year_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `sales_statistics`
--

CREATE TABLE `sales_statistics` (
  `id` bigint(20) NOT NULL,
  `customer_retention_rate` decimal(19,2) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `nps` int(11) DEFAULT NULL,
  `product` varchar(255) DEFAULT NULL,
  `sales_growth_rate` decimal(19,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `sales_statistics`
--

INSERT INTO `sales_statistics` (`id`, `customer_retention_rate`, `date`, `nps`, `product`, `sales_growth_rate`) VALUES
(1, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `active` int(11) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `active`, `email`, `last_name`, `name`, `password`, `picture`) VALUES
(2, 1, 'nessrineabdaoui2021@gmail.com', 'Abdaoui', 'Nessrine', '$2a$10$qB4UrSeGJcv88xRem0RmXO8XBeSFnMYq4vGzdHvuFHpuQEs.niEt2', NULL),
(33, 1, 'mraihiamin@gmail.com', 'Amine', 'Mraihi', '$2a$10$kdvAtSCMF0Adh9nzuyzLNuiEUSrrWjFVIBa1LHgWKNeHVZVNjApEu', NULL),
(34, 1, 'vues1445799@gmail.com', 'Amine', 'Mraihi', '$2a$10$.W8nWG2cWzSdzmW9igWqleJvdicBJQljFEZXnfxK/sKNrN.MUggCy', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(2, 1),
(33, 22),
(34, 32);

-- --------------------------------------------------------

--
-- Table structure for table `year`
--

CREATE TABLE `year` (
  `id` bigint(20) NOT NULL,
  `year` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `average_deal`
--
ALTER TABLE `average_deal`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKnnke4v3u6gdqor3ilvh0yqeko` (`product_id`),
  ADD KEY `FK82ucjefht2pi6xk3vam4vbe6n` (`year_id`);

--
-- Indexes for table `conversion_rate`
--
ALTER TABLE `conversion_rate`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK73x5css3h84qwgspi3peoi51d` (`product_id`),
  ADD KEY `FK8dvqiqmlg91jsjt3bo3k0x1jy` (`year_id`);

--
-- Indexes for table `customer_retention_rate`
--
ALTER TABLE `customer_retention_rate`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK7jcptvq16po09dsm9hrabajqs` (`product_id`),
  ADD KEY `FK9kaxjyc9ltnl8w37nq46l8ggh` (`year_id`);

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjnjxr6fd6nmvp28gakno4np94` (`receiver_id`),
  ADD KEY `FKip9clvpi646rirksmm433wykx` (`sender_id`);

--
-- Indexes for table `nps`
--
ALTER TABLE `nps`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `prospection`
--
ALTER TABLE `prospection`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKey9emflc5h3xdq0vroiw12sl9` (`product_id`),
  ADD KEY `FKbvkq5wfhr3m5m5udyga802kx6` (`prospection_status_id1`),
  ADD KEY `FK7cmailtsp1j27p8e3be6ia1kp` (`prospection_status_id2`),
  ADD KEY `FK3sygwgt3fugekjvlrlkkchsdl` (`prospection_status_id3`),
  ADD KEY `FK8glv1fh9wlp0lxcjy74v1w5xr` (`prospection_status_id4`),
  ADD KEY `FK6cq8sw3mb9bowuaxu7y3eqifn` (`year_id`);

--
-- Indexes for table `prospection_statistics`
--
ALTER TABLE `prospection_statistics`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `prospection_status`
--
ALTER TABLE `prospection_status`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role_id`);

--
-- Indexes for table `sales_growth_rate`
--
ALTER TABLE `sales_growth_rate`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK926x39a4e4y6m600khikw2fue` (`product_id`),
  ADD KEY `FKdxw6xlp9h0artwxxuvfmts0lp` (`year_id`);

--
-- Indexes for table `sales_statistics`
--
ALTER TABLE `sales_statistics`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`);

--
-- Indexes for table `year`
--
ALTER TABLE `year`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `average_deal`
--
ALTER TABLE `average_deal`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `conversion_rate`
--
ALTER TABLE `conversion_rate`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `customer_retention_rate`
--
ALTER TABLE `customer_retention_rate`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `messages`
--
ALTER TABLE `messages`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `nps`
--
ALTER TABLE `nps`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `prospection`
--
ALTER TABLE `prospection`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `prospection_statistics`
--
ALTER TABLE `prospection_statistics`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `prospection_status`
--
ALTER TABLE `prospection_status`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sales_growth_rate`
--
ALTER TABLE `sales_growth_rate`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sales_statistics`
--
ALTER TABLE `sales_statistics`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `year`
--
ALTER TABLE `year`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `average_deal`
--
ALTER TABLE `average_deal`
  ADD CONSTRAINT `FK82ucjefht2pi6xk3vam4vbe6n` FOREIGN KEY (`year_id`) REFERENCES `year` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FKnnke4v3u6gdqor3ilvh0yqeko` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `conversion_rate`
--
ALTER TABLE `conversion_rate`
  ADD CONSTRAINT `FK73x5css3h84qwgspi3peoi51d` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK8dvqiqmlg91jsjt3bo3k0x1jy` FOREIGN KEY (`year_id`) REFERENCES `year` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `customer_retention_rate`
--
ALTER TABLE `customer_retention_rate`
  ADD CONSTRAINT `FK7jcptvq16po09dsm9hrabajqs` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK9kaxjyc9ltnl8w37nq46l8ggh` FOREIGN KEY (`year_id`) REFERENCES `year` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `messages`
--
ALTER TABLE `messages`
  ADD CONSTRAINT `FKip9clvpi646rirksmm433wykx` FOREIGN KEY (`sender_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `FKjnjxr6fd6nmvp28gakno4np94` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `prospection`
--
ALTER TABLE `prospection`
  ADD CONSTRAINT `FK3sygwgt3fugekjvlrlkkchsdl` FOREIGN KEY (`prospection_status_id3`) REFERENCES `prospection_status` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK6cq8sw3mb9bowuaxu7y3eqifn` FOREIGN KEY (`year_id`) REFERENCES `year` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK7cmailtsp1j27p8e3be6ia1kp` FOREIGN KEY (`prospection_status_id2`) REFERENCES `prospection_status` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK8glv1fh9wlp0lxcjy74v1w5xr` FOREIGN KEY (`prospection_status_id4`) REFERENCES `prospection_status` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FKbvkq5wfhr3m5m5udyga802kx6` FOREIGN KEY (`prospection_status_id1`) REFERENCES `prospection_status` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FKey9emflc5h3xdq0vroiw12sl9` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `sales_growth_rate`
--
ALTER TABLE `sales_growth_rate`
  ADD CONSTRAINT `FK926x39a4e4y6m600khikw2fue` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FKdxw6xlp9h0artwxxuvfmts0lp` FOREIGN KEY (`year_id`) REFERENCES `year` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
