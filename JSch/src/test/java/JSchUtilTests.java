import com.alibaba.fastjson.JSONObject;
import com.et.jsch.DemoApplication;
import com.et.jsch.model.Remote;
import com.et.jsch.util.JSchUtil;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class JSchUtilTests {
    private Logger log = LoggerFactory.getLogger(getClass());

    Session session;
    @Before
    public void before() throws JSchException {
        Remote remote= new Remote();
        remote.setHost("xxx.xxx.xxx.xxx");
        remote.setUser("root");
        remote.setPassword("xxxx");
        session= JSchUtil.getSession(remote);
    }
    @After
    public void after(){
        JSchUtil.disconnect(session);
    }
    @Test
    public void remoteExecute() throws JSchException {
        List<String>  list= JSchUtil.remoteExecute(session,"ls");
        System.out.println(JSONObject.toJSON(list));
    }
    @Test
    public void uploadFile() throws JSchException, FileNotFoundException {
        String filestr ="D:\\tmp\\test\\file_utils\\file1.txt";
        File file = new File(filestr);
        InputStream in = new FileInputStream(file);

        String directory="/root/test";
        String fileName="test.txt";
       boolean flag= JSchUtil.uploadFile(session,in,directory,fileName);
        System.out.println(flag);
    }
    @Test
    public void deleteFile() throws JSchException, FileNotFoundException {
        String directory="/root/test";
        String fileName="test.txt";
        boolean flag= JSchUtil.deleteFile(session,directory,fileName);
        System.out.println(flag);
    }
    @Test
    public void scpFrom() throws JSchException, FileNotFoundException {

        String source="/root/test/file1.txt";
        String destination ="D:\\tmp\\scfFrom.txt";
        long  filesize= JSchUtil.scpFrom(session,source,destination);
        System.out.println(filesize);
    }
    @Test
    public void scpTo() throws JSchException, FileNotFoundException {

        String filestr ="D:\\tmp\\test\\file_utils\\file1.txt";
        String destination="/root/test/file1.txt";
        long  filesize= JSchUtil.scpTo(session,filestr,destination);
        System.out.println(filesize);
    }

}

