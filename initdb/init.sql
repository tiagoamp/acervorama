CREATE TABLE media_items (
  dtype varchar(31) NOT NULL,
  id bigint(20) NOT NULL AUTO_INCREMENT,
  additional_info varchar(255) DEFAULT NULL,
  classification varchar(255) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  path varchar(255) DEFAULT NULL,
  filename varchar(255) DEFAULT NULL,
  hash varchar(255) DEFAULT NULL,
  registration_date datetime DEFAULT NULL,
  tags varchar(255) DEFAULT NULL,
  type varchar(255) DEFAULT NULL,
  author varchar(255) DEFAULT NULL,
  title varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
);
