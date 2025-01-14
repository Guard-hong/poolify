package cn.poolify.core.message.assemble;

import cn.poolify.core.executor.wrapper.ExecutorWrapper;

/**
 * @Author: HCJ
 * @DateTime: 2025/1/13
 * @Description:
 * 提供一个默认实现
 **/
public interface IAssembler {

    String assemble(ExecutorWrapper executor);
}
