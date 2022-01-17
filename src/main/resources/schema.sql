--
-- Table structure for table `file_table`
--

CREATE TABLE FILE_TABLE
(
    ID                int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    CREATED_DATE_TIME timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FILE_NAME         varchar(255) NOT NULL,
    FILE_DATA         blob         NOT NULL
);