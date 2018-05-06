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
  buidling_id        CHAR(20),
  access_time        DATETIME,
  verify_type        VARCHAR(20),
  FOREIGN KEY (prprt_id) REFERENCES proprietor (prprt_id),
  FOREIGN KEY (buidling_id) REFERENCES building (building_id)
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
  permission VARCHAR(100),
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

INSERT INTO UserInfo
VALUES (1, 'Test', 'Test-<', '49a4afbc2862d18ad095e095d717e43485ae89d3b6e249239deb041b0d0bbcb1d24250128037d6b853272d8c595c60f10edf00be9efc7c1c315f5169dc8667ed', 'C14E16225482E32714D3831E3E927DE2DDB4F9BF2DB21', 1);
# INSERT INTO SysPermission
# VALUES (1, 'ticket', 'ticket-<', '/ticket/list','ticket:view', 0, '0', TRUE);
# INSERT INTO SysRole
# VALUES (1, 'test', 'test->', 1);
INSERT INTO UserRole
VALUES (1, 0);


UPDATE UserInfo
SET user_password='49a4afbc2862d18ad095e095d717e43485ae89d3b6e249239deb041b0d0bbcb1d24250128037d6b853272d8c595c60f10edf00be9efc7c1c315f5169dc8667ed',salt='C14E16225482E32714D3831E3E927DE2DDB4F9BF2DB21',user_name='Test'
WHERE uid=1;

SELECT * FROM SysRole JOIN UserRole Role ON SysRole.role_id = Role.role_id;

show tables;