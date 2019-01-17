package com.yuudati.bookmanager.util;

import com.yuudati.bookmanager.entity.Book;
import com.yuudati.bookmanager.entity.BookCharacters;
import com.yuudati.bookmanager.mapper.BookCharactersMapper;
import com.yuudati.bookmanager.mapper.BookInfoMapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        if (context == null) {
            synchronized (SpringContext.class){
                if (context == null){
                    context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
                }
            }
        }
        return context;
    }

    public static BookInfoMapper getBookInfoMapper(){
        return (BookInfoMapper) getContext().getBean("bookInfoMapper");
    }

    public static BookCharactersMapper getBookCharactersMapper(){
        return (BookCharactersMapper) getContext().getBean("bookCharactersMapper");
    }

    public static Object getBean(Class clazz){
        return getContext().getBean(clazz);

    }
}
