--
-- Table structure for table `file_table`
--

CREATE TABLE IF NOT EXISTS `file_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `file_name` varchar(255) NOT NULL,
  `file_data` longblob NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;