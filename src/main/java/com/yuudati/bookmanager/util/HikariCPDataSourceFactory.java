package com.yuudati.bookmanager.util;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

/**
 * @Author Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/16 14:22
 */
public class HikariCPDataSourceFactory extends UnpooledDataSourceFactory {

    public HikariCPDataSourceFactory(){
        this.dataSource = new HikariDataSource();
    }

}
