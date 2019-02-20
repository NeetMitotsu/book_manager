import com.alibaba.druid.pool.DruidDataSource;
import com.yuudati.bookmanager.entity.BookInfo;
import com.yuudati.bookmanager.mapper.BookInfoMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

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
    public void testSUM() {
        Random random = new Random();
        int[] data = new int[100000000];
        {
            for (int i = 0; i < data.length; i++) {
                data[i] = random.nextInt(400);
//                data[i] = Integer.MIN_VALUE;
            }
        }
        long sumI = 0, sumJ = 0;

        long t = System.currentTimeMillis();

        for (int datum : data) {
            int i = (datum - 200) >> 31;
            sumJ += (~i & datum);
        }
        System.out.println(sumJ);
        System.out.println(System.currentTimeMillis() - t);

        t = System.currentTimeMillis();
        for (int datum1 : data) {
            if (datum1 >= 200) {
                sumI += datum1;
            }
        }
        System.out.println(sumI);
        System.out.println(System.currentTimeMillis() - t);
    }

    @Test
    public void createUUID(){
        System.out.println(Integer.MIN_VALUE - 1000);
    }

    @Test
    public void select() {
        final BookInfo bookInfo = infoMapper.selectByPrimaryKey("4681284c2f4b24546ec75a2b11efa127");
        System.out.println(bookInfo.getId());
        System.out.println(bookInfo.getArtist());
//        System.out.println(bookInfo);
//        final BookInfoMapper bookInfoMapper = (BookInfoMapper) SpringContext.getContext().getBean("bookInfoMapper");
//        final int insert = infoMapper.insert(new BookInfo(new File("E:\\迅雷下载\\C87全彩，非全彩汉化本分类合集第十弹（33本，1.25G，3.5-3.19）\\全彩（4）\\【CE家族社】(C87) [ヒツジ企画 (HITSUJI)] ヤザワックス (ラブライブ!).rar"), "c87", "HITSUJI", "jsjssj", "sfsdfsdf", "sdfsfsf", "E:\\迅雷下载\\C87全彩，非全彩汉化本分类合集第十弹（33本，1.25G，3.5-3.19）\\全彩（4）\\【CE家族社】(C87) [ヒツジ企画 (HITSUJI)] ヤザワックス (ラブライブ!).rar"));
//        System.out.println(insert);

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
        return new HikariDataSource(config);
    }

    public DataSource initDruid() {

        DruidDataSource dataSource = new DruidDataSource();
        return null;
    }

}
