package com.liskov.gmall.manage;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class GmallManageWebApplicationTests {

    @Test
    void contextLoads() throws IOException, MyException {
        // 配置fdfs的全局信息
        String file = GmallManageWebApplicationTests.class.getClassLoader().getResource("tracker.conf").getFile();
        ClientGlobal.init(file);

        // 获得tracker
        TrackerClient trackerClient = new TrackerClient();

        TrackerServer connection = trackerClient.getConnection();
//        TrackerServer connection = trackerClient.getTrackerServer();

        // 通过tracker获得storage
        StorageClient storageClient = new StorageClient(connection, null);


        // 通过storage上传文件
        String[] gifs = storageClient.upload_file("d:/a.gif", "gif", null);
        String url = "http://192.168.2.128";
        for (String gif : gifs) {
            url = url+"/"+gif;
        }

        System.out.println(url);

    }

}
