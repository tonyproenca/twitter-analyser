CREATE DATABASE TWITTER_ANALYSER;

CREATE USER 'ttanalyser'@'localhost' IDENTIFIED BY 'TwitterAnalyser123@';

GRANT ALL PRIVILEGES ON * . * TO 'ttanalyser'@'localhost';