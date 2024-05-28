CREATE DATABASE db;

CREATE TABLE IF NOT EXISTS employee
( id INTEGER NOT NULL AUTO_INCREMENT,
  firstName VARCHAR(128) NOT NULL,
  lastName VARCHAR(128) NOT NULL,
  phoneNumber VARCHAR(128) NOT NULL,
  positionName VARCHAR(128) NOT NULL,
  PRIMARY KEY (id)
);

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");
        String positionName = request.getParameter("positionName");

        Employee