package cn.poolify.admin.alarm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: HCJ
 * @DateTime: 2024/9/22
 * @Description: 发送消息实体
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmMessage {

    private MessageContent messageContent;

    private String remarks;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MessageContent {
        /**
         * 应用名
         */
        private String applicationName;
        /**
         * 线程池名称，用于标识数据属于哪个线程池
         */
        private String threadPoolName;

        /**
         * 当前线程池中正在执行任务的活跃线程数
         */
        private Integer activeThreadCount;
        /**
         * 核心线程数
         */
        private Integer corePoolSize;
        /**
         * 总线程数
         */
        private Integer maximumPoolSize;

        /**
         * 当前线程池任务队列中等待执行的任务数
         */
        private Integer queueSize;
    }

    public static String buildMessageContent(AlarmMessage message) {
        StringBuilder content = new StringBuilder();
        String remarks = message.getRemarks();
        MessageContent messageContent = message.getMessageContent();
        content.append("【监控告警】 ").append("\n")
                .append(" ============").append("\n");
        content.append(" 应用名称: ").append(messageContent.getApplicationName()).append("\n")
                .append(" 线程池名称: ").append(messageContent.getThreadPoolName()).append("\n")
                .append(" 核心线程数: ").append(messageContent.getCorePoolSize()).append("\n")
                .append(" 最大线程数: ").append(messageContent.getMaximumPoolSize()).append("\n")
                .append(" 活跃线程数: ").append(messageContent.getActiveThreadCount()).append("\n")
                .append(" 队列中任务数: ").append(messageContent.getQueueSize()).append("\n");
        if (remarks != null) {
            content.append(" ").append(remarks).append("\n");
        }
        return content.toString();
    }
}
