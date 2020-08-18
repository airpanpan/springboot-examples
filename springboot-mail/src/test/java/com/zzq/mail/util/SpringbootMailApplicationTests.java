package com.zzq.mail.util;

import com.zzq.SpringbootMailApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootMailApplication.class)
@WebAppConfiguration
public class SpringbootMailApplicationTests {

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateService templateService;

    //private static final String TO = "zichao.wu@medbanks.cn";
    private static final String TO = "zhiqiang.zeng@medbanks.cn";
    private static final String SUBJECT = "测试111b222邮件bbbsb";
    private static final String CONTENT = "test content";

    /**
     * 测试发送普通邮件
     */
    @org.junit.Test
    public void sendSimpleMailMessage() {
        mailService.sendSimpleMailMessge(TO, SUBJECT, CONTENT);
    }

    /**
     * 测试发送html邮件
     */
    @org.junit.Test
    public void sendHtmlMessage() {
       /* String htmlStr = "<h1>Test</h1>";
        mailService.sendMimeMessge(TO, SUBJECT, htmlStr);*/

        Map<String, Object> params = new HashMap<>();
        params.put("row1", "779单" );
        params.put("row2", "9998单");
        String htmlStr = templateService.render("sub/HelloWorld", params);
        mailService.sendMimeMessge(TO, SUBJECT, htmlStr);

    }

    /**
     * 测试发送带附件的邮件
     * @throws FileNotFoundException
     */
    @org.junit.Test
    public void sendAttachmentMessage() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:test.txt");
        String filePath = file.getAbsolutePath();
        mailService.sendMimeMessge(TO, SUBJECT, CONTENT, filePath);
    }

    /**
     * 测试发送带附件的邮件
     * @throws FileNotFoundException
     */
    @Test
    public void sendPicMessage() throws FileNotFoundException {
        String htmlStr = "<html><body>测试：图片1 <br> <img src=\'cid:pic1\'/> <br>图片2 <br> <img src=\'cid:pic2\'/></body></html>";
        Map<String, String> rscIdMap = new HashMap<>(2);
        rscIdMap.put("pic1", ResourceUtils.getFile("classpath:pic01.jpg").getAbsolutePath());
        rscIdMap.put("pic2", ResourceUtils.getFile("classpath:pic02.jpg").getAbsolutePath());
        mailService.sendMimeMessge(TO, SUBJECT, htmlStr, rscIdMap);
    }

}
