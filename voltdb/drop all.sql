-- Employees
DROP PROCEDURE getAllEmployees;
DROP PROCEDURE getAllEmployeesByOrganization;
DROP PROCEDURE getEmployee;
DROP TABLE Employees;
-- Products
DROP PROCEDURE getAllProducts;
DROP PROCEDURE getProduct;
DROP TABLE Products;
-- Organizations
DROP PROCEDURE getAllOrganizations;
DROP PROCEDURE getOrganization;
DROP TABLE Organizations;
-- Master Organizations
DROP PROCEDURE getAllMasterOrganizations;
DROP PROCEDURE getMasterOrganization;
DROP TABLE MasterOrganizations;
-- External rights
DROP PROCEDURE getAllExternalRights;
DROP PROCEDURE getExternalRights;
DROP PROCEDURE getExternalRightsByReceivingOrganization;
DROP PROCEDURE deleteAllExternalRights;
DROP TABLE ExternalRights;
-- Approval requests
DROP PROCEDURE getAllApprovalRequests;
DROP PROCEDURE getApprovalRequest;
DROP PROCEDURE getApprovalRequestByRequestingOrganizationId;
DROP PROCEDURE deleteAllApprovalRequests;
DROP TABLE ApprovalRequests;
