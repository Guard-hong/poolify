package cn.poolify.core.constants;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/24
 * @Description:
 **/
public interface DefaultAssemblerConstants {

    String ALARM_TEMPLATE =
            "【报警】 动态线程池运行告警 \n\n" +
                    "服务名称：%s \n\n" +
                    "线程池名称：%s \n\n" +
                    "核心线程数：%d \n\n" +
                    "最大线程数：%d \n\n" +
                    "当前线程数：%d \n\n" +
                    "活跃线程数：%d \n\n" +
                    "历史最大线程数：%d \n\n" +
                    "任务总数：%d \n\n" +
                    "执行完成任务数：%d \n\n" +
                    "队列类型：%s \n\n" +
                    "队列任务数量：%d \n\n" +
                    "队列剩余容量：%d \n\n" +
                    "拒绝策略：%s \n\n" +
                    "总拒绝任务数量：%s \n\n" +
                    "总执行超时任务数量：%s \n\n" +
                    "总等待超时任务数量：%s \n\n";
    String NOTICE_TEMPLATE =
            "【通知】 动态线程池参数变更 \n" +
                    "> 服务名称：%s \n" +
                    "> 线程池名称：%s \n" +
                    "> 核心线程数：%s => %s \n" +
                    "> 最大线程数：%s => %s \n" +
                    "> 允许核心线程超时：%s => %s \n" +
                    "> 线程存活时间：%ss => %ss \n" +
                    "> 运行超时时间：%s => %s \n" +
                    "> 队列超时时间：%s => %s \n";
}
