import com.alibaba.druid.pool.DruidDataSource;
import com.yuudati.bookmanager.entity.BookInfo;
import com.yuudati.bookmanager.mapper.BookInfoMapper;
import com.yuudati.bookmanager.util.SpringContext;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @Author Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/15 16:11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ApplicationContext.xml"})
public class SQLiteJDBC {

    private SqlSession sqlSession;

//    @Before
//    public void init() throws IOException {
//        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
//        SqlSessionFactory sessionFactory = builder.build(Resources.getResourceAsStream("mybatis-config.xml"));
//        sqlSession = sessionFactory.openSession();
//    }


    @Autowired
    private BookInfoMapper infoMapper;

    @Test
    public void select(){
        final BookInfo bookInfo = infoMapper.selectByPrimaryKey("111111");
//        System.out.println(bookInfo);
//        final BookInfoMapper bookInfoMapper = (BookInfoMapper) SpringContext.getContext().getBean("bookInfoMapper");
        System.out.println(infoMapper.selectByPrimaryKey("111111").toString());
        final int insert = infoMapper.insert(new BookInfo(new File("E:\\迅雷下载\\C87全彩，非全彩汉化本分类合集第十弹（33本，1.25G，3.5-3.19）\\全彩（4）\\【CE家族社】(C87) [ヒツジ企画 (HITSUJI)] ヤザワックス (ラブライブ!).rar"), "c87", "HITSUJI", "jsjssj", "sfsdfsdf", "sdfsfsf", "E:\\迅雷下载\\C87全彩，非全彩汉化本分类合集第十弹（33本，1.25G，3.5-3.19）\\全彩（4）\\【CE家族社】(C87) [ヒツジ企画 (HITSUJI)] ヤザワックス (ラブライブ!).rar"));
        System.out.println(insert);

    }

//    @After
//    public void close(){
//        sqlSession.close();
//    }

    public DataSource initHikariCP() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(100);
        config.setDataSourceClassName("org.sqlite.JDBC");
        config.addDataSourceProperty("databaseName", "book_manager.db");
        config.setConnectionTestQuery("SELECT 1");
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    public DataSource initDruid(){

        DruidDataSource dataSource = new DruidDataSource();
        return null;
    }

}
