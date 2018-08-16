-- Employees
CREATE TABLE Employees (
   EmployeeId varchar(64) UNIQUE NOT NULL,
   FirstName varchar(32),
   LastName varchar(32),
   OrganizationId varchar(64) NOT NULL,
   InternalCreate TINYINT DEFAULT 0,
   InternalRead TINYINT DEFAULT 1,
   InternalUpdate TINYINT DEFAULT 0,
   InternalDelete TINYINT DEFAULT 0,
   PRIMARY KEY(EmployeeId)
);
CREATE PROCEDURE getAllEmployees AS SELECT * FROM employees;
CREATE PROCEDURE getEmployee AS SELECT * FROM employees WHERE EmployeeId=?;
-- Products
CREATE TABLE Products (
   ProductId VARCHAR(64) UNIQUE NOT NULL,
   OrganizationId VARCHAR(64) NOT NULL,
   Name VARCHAR(64),
   Price FLOAT,
   Stock INTEGER,
   PRIMARY KEY(ProductId)
);
CREATE PROCEDURE getAllProducts AS SELECT * FROM products;
CREATE PROCEDURE getProduct AS SELECT * FROM products WHERE ProductId=?;
CREATE PROCEDURE getProductsByOrganizationId AS SELECT * FROM products WHERE OrganizationId=?;
-- Organizations
CREATE TABLE Organizations (
   OrganizationId VARCHAR(64) UNIQUE NOT NULL,
   Name VARCHAR(32),
   PRIMARY KEY(OrganizationId)
);
CREATE PROCEDURE getAllOrganizations AS SELECT * FROM organizations;
CREATE PROCEDURE getOrganization AS SELECT * FROM organizations WHERE OrganizationId=?;
-- External rights
CREATE TABLE ExternalRights (
   ExternalRightsId VARCHAR(64) UNIQUE NOT NULL,
   ReceivingOrganizationId VARCHAR(64) NOT NULL,
   GivingOrganizationId VARCHAR(64) NOT NULL,
   IsCreate TINYINT DEFAULT 0,
   IsRead TINYINT DEFAULT 0,
   IsUpdate TINYINT DEFAULT 0,
   IsDelete TINYINT DEFAULT 0,
   QuantityRestrictionAmount INTEGER,
   RestrictingCondition VARCHAR(16),
   CONSTRAINT PK_ExternalRightsId PRIMARY KEY (ReceivingOrganizationId, GivingOrganizationId)
);
CREATE PROCEDURE getAllExternalRights AS SELECT * FROM ExternalRights;
CREATE PROCEDURE getExternalRights AS SELECT * FROM ExternalRights WHERE ExternalRightsId=?;
CREATE PROCEDURE getExternalRightsByReceivingOrganization AS SELECT * FROM ExternalRights WHERE ReceivingOrganizationId=?;
CREATE PROCEDURE deleteAllExternalRights AS DELETE FROM ExternalRights;
-- Approval requests
CREATE TABLE ApprovalRequests (
   ApprovalRequestId VARCHAR(64) UNIQUE NOT NULL,
   RequestingOrganizationId VARCHAR(64) NOT NULL,
   SharingOrganizationId VARCHAR(64) NOT NULL,
   IsCreate TINYINT DEFAULT 0,
   IsRead TINYINT DEFAULT 1,
   IsUpdate TINYINT DEFAULT 0,
   IsDelete TINYINT DEFAULT 0,
   QuantityRestrictionAmount INTEG
   RestrictingCondition VARCHAR(16),
   CONSTRAINT PK_ApprovalRequestId PRIMARY KEY (RequestingOrganizationId, SharingOrganizationId)
);
CREATE PROCEDURE getAllApprovalRequests AS SELECT * FROM ApprovalRequests;
CREATE PROCEDURE getApprovalRequest AS SELECT * FROM ApprovalRequests WHERE ApprovalRequestId=?;
CREATE PROCEDURE getApprovalRequestByRequestingOrganizationId AS SELECT * FROM ApprovalRequests WHERE RequestingOrganizationId=?;
CREATE PROCEDURE deleteAllApprovalRequests AS DELETE FROM ApprovalRequests;
