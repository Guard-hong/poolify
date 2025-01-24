## 消息发送

1. notifier: 用于消息发送.通过url、token和发送消息内容context来作为参数
2. assembler: 用于消息装配.
   1. alarm: 提醒消息,用于线程池参数超过阈值时进行告警.
   2. notice: 通知消息,用于线程池参数修改时发出通知.
3. transmitter: 关联2者,消息构建、发送等操作.

alarm: 线程池
notice: 改变前的参数、改变后的参数、差异字段

### assembler

消息需要设置不同的告警颜色。