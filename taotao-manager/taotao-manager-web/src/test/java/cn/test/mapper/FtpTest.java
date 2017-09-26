package cn.test.mapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.taotao.common.utils.FtpUtil;

public class FtpTest {

    @Test
    public void fun1() throws Exception {
        FTPClient client = new FTPClient();
        client.connect("192.168.2.133", 21);
        client.login("ftpuser", "ftpuser");
        // 读取本地文件,更改要上传的地方
        FileInputStream inputStream = new FileInputStream(new File("E:\\lufei.jpg"));
        client.changeWorkingDirectory("/home/ftpuser/www/images");
        client.setFileType(FTP.BINARY_FILE_TYPE);
        client.storeFile("hello1.jpg", inputStream);
        client.logout();
    }

    @Test
    public void fun2() throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(new File("E:\\lufei.jpg"));
        FtpUtil.uploadFile("192.168.2.133", 21, "ftpuser", "ftpuser", "/home/ftpuser/www/images",
                "2016/2/29", "hello.jpg", inputStream);
    }
}
