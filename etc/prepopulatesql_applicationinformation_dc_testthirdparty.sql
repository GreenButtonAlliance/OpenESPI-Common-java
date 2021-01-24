/*
 *
 *    Copyright (c) 2018-2021 Green Button Alliance, Inc.
 *
 *    Portions (c) 2013-2018 EnergyOS.org
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

/* Add application_information */
SET sql_mode = 'PIPES_AS_CONCAT';

SET @dcbaseurl = 'http://localhost:8080';
SET @tpbaseurl = 'http://localhost:8081';

INSERT INTO `application_information` (`id`, `kind`, `description`, `published`, `self_link_href`, `self_link_rel`, `up_link_href`, `up_link_rel`, `updated`, `uuid`, `authorizationServerAuthorizationEndpoint`, `authorizationServerRegistrationEndpoint`, `authorizationServerTokenEndpoint`, `authorizationServerUri`, `clientId`, `clientIdIssuedAt`, `clientName`, `clientSecret`, `clientSecretExpiresAt`, `clientUri`, `contacts`, `dataCustodianApplicationStatus`, `dataCustodianBulkRequestURI`, `dataCustodianDefaultBatchResource`, `dataCustodianDefaultSubscriptionResource`, `dataCustodianId`, `dataCustodianResourceEndpoint`, `dataCustodianThirdPartySelectionScreenURI`, `logoUri`, `policyUri`, `redirectUri`, `registrationAccessToken`, `registrationClientUri`, `responseTypes`, `softwareId`, `softwareVersion`, `thirdPartyApplicationDescription`, `thirdPartyApplicationName`, `thirdPartyApplicationStatus`, `thirdPartyApplicationType`, `thirdPartyApplicationUse`, `thirdPartyDataCustodianSelectionScreenURI`, `thirdPartyLoginScreenURI`, `thirdPartyNotifyUri`, `thirdPartyPhone`, `thirdPartyScopeSelectionScreenURI`, `thirdPartyUserPortalScreenURI`, `tokenEndpointAuthMethod`, `tosUri`, `dataCustodianScopeSelectionScreenURI`) VALUES
(1, 'DATA_CUSTODIAN_ADMIN', 'GreenButtonData.org  Data Custodian Admin Application', '2014-01-02 05:00:00', '/espi/1_1/resource/DataCustodian/ApplicationInformation/1', 'self', '/espi/1_1/resource/DataCustodian/ApplicationInformation', 'up', '2014-01-02 05:00:00', '185C9C3F-5B4A-44A9-8959-3AE79E60A9F5', @dcbaseurl || '/DataCustodian/oauth/authorize', @dcbaseurl || '/DataCustodian/espi/1_1/register', @dcbaseurl || '/DataCustodian/oauth/token', @dcbaseurl || '/DataCustodian/', 'data_custodian', 1403190000, 'Green Button DataCustodian Admin', 'secret', 0, @dcbaseurl || '/DataCustodian','john.teeter@energyos.org,martin.burns@nist.gov,donald.coffin@reminetworks.com', '1', @dcbaseurl || '/DataCustodian/espi/1_1/resource/Batch/Bulk', @dcbaseurl || '/DataCustodian/espi/1_1/resource/Batch', @dcbaseurl || '/DataCustodian/espi/1_1/resource/Batch/Subscription', 'data_custodian', @dcbaseurl || '/DataCustodian/espi/1_1/resource', @dcbaseurl || '/DataCustodian/RetailCustomer/{retailCustomerId}/ThirdPartyList', NULL, NULL, NULL, NULL, @dcbaseurl || '/DataCustodian/espi/1_1/resource/ApplicationInformation/1', 0, 'EnergyOS OpenESPI', '1.2', 'EnergyOS OpenESPI Example DataCustodian Admin', 'Data Custodian Admin (localhost)', '1', '1', '1', NULL, NULL, NULL, NULL, NULL, NULL, 'client_secret_basic', NULL, @dcbaseurl || '/DataCustodian/RetailCustomer/ScopeSelection/'),
(2, 'THIRD_PARTY', 'GreenButtonData.org  ThirdParty Application', '2014-01-02 05:00:00', '/espi/1_1/resource/DataCustodian/ApplicationInformation/2', 'self', '/espi/1_1/resource/DataCustodian/ApplicationInformation', 'up', '2014-01-02 05:00:00', 'AF6E8B03-0299-467E-972A-A883ECDCC575', @dcbaseurl || '/DataCustodian/oauth/authorize', @dcbaseurl || '/DataCustodian/espi/1_1/register', @dcbaseurl || '/DataCustodian/oauth/token', @dcbaseurl || '/DataCustodian/', 'third_party', 1403190000, 'Green Button Third Party', 'secret', 0, @tpbaseurl || '/ThirdParty', 'john.teeter@energyos.org,martin.burns@nist.gov,donald.coffin@reminetworks.com', '1', @dcbaseurl || '/DataCustodian/espi/1_1/resource/Batch/Bulk', @dcbaseurl || '/DataCustodian/espi/1_1/resource/Batch', @dcbaseurl || '/DataCustodian/espi/1_1/resource/Batch/Subscription', 'data_custodian', @dcbaseurl || '/DataCustodian/espi/1_1/resource', @dcbaseurl || '/DataCustodian/RetailCustomer/1/ThirdPartyList', @tpbaseurl || '/ThirdParty/resources/ico/favicon.png', NULL, @tpbaseurl || '/ThirdParty/espi/1_1/OAuthCallBack', 'd89bb056-0f02-4d47-9fd2-ec6a19ba8d0c', @dcbaseurl || '/DataCustodian/espi/1_1/resource/ApplicationInformation/2', 0, 'EnergyOS OpenESPI Example Third Party', '1.2', 'EnergyOS OpenESPI Example Third Party', 'Third Party (localhost)', '1', '1', '1', @tpbaseurl || '/ThirdParty/RetailCustomer/{retailCustomerId}/DataCustodianList', @tpbaseurl || '/ThirdParty/login', @tpbaseurl || '/ThirdParty/espi/1_1/Notification', NULL, @tpbaseurl || '/ThirdParty/RetailCustomer/ScopeSelection', @tpbaseurl || '/ThirdParty', 'client_secret_basic', NULL, @dcbaseurl || '/DataCustodian/RetailCustomer/ScopeSelectionList?ThirdPartyID=third_party'),
(3, 'UPLOAD_ADMIN', 'GreenButtonData.org  MDM Upload Application', '2014-01-02 05:00:00', '/espi/1_1/resource/DataCustodian/ApplicationInformation/3', 'self', '/espi/1_1/resource/DataCustodian/ApplicationInformation', 'up', '2014-01-02 05:00:00', '185C9C3F-5B4A-44A9-8959-3AE79E60A9F7', @dcbaseurl || '/DataCustodian/oauth/authorize', @dcbaseurl || '/DataCustodian/espi/1_1/register', @dcbaseurl || '/DataCustodian/oauth/token', @dcbaseurl || '/DataCustodian/', 'upload', 1403190000, 'Green Button DMD Upload Service', 'secret', 0, NULL, 'john.teeter@energyos.org,martin.burns@nist.gov,donald.coffin@reminetworks.com', '1', @dcbaseurl || '/DataCustodian/espi/1_1/resource/Batch/Bulk', @dcbaseurl || '/DataCustodian/espi/1_1/resource/Batch', @dcbaseurl || '/DataCustodian/espi/1_1/resource/Batch/Subscription', 'data_custodian', @dcbaseurl || '/DataCustodian/espi/1_1/resource', @dcbaseurl || '/DataCustodian/RetailCustomer/1/ThirdPartyList', NULL, NULL, NULL, NULL, @dcbaseurl || '/DataCustodian/espi/1_1/resource/ApplicationInformation/3', 0, 'EnergyOS OpenESPI DMD Upload Service', '1.2', 'EnergyOS OpenESPI DMD Upload Service', 'MDM Upload (localhost)', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'client_secret_basic', NULL, NULL);

/* Add application_information_scopes */ 
INSERT INTO application_information_scopes (application_information_id, scope) VALUES (1, 'FB=1_3_4_5_13_14_19_32_33_34_35_36_37_38_41_44_45');
INSERT INTO application_information_scopes (application_information_id, scope) VALUES (2, 'FB=1_3_4_5_13_14_39;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13');
INSERT INTO application_information_scopes (application_information_id, scope) VALUES (2, 'FB=1_3_4_5_13_14_15_39;IntervalDuration=900;BlockDuration=monthly;HistoryLength=13');
INSERT INTO application_information_scopes (application_information_id, scope) VALUES (2, 'FB=1_3_4_5_13_14_39;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13');
INSERT INTO application_information_scopes (application_information_id, scope) VALUES (2, 'FB=1_3_4_5_6_7_8_9_10_11_29_12_13_14_15_16_17_18_19_27_28_32_33_34_35_37_38_39_40_41_44;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13');
INSERT INTO application_information_scopes (application_information_id, scope) VALUES (3, 'FB=45');

/* Add application_information_grant_types */
INSERT INTO application_information_grant_types (application_information_id, grantTypes) VALUES (1, 'CLIENT_CREDENTIALS');
INSERT INTO application_information_grant_types (application_information_id, grantTypes) VALUES (2, 'AUTHORIZATION_CODE');
INSERT INTO application_information_grant_types (application_information_id, grantTypes) VALUES (2, 'REFRESH_TOKEN');
INSERT INTO application_information_grant_types (application_information_id, grantTypes) VALUES (2, 'CLIENT_CREDENTIALS');
INSERT INTO application_information_grant_types (application_information_id, grantTypes) VALUES (3, 'CLIENT_CREDENTIALS');

/* Add predefined authorizations */
INSERT INTO `authorizations` VALUES (1,NULL,'2015-05-15 15:02:39',NULL,'self',NULL,'up','2015-05-15 15:02:40','A1635478-76A7-449D-8AE9-EBB191352C25','2a85f4bd-30db-4b7d-8f41-b046b0566cb3','https://localhost:8443/DataCustodian/espi/1_1/resource/Authorization/1',0,0,NULL,NULL,NULL,NULL,1747076559,NULL,0,0,NULL,'https://localhost:8443/DataCustodian/espi/1_1/resource/',NULL,'FB=3_19_32_33_34_35_36_37_38_41_44_45',NULL,'1','data_custodian',0,1,0,1),
(2,NULL,'2015-05-15 15:02:53',NULL,'self',NULL,'up','2015-05-15 15:02:53','63463495-7D32-4E45-8775-3B35AC60E13F','03909715-b0ca-4797-9a9a-601fff1d2848','https://localhost:8443/DataCustodian/espi/1_1/resource/Authorization/2',0,0,NULL,NULL,NULL,NULL,1747076572,NULL,0,0,NULL,'https://localhost:8443/DataCustodian/espi/1_1/resource/Batch/RetailCustomer/**/UsagePoint',NULL,'FB=45',NULL,'1','upload',0,3,0,2),
(3,NULL,'2015-05-15 15:02:56',NULL,'self',NULL,'up','2015-05-15 15:02:56','766A7FBC-BB0B-41D1-801F-0E91EA0A3A64','53520584-d640-4812-a721-8a1afa459ff7','https://localhost:8443/DataCustodian/espi/1_1/resource/Authorization/3',0,0,NULL,NULL,NULL,NULL,1747076575,NULL,0,0,NULL,'https://localhost:8443/DataCustodian/espi/1_1/resource/Batch/Bulk/**',NULL,'FB=34_35',NULL,'1','third_party_admin',0,2,0,3),
(4,NULL,'2015-05-15 15:03:02',NULL,'self',NULL,'up','2015-05-15 15:03:02','403CBA0C-46F5-4E64-816C-5D11C06DEB7A','c66b0854-ea1f-4e24-afb7-afab9e0f6c5e','https://localhost:8443/DataCustodian/espi/1_1/resource/Authorization/4',0,0,NULL,NULL,NULL,NULL,1747076581,NULL,0,0,NULL,'https://localhost:8443/DataCustodian/espi/1_1/resource/ApplicationInformation/2',NULL,'FB=36_40',NULL,'1','REGISTRATION_third_party',0,2,0,4);

/* Add predefined subscriptions */
INSERT INTO `subscriptions` VALUES (1,NULL,'2015-05-15 15:02:40',NULL,'self',NULL,'up','2015-05-15 15:02:40','BE830DC5-3F21-4967-AA35-EFDF8C61A914','4e625c48-3ec2-4571-9e21-d1da0c57516d','2015-05-15 15:02:40',1,1,0),
(2,NULL,'2015-05-15 15:02:53',NULL,'self',NULL,'up','2015-05-15 15:02:53','55AE97C3-2DD4-4762-B7BF-11AED8D7AE58','7a60b601-f2c0-41aa-8e71-8dbe42c028d9','2015-05-15 15:02:53',3,2,0),
(3,NULL,'2015-05-15 15:02:56',NULL,'self',NULL,'up','2015-05-15 15:02:56','991C0B49-97BA-48C2-BE30-7545C027F72E','82e034de-0812-4c8f-879a-fc367e7fe224','2015-05-15 15:02:56',2,3,0),
(4,NULL,'2015-05-15 15:03:02',NULL,'self',NULL,'up','2015-05-15 15:03:02','99F1F395-8883-44F7-8D1D-1DBABDCB7C1F','38a6bea3-cdad-4ab6-af81-2034ad112b0e','2015-05-15 15:03:02',2,4,0);

