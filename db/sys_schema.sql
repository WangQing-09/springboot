-- CREATE DATABASE springboot DEFAULT CHARACTER SET utf8;

DROP TABLE IF EXISTS tb_user;
DROP TABLE IF EXISTS tb_goods;

-- 用户表
CREATE TABLE tb_user(
  user_Id BIGINT AUTO_INCREMENT COMMENT '编号' NOT NULL,
  user_name VARCHAR(100) COMMENT '用户名' DEFAULT NULL,
  password VARCHAR(100) COMMENT '密码' DEFAULT NULL,
  salt VARCHAR(100) COMMENT '盐' DEFAULT NULL,
  CONSTRAINT pk_sys_user PRIMARY KEY(user_Id)
) CHARSET=utf8 ENGINE=InnoDB;

INSERT INTO tb_user VALUES(1, 'tycoding', '5a1e3a5aede16d438c38862cac1a78db', 2);
INSERT INTO tb_user VALUES(2, '涂陌', '123',NOT NULL);
INSERT INTO tb_user VALUES(3, 'zhangsan', '86fb1b048301461bdc71d021d2af3f97', 1);

-- 角色表
CREATE TABLE tb_role(
    role_id BIGINT(20) NOT NULL ,
    role_name VARCHAR(30) DEFAULT NULL,
    PRIMARY KEY (role_id)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO tb_role VALUES (1, '管理员');
INSERT INTO tb_role VALUES (2, '普通用户');

-- 用户-角色表
CREATE TABLE tb_user_role (
    user_Id BIGINT(20) NOT NULL,
    role_Id BIGINT(20) NOT NULL,
    PRIMARY KEY (user_Id, role_Id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO tb_user_role VALUES (1, 1);
INSERT INTO tb_user_role VALUES (3, 2);

SET FOREIGN_KEY_CHECKS = 1;

-- 权限表
CREATE TABLE tb_permission (
    id BIGINT(20) NOT NULL,
    parent_id BIGINT(20) DEFAULT NULL,
    res_name VARCHAR(50) DEFAULT NULL,
    res_type VARCHAR(10) DEFAULT NULL,
    permission VARCHAR(20) DEFAULT NULL,
    url VARCHAR(100) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO tb_permission VALUES (1,NULL,'系统管理','menu','system',NULL);
INSERT INTO tb_permission VALUES (2,1,'用户管理','menu','systemUserList','/user/userList');
INSERT INTO tb_permission VALUES (3,1,'角色管理','menu','systemRole','/roles');
INSERT INTO tb_permission VALUES (4,NULL,'一级菜单','menu','menu',NULL);
INSERT INTO tb_permission VALUES (5,4,'二级菜单1','menu','menuXxx','/xxx');
INSERT INTO tb_permission VALUES (6,4,'二级菜单2','menu','menuYyy','/yyy');
INSERT INTO tb_permission VALUES (7,2,'用户添加','button','systemUserAdd',NULL);

-- 角色权限表
CREATE TABLE tb_role_permission (
    role_id BIGINT(20) NOT NULL,
    permission_id BIGINT(20) NOT NULL,
    PRIMARY KEY (role_id,permission_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO tb_role_permission VALUES (1,1);
INSERT INTO tb_role_permission VALUES (1,2);
INSERT INTO tb_role_permission VALUES (1,3);
INSERT INTO tb_role_permission VALUES (1,4);
INSERT INTO tb_role_permission VALUES (1,5);
INSERT INTO tb_role_permission VALUES (1,6);
INSERT INTO tb_role_permission VALUES (1,7);
INSERT INTO tb_role_permission VALUES (2,1);
INSERT INTO tb_role_permission VALUES (2,2);

-- 商品表
CREATE TABLE tb_goods(
  id BIGINT AUTO_INCREMENT COMMENT '编号',
  title VARCHAR(1000) COMMENT '商品标题',
  price VARCHAR(100) COMMENT '商品价格',
  image VARCHAR(1000) COMMENT '商品图片',
  brand VARCHAR(100) COMMENT '商品品牌',
  CONSTRAINT pk_sys_user PRIMARY KEY(id)
) CHARSET=utf8 ENGINE=InnoDB;
