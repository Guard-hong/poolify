package cn.poolify.core.properties;

import cn.poolify.core.properties.entity.DtpExecutorProps;
import lombok.Data;

import java.util.List;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/25
 * @Description:
 **/
@Data
public class DtpProperties {
    private List<DtpExecutorProps> executorProps;

}
