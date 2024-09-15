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
