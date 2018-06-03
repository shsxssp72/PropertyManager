DROP DATABASE IF EXISTS PropertyManagement;
CREATE DATABASE PropertyManagement;
USE PropertyManagement;


CREATE TABLE department
(
  dept_id   CHAR(20) PRIMARY KEY,
  dept_name VARCHAR(20)
);

CREATE TABLE staff
(
  staff_id   CHAR(20) PRIMARY KEY,
  staff_name VARCHAR(20) NOT NULL,
  gender     VARCHAR(20) NOT NULL,
  tel        VARCHAR(15) NOT NULL,
  address    VARCHAR(100),
  position   VARCHAR(20), ##注意，此处应用自建类型加以枚举指定或用外键约束
  salary     DECIMAL(11, 2), ##将salary更改为了定点数
  dept       CHAR(20), ##注意，此处应用自建类型加以枚举指定或用外键约束
  elec_qlfy  BOOL, ##电工资质
  plbr_qlfy  BOOL, ##水管工资质
  FOREIGN KEY (dept) REFERENCES department (dept_ID),
  CHECK (position IN ('manager', 'supervisor', 'guard', 'cleaner', 'repairman', 'accountant', 'receptionist'))##工作人员的职位
);

CREATE TABLE subarea
(
  subarea_id CHAR(20) PRIMARY KEY
);

CREATE TABLE building
(
  building_id  CHAR(20) PRIMARY KEY,
  subarea_id   CHAR(20),
  max_floor    INT,
  max_room_num INT,
  FOREIGN KEY (subarea_id) REFERENCES subarea (subarea_id)
);

# CREATE TABLE facilityType
# (
# 	facilityType VARCHAR(20) PRIMARY KEY
# );

CREATE TABLE facilities
(
  fclt_id     CHAR(20) PRIMARY KEY,
  fclt_type   VARCHAR(20), ##注意，此处应用自建类型加以枚举指定或用外键约束
  subarea_id  CHAR(20),
  building_id CHAR(20),
  floor       INT,
  location    VARCHAR(20),
  #FOREIGN KEY (fclt_type) REFERENCES facilityType (facilityType),
  FOREIGN KEY (building_id) REFERENCES building (building_ID),
  FOREIGN KEY (subarea_id) REFERENCES subarea (subarea_id)
  #CHECK (fclt_type IN ('electricity', 'pipe')) ##将设施制定为电器和管道两种类型
);

CREATE TABLE overhaulRecord
(
  overhaul_id      CHAR(20) PRIMARY KEY,
  fclt_id          CHAR(20),
  overhaul_type    VARCHAR(20),
  overhaul_time    DATETIME,
  overhaul_handler CHAR(20),
  overhaul_result  VARCHAR(1000),
  FOREIGN KEY (fclt_id) REFERENCES facilities (fclt_id),
  FOREIGN KEY (overhaul_handler) REFERENCES staff (staff_ID)
  #CHECK (overhaul_type IN ('electricity', 'pipe')) ##将检修设施制定为电器和管道两种类型
);


CREATE TABLE proprietor
(
  prprt_id      CHAR(20) PRIMARY KEY,
  prprt_name    VARCHAR(20) NOT NULL,
  gender        VARCHAR(20) NOT NULL,
  tel           VARCHAR(15) NOT NULL,
  birthday      DATE,
  aprt_building CHAR(20),
  aprt_floor    INT         NOT NULL,
  aprt_room_num INT         NOT NULL,
  FOREIGN KEY (aprt_building) REFERENCES building (building_ID)
);

CREATE TABLE carpark
(
  carpark_id   CHAR(20) PRIMARY KEY,
  subarea_id   CHAR(20),
  owner_id     CHAR(20),
  plate_number CHAR(20),
  valid_term   DATE,
  FOREIGN KEY (owner_id) REFERENCES proprietor (prprt_id),
  FOREIGN KEY (subarea_id) REFERENCES subarea (subarea_id)
);

CREATE TABLE ticket
(
  ticket_id          CHAR(20) PRIMARY KEY,
  ticket_type        VARCHAR(20), ##注意，此处应用自建类型加以枚举指定或用外键约束
  ticket_time        DATETIME,
  initiator_prprt_id CHAR(20), ##二选一
  initiator_staff_id CHAR(20), ##二选一
  subarea_id         CHAR(20),
  aprt_building      CHAR(20),
  aprt_floor         INT,
  aprt_room_num      INT,
  description        VARBINARY(1000),
  handler_id         CHAR(20),
  handle_time        DATETIME,
  ticket_result      VARCHAR(1000),
  ticket_fdbk        INT, ## 注意，此处定义评价等级映射关系
  FOREIGN KEY (initiator_prprt_id) REFERENCES proprietor (prprt_id),
  FOREIGN KEY (initiator_staff_id) REFERENCES staff (staff_id),
  FOREIGN KEY (subarea_id) REFERENCES subarea (subarea_id),
  FOREIGN KEY (aprt_building) REFERENCES building (building_id),
  FOREIGN KEY (handler_id) REFERENCES staff (staff_ID),
  CHECK (ticket_type IN ('electricity', 'pipe', 'guard', 'clean'))
);

CREATE TABLE dailyTask
(
  task_id      CHAR(20) PRIMARY KEY,
  task_type    VARCHAR(20), ##注意，此处应用自建类型加以枚举指定或用外键约束
  task_time    DATETIME,
  task_area    CHAR(20),
  task_handler CHAR(20),
  task_result  VARCHAR(1000),
  isException  BOOL,
  FOREIGN KEY (task_handler) REFERENCES staff (staff_ID),
  FOREIGN KEY (task_area) REFERENCES subarea (subarea_id),
  CHECK (task_type IN ('guard', 'clean', 'overhaul'))
);

CREATE TABLE buildingEntranceRecord ##单向门禁
(
  entrance_record_id CHAR(20) PRIMARY KEY,
  prprt_id           CHAR(20),
  building_id        CHAR(20),
  access_time        DATETIME,
  verify_type        VARCHAR(20),
  FOREIGN KEY (prprt_id) REFERENCES proprietor (prprt_id),
  FOREIGN KEY (building_id) REFERENCES building (building_id)
);


CREATE TABLE chargingItem
(
  item_id    CHAR(20) PRIMARY KEY,
  item_title VARCHAR(50)
  #PRIMARY KEY (item_id, item_title)
);

CREATE TABLE fee
(
  fee_id     CHAR(20) PRIMARY KEY,
  item_id    CHAR(20),
  start_date DATE NOT NULL,
  end_date   DATE NOT NULL,
  price      DECIMAL(11, 2),
  FOREIGN KEY (item_id) REFERENCES chargingItem (item_id)
);

CREATE TABLE chargingSituation
(
  chargingSituation_id CHAR(20) PRIMARY KEY,
  fee_id               CHAR(20),
  prprt_id             CHAR(20),
  collector_id         CHAR(20),
  charge_date          DATE,
  FOREIGN KEY (fee_id) REFERENCES fee (fee_id),
  FOREIGN KEY (prprt_id) REFERENCES proprietor (prprt_id),
  FOREIGN KEY (collector_id) REFERENCES staff (staff_ID)
);

CREATE TABLE carIORecord
(
  io_record_id    CHAR(20) PRIMARY KEY,
  plate_number    CHAR(20),
  prprt_id        CHAR(20), ##可为空，代表外部车辆
  record_in_time  DATETIME,
  record_out_time DATETIME,
  price           DECIMAL(5, 2),
  FOREIGN KEY (prprt_id) REFERENCES proprietor (prprt_id)
);

CREATE TABLE suggestion
(
  suggestion_id     CHAR(20) PRIMARY KEY,
  prprt_id          CHAR(20),
  suggestion_type   VARCHAR(20),
  suggestion_detail VARCHAR(1000),
  FOREIGN KEY (prprt_id) REFERENCES proprietor (prprt_id)
);

# INSERT INTO subarea
# VALUES (0);
# INSERT INTO building
# VALUES (1, 0, '测试建筑', 10, 10);
# INSERT INTO proprietor
# VALUES (2, '测试用户', '男', '100000000', 19000101, 1, 2, 3);
# INSERT INTO carIORecord
# VALUES (3, 'TESTTEST', NULL, '进', 20000101032512);
#
#
#
# SELECT *
# FROM carIORecord;

DROP TABLE IF EXISTS UserRole;
DROP TABLE IF EXISTS RolePermission;
DROP TABLE IF EXISTS UserInfo;
DROP TABLE IF EXISTS SysPermission;
DROP TABLE IF EXISTS SysRole;

CREATE TABLE SysRole
(
  role_id      INT PRIMARY KEY,
  role_name    VARCHAR(50),
  descryption  VARCHAR(100),
  is_available BOOL
);

CREATE TABLE SysPermission
(
  perm_id       INT PRIMARY KEY,
  perm_name     VARCHAR(100),
  resource_type VARCHAR(20),
  permit_url    VARCHAR(50),
  permission    VARCHAR(100),
  parent_id     INT,
  parent_ids    VARCHAR(50),
  is_available  BOOL
);

CREATE TABLE UserInfo
(
  uid           INT PRIMARY KEY,
  user_name     VARCHAR(20),
  display_name  VARCHAR(20),
  user_password CHAR(255),
  salt          CHAR(255),
  state         INT
);

CREATE TABLE RolePermission
(
  role_id INT,
  perm_id INT,
  PRIMARY KEY (role_id, perm_id),
  FOREIGN KEY (role_id) REFERENCES SysRole (role_id),
  FOREIGN KEY (perm_id) REFERENCES SysPermission (perm_id)
);

CREATE TABLE UserRole
(
  uid     INT,
  role_id INT,
  PRIMARY KEY (uid, role_id),
  FOREIGN KEY (uid) REFERENCES UserInfo (uid),
  FOREIGN KEY (role_id) REFERENCES SysRole (role_id)
);

# INSERT INTO UserInfo
# VALUES (1, 'Test', 'Test-<', '49a4afbc2862d18ad095e095d717e43485ae89d3b6e249239deb041b0d0bbcb1d24250128037d6b853272d8c595c60f10edf00be9efc7c1c315f5169dc8667ed', 'C14E16225482E32714D3831E3E927DE2DDB4F9BF2DB21', 1);
# INSERT INTO SysPermission
# VALUES (1, 'ticket', 'ticket-<', '/ticket/list','ticket:view', 0, '0', TRUE);
# INSERT INTO SysRole
# VALUES (1, 'test', 'test->', 1);
# INSERT INTO UserRole
# VALUES (1, 0);


INSERT INTO UserInfo
VALUES (0, 'Manager', 'Manager',
        'a2c0b51253b7f4d21677f74b0f1d700cbbc9f059d79b6a2f19fa3b260646ef385ba3bf6d2bd7e3d253eabd5573b6e04cbb426bc31dff543f59031365d1a8d655',
        'UV5AN7aFWXQ2wm5yWZjhO3SfRGYzw5BPDY0E7nzUXYjkvZG5WppHXxxO4vAad3ausThdSp1zAIyluHwS14eq0iw7gdOm7NbgMEVq8VyCWsfH146UGJGpLfy0il7Spmde',
        TRUE);
INSERT INTO UserRole
VALUES (0, 0);
INSERT INTO UserInfo
VALUES (1, 'Supervisor', 'Supervisor',
        '912f07a7f382a6d092dc9ad42d598cfcba8a4655a023c6eebdc222f098138e22c25afae642ce294b4c3b6414a6fd432d227815426dfbac08d4269da2b2eda5e7',
        'rKtn5AqKzxWJdAXCyjRGDQ60zpYrRzpP2kDMnAFeZxA3xESy3Hskqtq1BxUEdiq6xiuEMZz5U8PyTeONwtxnygNqnbX6V1Ra13y1nzMQh5dnYfJgEQBi1nMp5n87gz2Y',
        TRUE);
INSERT INTO UserRole
VALUES (1, 1);
INSERT INTO UserInfo
VALUES (2, 'Guard', 'Guard',
        '780fb9cc8660100e9115b03a81686e72b19582e39beb2a4cd525504f125da7a8ded23f6deeeb8422e0e7a3438c0ec1e08e090aaef95cdda75fd073caeaeb34a4',
        'TJjHgzb3l5jeCrqTjWydkB1NiHqvvyWicwIbzNQ2lsezDeDZ4w3dqVB6dJhsgi4FxCNXam0MJLrEAUNglOBO5P8MgnePhSNZ0IYsL28tcFIJmQXxqrs0mZzWqgLIrGVn',
        TRUE);
INSERT INTO UserRole
VALUES (2, 2);
INSERT INTO UserInfo
VALUES (3, 'Cleaner', 'Cleaner',
        '0109be1ac0616468110ab2f67df33fabbb5eac3b8dbea4a4ce2b4acd346f396ac175d5f7a942df17e5a433b65cdd4273e55e5faa847eaa9cbcb848e71ad032d5',
        '0cOQeoFA5ynmrEA5Y8qNUV77vaVLM51pne6wPv7511ixdK36u3UMwmiUWDzyjI32SpOyXLiP21yWFGFM2wFhYZmFvsyYwWa11ofaHkyL7tbyjtrDn4JhGcFTMfJ8X4iH',
        TRUE);
INSERT INTO UserRole
VALUES (3, 3);
INSERT INTO UserInfo
VALUES (4, 'Repairman', 'Repairman',
        '283106e544b2b8bc520bb52beaaac61828a86b865c7d81b729f49955db8a00f985651700400376831d25eb77d382c316434def6e5f08cf9dba1fc68ab22380ff',
        'Rc6kN4LDx7pXTHgEdvNJLNuMcqBPCH6CCboOMmFrzY0LQt2boBuvJiiwwyJiP8iFzbWwADkDoXgOsXj0rsCDQr2t1SLGOhUorrCbjz4EEIB0y0WLsCVpLvbyA4royG5M',
        TRUE);
INSERT INTO UserRole
VALUES (4, 4);
INSERT INTO UserInfo
VALUES (5, 'Accountant', 'Accountant',
        '08f46c0cddcea02d0922a0e22da472461314094e7f86d1e980c324286c382b09f6debeee9d8aa0764c5fded4ea1ad070b38660809a647d541a47487fb51e67ff',
        'rrFgX17248pcd4kRoKwNsiH3LdJP4QcfFXDJ0PldolAm3GT10hR3dEPVbAmtlZWTX8nC5sSlJPqZTY8WovT8EXhvVstQY1RnRkZBRjh2HeXFCDoGfkO6rl1wmN4o0BMk',
        TRUE);
INSERT INTO UserRole
VALUES (5, 5);
INSERT INTO UserInfo
VALUES (6, 'Receptionist', 'Receptionist',
        '4170b77d4b45011e00bd166363a392b655405fa94e702f40787369a94ac403c5b6e2659f6562721cab964d799f597ff8b37c380ca33231a4b99bbb93f83a3371',
        'pGODVmDylrDPsCuolsbYuCkFLOaigAsKy564yy72gHkmUr6XO6tiVEFpEzYmfdXvmZ8yQoyhDVmTcQhPgN2E6pgDHupqC8rhOWKep3HgTwRAC6CN2b1IFSsjaQusPm3D',
        TRUE);
INSERT INTO UserRole
VALUES (6, 6);
INSERT INTO UserInfo
VALUES (7, 'Proprietor', 'Proprietor',
        '4c9e4e258df71a0252b287d6927fecb3f0c1f78ea23092c311158887dff322422a5873cd7599b16de47669dfa140905c3b0c10c19dc897b9a8cfb5c091a562d7',
        'rls6T0UkX7Sttc5vqPoWk3uCM8DprHWc1pkMa5NGV2HEAQtDpgOEeFECkQ2RKYxEYPQujTaffIYsSljC4Go1OCJtnhF5UJxZFm3VdGwSvMPQM85yhTPqu2OniYBXM7ZC',
        TRUE);
INSERT INTO UserRole
VALUES (7, 7);


USE propertymanagement;
UPDATE UserInfo
SET staff_id = 'SF1723219020'
WHERE uid = 6;

UPDATE UserInfo
SET staff_id = 'SF1939583739'
WHERE uid = 5;

UPDATE UserInfo
SET staff_id = 'SF1040003242'
WHERE uid = 4;

UPDATE UserInfo
SET staff_id = 'SF2131836294'
WHERE uid = 3;

UPDATE UserInfo
SET staff_id = 'SF684900388'
WHERE uid = 2;

UPDATE UserInfo
SET staff_id = 'SF1939583739'
WHERE uid = 1;

UPDATE UserInfo
SET staff_id = 'SF907234094'
WHERE uid = 0;

UPDATE UserInfo
SET prprt_id = 'P1058531497'
WHERE uid = 7;


UPDATE chargingSituation
SET charge_date = NULL
WHERE prprt_id = 'P1000931623';


SELECT count(*)
FROM (SELECT *
      FROM chargingSituation
      WHERE collector_id = -1) as S;

show TABLES