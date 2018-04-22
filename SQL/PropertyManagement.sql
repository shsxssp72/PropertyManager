DROP DATABASE PropertyManagement;
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
  CHECK (position IN ('manager', 'supervisor', 'guard', 'cleaner', 'repairman', 'accountant'))##工作人员的职位
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
  FOREIGN KEY (subarea_id) REFERENCES subarea (subarea_id),
  CHECK (fclt_type IN ('electricity', 'pipe')) ##将设施制定为电器和管道两种类型
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
  carpark_id CHAR(20) PRIMARY KEY,
  subarea_id CHAR(20),
  owner_id   CHAR(20),
  valid_term DATE,
  FOREIGN KEY (owner_id) REFERENCES proprietor (prprt_id),
  FOREIGN KEY (subarea_id) REFERENCES subarea (subarea_id)
);

CREATE TABLE ticket
(
  ticket_id     CHAR(20) PRIMARY KEY,
  ticket_type   VARCHAR(20), ##注意，此处应用自建类型加以枚举指定或用外键约束
  ticket_time   DATETIME,
  initiator_id  CHAR(20),
  handler_id    CHAR(20),
  handle_date   DATE,
  handle_time   TIME,
  ticket_result VARCHAR(1000),
  ticket_fdbk   INT, ## 注意，此处定义评价等级映射关系
  FOREIGN KEY (initiator_id) REFERENCES proprietor (prprt_id),
  FOREIGN KEY (handler_id) REFERENCES staff (staff_ID),
  CHECK (ticket_type IN ('electricity', 'pipe')) ##将报修设施制定为电器和管道两种类型
);

CREATE TABLE dailyTask
(
  task_id      CHAR(20) PRIMARY KEY,
  task_type    VARCHAR(20), ##注意，此处应用自建类型加以枚举指定或用外键约束
  task_time    DATETIME,
  task_area    CHAR(20),
  task_handler CHAR(20),
  task_result  VARCHAR(1000),
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
  fee_id       CHAR(20),
  prprt_id     CHAR(20),
  collector_id CHAR(20),
  charge_date  DATE,
  PRIMARY KEY (fee_id, prprt_id),
  FOREIGN KEY (fee_id) REFERENCES fee (fee_id),
  FOREIGN KEY (prprt_id) REFERENCES proprietor (prprt_id),
  FOREIGN KEY (collector_id) REFERENCES staff (staff_ID)
);

CREATE TABLE carIORecord
(
  io_record_id CHAR(20) PRIMARY KEY,
  plate_number CHAR(20),
  prprt_id     CHAR(20), ##可为空，代表外部车辆
  record_type  CHAR(5), ##进或出
  record_time  DATETIME,
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

SHOW TABLES;