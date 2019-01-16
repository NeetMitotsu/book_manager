import java.io.File;

/**
 * @Author Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/10 14:58
 */
public class TestFile {
    public static void main(String[] args) {
        final File file = new File("E:\\aaaaaaaaaa\\(c87)_[测试作者]_测试名字_(ACG)_[中国翻译](1).rar");
        System.out.println(file.getPath());
        System.out.println(file.getParent());
    }
}
