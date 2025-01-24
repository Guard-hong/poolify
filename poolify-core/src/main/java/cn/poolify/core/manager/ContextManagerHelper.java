/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.poolify.core.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;
import java.util.Objects;

/**
 * @Author: HCJ
 * @DateTime: 2024/12/21
 * @Description: 上下文管理
 **/
@Slf4j
public class ContextManagerHelper implements ApplicationContextAware {
    private static ApplicationContext context;

    public static String getEnvironmentProperty(String key) {
        return getInstance().getEnvironment().getProperty(key);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getInstance() {
        if (Objects.isNull(context)) {
            throw new NullPointerException("ApplicationContext is null, please check if the spring container is started.");
        }
        return context;
    }

    public static <T> T geBean(Class<T> clazz){
        return getInstance().getBean(clazz);
    }

    public static ApplicationContext getContext(){
        return context;
    }

}

