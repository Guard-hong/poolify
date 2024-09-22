DROP DATABASE IF EXISTS poolify;
create database poolify;
use poolify;
DROP TABLE IF EXISTS thread_pool_runtime_status;
CREATE TABLE thread_pool_runtime_status (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '唯一标识符，主键，自动递增',
                                            application_name VARCHAR(255) NOT NULL COMMENT '应用名称，用于标识数据属于哪个应用',
                                            thread_pool_name VARCHAR(255) NOT NULL COMMENT '线程池名称，用于标识数据属于哪个线程池',
                                            active_thread_count INT COMMENT '当前线程池中正在执行任务的活跃线程数',
                                            queue_size INT COMMENT '当前线程池任务队列中等待执行的任务数',
                                            completed_task_count BIGINT COMMENT '线程池中已完成的任务总数',
                                            timestamp DATETIME COMMENT '记录状态的时间戳'
) COMMENT='线程池运行状态记录表';

DROP TABLE IF EXISTS thread_pool_config;
CREATE TABLE thread_pool_config (
                                    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键，自增 ID',       -- 主键，自增 ID
                                    registry_type varchar(10) NOT NULL COMMENT '注册中心类型',
                                    addr VARCHAR(255) NOT NULL COMMENT '地址字段',                      -- 地址字段
                                    application_name VARCHAR(255) NOT NULL COMMENT '应用名称',          -- 应用名称
                                    thread_pool_name VARCHAR(255) NOT NULL COMMENT '线程池名称',        -- 线程池名称
                                    core_pool_size INT NOT NULL COMMENT '核心线程池大小',              -- 核心线程池大小
                                    maximum_pool_size INT NOT NULL COMMENT '最大线程池大小',               -- 最大线程池大小
                                    queue_type VARCHAR(100) NOT NULL COMMENT '队列类型',               -- 队列类型
                                    queue_capacity INT NULL COMMENT '队列容量',                    -- 队列容量
                                    keep_alive_time INT NULL COMMENT '存活时间',                   -- 存活时间
                                    keep_alive_time_unit TINYINT NULL COMMENT '存活时间单位',       -- 存活时间单位
                                    rejected_policy VARCHAR(100) NULL COMMENT '拒绝策略',           -- 拒绝策略
                                    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间', -- 创建时间
                                    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' -- 更新时间
) COMMENT '线程池配置表';

DROP TABLE IF EXISTS thread_pool_adjust_log;
CREATE TABLE thread_pool_adjust_log (
                                    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键，自增 ID',
                                    thread_pool_config_id BIGINT NOT NULL COMMENT '线程池配置id',
                                    before_adjust_config VARCHAR(255) NOT NULL COMMENT '调整前配置',
                                    adjust_config VARCHAR(255) NOT NULL COMMENT '调整后配置',
                                    state tinyint NOT NULL COMMENT '线程池调整状态',
                                    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' -- 更新时间
) COMMENT '线程池调整日志表';