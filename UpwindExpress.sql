DROP DATABASE IF EXISTS Upwind_Express;
CREATE DATABASE Upwind_Express DEFAULT CHARACTER SET utf8;

use Upwind_Express;

CREATE TABLE consumer (
  id int(10) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  phone varchar(11) NOT NULL COMMENT '用户手机号/账号',
  password varchar(25) NOT NULL COMMENT '账号密码',
  name varchar(10) NOT NULL COMMENT '用户姓名',
  gender varchar(5) NOT NULL COMMENT '用户性别',
  identity_num varchar(18) DEFAULT NULL COMMENT '用户身份证号',
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE address (
  id int(10) NOT NULL AUTO_INCREMENT COMMENT '地址id',
  province varchar(20) NOT NULL COMMENT '省',
  city varchar(20) NOT NULL COMMENT '市',
  district varchar(20) NOT NULL COMMENT '县/区',
  detail_addr varchar(50) NOT NULL COMMENT '详细地址',
  is_default int(1) NOT NULL COMMENT '是否设为默认',
  consumer_id int(10) NOT NULL COMMENT '用户id',
  PRIMARY KEY(id),
  CONSTRAINT fk_consumer_address FOREIGN KEY (consumer_id) REFERENCES consumer (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE outlet (
  id int(10) NOT NULL AUTO_INCREMENT COMMENT '网点id',
  title varchar(100) NOT NULL COMMENT '网点名称',
  phone varchar(11) NOT NULL COMMENT '负责人手机号/账号',
  password varchar(25) NOT NULL COMMENT '账号密码',
  name varchar(10) NOT NULL COMMENT '负责人姓名',
  province varchar(20) NOT NULL COMMENT '省',
  city varchar(20) NOT NULL COMMENT '市',
  district varchar(20) NOT NULL COMMENT '县/区',
  detail_addr varchar(50) NOT NULL COMMENT '详细地址',
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE courier (
  id int(10) NOT NULL AUTO_INCREMENT COMMENT '快递员id',
  job_no varchar(11) DEFAULT NULL COMMENT '工号',
  phone varchar(11) NOT NULL COMMENT '快递员手机号/账号',
  password varchar(25) NOT NULL COMMENT '账号密码',
  name varchar(10) NOT NULL COMMENT '快递员姓名',
  gender varchar(5) NOT NULL COMMENT '快递员性别',
  identity_num varchar(18) DEFAULT NULL COMMENT '快递员身份证号',
  approved_flag int(1) NOT NULL COMMENT '审核是否通过',
  outlet_id int(10) NOT NULL COMMENT '网点id',
  PRIMARY KEY(id),
  CONSTRAINT fk_outlet_courier FOREIGN KEY (outlet_id) REFERENCES outlet (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE sendwise (
  id int(10) NOT NULL AUTO_INCREMENT COMMENT '寄件信息id',
  province varchar(20) NOT NULL COMMENT '省',
  city varchar(20) NOT NULL COMMENT '市',
  district varchar(20) NOT NULL COMMENT '县/区',
  detail_addr varchar(50) NOT NULL COMMENT '详细地址',
  sender_id int(10) NOT NULL COMMENT '寄件人id',
  courier_id int(10) DEFAULT NULL COMMENT '收寄快递员id',
  PRIMARY KEY(id),
  CONSTRAINT fk_consumer_sendwise FOREIGN KEY (sender_id) REFERENCES consumer (id),
  CONSTRAINT fk_courier_sendwise FOREIGN KEY (courier_id) REFERENCES courier (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE receivewise (
  id int(10) NOT NULL AUTO_INCREMENT COMMENT '收件信息id',
  province varchar(20) NOT NULL COMMENT '省',
  city varchar(20) NOT NULL COMMENT '市',
  district varchar(20) NOT NULL COMMENT '县/区',
  detail_addr varchar(50) NOT NULL COMMENT '详细地址',
  receiver_id int(10) NOT NULL COMMENT '收件人id',
  courier_id int(10) DEFAULT NULL COMMENT '派件快递员id',
  PRIMARY KEY(id),
  CONSTRAINT fk_consumer_receivewise FOREIGN KEY (receiver_id) REFERENCES consumer (id),
  CONSTRAINT fk_courier_receivewise FOREIGN KEY (courier_id) REFERENCES courier (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE express (
  id int(10) NOT NULL AUTO_INCREMENT COMMENT '快递id',
  order_no varchar(11) DEFAULT NULL COMMENT '快递单号',
  category varchar(20) NOT NULL COMMENT '物品类型',
  weight float(7,3) DEFAULT NULL COMMENT '快递重量',
  freight float(7,2) DEFAULT NULL COMMENT '快递运费',
  status varchar(10) NOT NULL COMMENT '快递状态',
  order_time timestamp(3) NOT NULL COMMENT '下单时间',
  send_time timestamp(3) DEFAULT NULL COMMENT '收寄时间',
  receive_time timestamp(3) DEFAULT NULL COMMENT '签收时间',
  sendwise_id int(10) NOT NULL COMMENT '寄件信息id',
  receivewise_id int(10) NOT NULL COMMENT '收件信息id',
  PRIMARY KEY(id),
  CONSTRAINT fk_express_sendwise FOREIGN KEY (sendwise_id) REFERENCES sendwise (id),
  CONSTRAINT fk_express_receivewise FOREIGN KEY (receivewise_id) REFERENCES receivewise (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE manager (
  id int(10) NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  account varchar(11) DEFAULT NULL COMMENT '管理员账号',
  password varchar(25) NOT NULL COMMENT '账号密码',
  PRIMARY KEY (id)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8