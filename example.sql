use philips
--
-- Table structure for table `monitor`
--

DROP TABLE IF EXISTS `monitor`;
CREATE TABLE `monitor` (
  `id` int(11) NOT NULL auto_increment,
  `brand` varchar(20) default NULL,
  `name` varchar(20) default NULL,
  `size` varchar(20) default NULL,
  `type` varchar(20) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `monitor`
--

LOCK TABLES `monitor` WRITE;
/*!40000 ALTER TABLE `monitor` DISABLE KEYS */;
INSERT INTO `monitor` VALUES (1,'Goldway','G30E','10','Non-Touch'),(2,'Goldway','G40E','12','Non-Touch'),(3,'Efficia','CM10','10','Non-Touch'),(4,'Efficia','CM12','12','Non-Touch'),(5,'Efficia','CM100','10','Touch'),(6,'Efficia','CM120','12','Touch'),(7,'Efficia','CM150','15','Touch');
/*!40000 ALTER TABLE `monitor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stageonequerytable`
--

DROP TABLE IF EXISTS `stageonequerytable`;
CREATE TABLE `stageonequerytable` (
  `id` int(11) NOT NULL auto_increment,
  `question` varchar(500) default NULL,
  `serialno` int(11) default NULL,
  `selector` tinyint(1) default '0',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `stageonequerytable`
--

LOCK TABLES `stageonequerytable` WRITE;
/*!40000 ALTER TABLE `stageonequerytable` DISABLE KEYS */;
INSERT INTO `stageonequerytable` VALUES (1,'Press 1 to select monitors of your choice',1,0),(2,'Press 2 to search for monitors',2,0),(3,'Press 3, if you have any query',3,0);
/*!40000 ALTER TABLE `stageonequerytable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stagetwoquerytable`
--

DROP TABLE IF EXISTS `stagetwoquerytable`;
CREATE TABLE `stagetwoquerytable` (
  `id` int(11) NOT NULL auto_increment,
  `question` varchar(500) default NULL,
  `one_id` int(11) default NULL,
  `serialno` int(11) default NULL,
  `selector` tinyint(1) default '0',
  PRIMARY KEY  (`id`),
  KEY `one_id` (`one_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `stagetwoquerytable`
--

LOCK TABLES `stagetwoquerytable` WRITE;
/*!40000 ALTER TABLE `stagetwoquerytable` DISABLE KEYS */;
INSERT INTO `stagetwoquerytable` VALUES (1,'Enter the monitor\'s name',2,1,0),(2,'Press 1 to select monitors by Brand',1,1,0),(3,'Press 2 to select monitors by Screen-Type',1,2,0),(4,'Press 3 to select monitors by Screen-Size',1,3,0),(5,'Press 4 to see all the available monitors',1,4,0),(6,'Enter the following details',3,1,0);
/*!40000 ALTER TABLE `stagetwoquerytable` ENABLE KEYS */;
UNLOCK TABLES;



-- Table structure for table `userdetails`
--

DROP TABLE IF EXISTS `userdetails`;
CREATE TABLE `userdetails` (
  `id` int(11) NOT NULL auto_increment,
  `Name` varchar(20) default NULL,
  `Contact_No` varchar(20) default NULL,
  `Email` varchar(20) default NULL,
  `Hospital_Address` varchar(20) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userdetails`
--

LOCK TABLES `userdetails` WRITE;
/*!40000 ALTER TABLE `userdetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `userdetails` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
