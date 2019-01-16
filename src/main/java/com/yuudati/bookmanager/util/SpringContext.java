package com.yuudati.bookmanager.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 取得Spring上下文
 * @Author Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/16 17:13
 */
public class SpringContext {

    protected static ApplicationContext context;

    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext(){
        return context;
    }
}
