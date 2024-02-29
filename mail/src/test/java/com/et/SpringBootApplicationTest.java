package com.et;

import com.et.mail.DemoApplication;
import com.et.mail.util.MailUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author zhaokuii11@163.com
 * @create 2022-01-11 19:49
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class SpringBootApplicationTest {
    @Autowired
    MailUtils mailUtils;
    @Autowired
    private TemplateEngine templateEngine;


    /**
     * 指定模板发送邮件
     */
    /**
     * 指定模板发送邮件
     */
    @Test
    public void testTemplateMail() {
        //向Thymeleaf模板传值，并解析成字符串
        Context context = new Context();
        context.setVariable("id", "001");
        String emailContent = templateEngine.process("emailTemplate", context);
        mailUtils.sendHtmlMail("zhaokuii11@163.com", "这是一个模板文件", emailContent);
    }
    /**
     * 发送简单纯文本邮件
     */
    @Test
    public void sendSimpleMail() {
        mailUtils.sendSimpleMail("zhaokuii11@163.com", "发送邮件测试", "大家好，这是我用springboot进行发送邮件测试");
    }

    /**
     * 发送HTML邮件
     */
    @Test
    public void sendHtmlMail() {
        String content = "<html><body><h3><font color=\"red\">" + "大家好，这是springboot发送的HTML邮件" + "</font></h3></body></html>";
        mailUtils.sendHtmlMail("zhaokuii11@163.com", "发送邮件测试", content);
    }

    /**
     * 发送带附件的邮件
     */
    @Test
    public void sendAttachmentMail() {
        String content = "<html><body><h3><font color=\"red\">" + "大家好，这是springboot发送的HTML邮件，有附件哦" + "</font></h3></body></html>";
        String filePath = "your file path";
        mailUtils.sendAttachmentMail("zhaokuii11@163.com", "发送邮件测试", content, filePath);
    }

    /**
     * 发送带图片的邮件
     */
    @Test
    public void sendInlineResourceMail() {
        String rscPath = "your picture path";
        String rscId = "001";
        String content = "<html><body><h3><font color=\"red\">" + "大家好，这是springboot发送的HTML邮件，有图片哦" + "</font></h3>"
                + "<img src=\'cid:" + rscId + "\'></body></html>";
        mailUtils.sendInlineResourceMail("zhaokuii11@163.com", "发送邮件测试", content, rscPath, rscId);
    }
}