package com.et.sftp.config;

import com.jcraft.jsch.ChannelSftp;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.file.FileNameGenerator;
import org.springframework.integration.file.remote.FileInfo;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.sftp.gateway.SftpOutboundGateway;
import org.springframework.integration.sftp.outbound.SftpMessageHandler;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;



@Configuration
@EnableConfigurationProperties(SftpProperties.class)
public class SftpConfiguration {

    @Resource
    private SftpProperties properties;


    @Bean
    public MessagingTemplate messagingTemplate(BeanFactory beanFactory) {
        MessagingTemplate messagingTemplate = new MessagingTemplate();
        messagingTemplate.setBeanFactory(beanFactory);
        return messagingTemplate;
    }

    @Bean
    public SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        factory.setHost(properties.getHost());
        factory.setPort(properties.getPort());
        factory.setUser(properties.getUsername());
        factory.setPassword(properties.getPassword());
        factory.setAllowUnknownKeys(true);
//        factory.setTestSession(true);
//        return factory;
        return new CachingSessionFactory<ChannelSftp.LsEntry>(factory);
    }

    @Bean
    public SftpRemoteFileTemplate remoteFileTemplate(SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory) {
        return new SftpRemoteFileTemplate(sftpSessionFactory);
    }


    @Bean
    @ServiceActivator(inputChannel = "downloadChannel")
    public MessageHandler downloadHandler(SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory) {
        SftpOutboundGateway sftpOutboundGateway = new SftpOutboundGateway(sftpSessionFactory, "mget", "payload");
        sftpOutboundGateway.setOptions("-R");
        sftpOutboundGateway.setFileExistsMode(FileExistsMode.REPLACE_IF_MODIFIED);
        sftpOutboundGateway.setLocalDirectory(new File(properties.getLocalDir()));
        sftpOutboundGateway.setAutoCreateLocalDirectory(true);
        return sftpOutboundGateway;
    }


    @Bean
    @ServiceActivator(inputChannel = "uploadChannel", outputChannel = "testChannel")
    public MessageHandler uploadHandler(SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory) {
        SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory);
        handler.setRemoteDirectoryExpression(new LiteralExpression(properties.getRemoteDir()));
//        handler.setChmod();
        handler.setFileNameGenerator(message -> {
            if (message.getPayload() instanceof File) {
                return ((File) message.getPayload()).getName();
            } else {
                throw new IllegalArgumentException("File expected as payload.");
            }
        });
        return handler;
    }

    @Bean
    @ServiceActivator(inputChannel = "uploadByteChannel")
    public MessageHandler multiTypeHandler(SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory) {
        SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory);
        handler.setRemoteDirectoryExpression(new LiteralExpression(properties.getRemoteDir()));
        handler.setFileNameGenerator(message -> {
            if (message.getPayload() instanceof byte[]) {
                return (String) message.getHeaders().get("name");
            } else {
                throw new IllegalArgumentException("byte[] expected as payload.");
            }
        });
        return handler;
    }


    @Bean
    @ServiceActivator(inputChannel = "lsChannel")
    public MessageHandler lsHandler(SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory) {
        SftpOutboundGateway sftpOutboundGateway = new SftpOutboundGateway(sftpSessionFactory, "ls", "payload");
        sftpOutboundGateway.setOptions("-R"); //配置项
        return sftpOutboundGateway;
    }


    @Bean
    @ServiceActivator(inputChannel = "nlstChannel")
    public MessageHandler listFileNamesHandler(SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory) {
        SftpOutboundGateway sftpOutboundGateway = new SftpOutboundGateway(sftpSessionFactory, "nlst", "payload");
        return sftpOutboundGateway;
    }

    @Bean
    @ServiceActivator(inputChannel = "getChannel")
    public MessageHandler getFileHandler(SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory) {
        SftpOutboundGateway sftpOutboundGateway = new SftpOutboundGateway(sftpSessionFactory, "get", "payload");
        sftpOutboundGateway.setOptions("-R");
        sftpOutboundGateway.setFileExistsMode(FileExistsMode.REPLACE_IF_MODIFIED);
        sftpOutboundGateway.setLocalDirectory(new File(properties.getLocalDir()));
        sftpOutboundGateway.setAutoCreateLocalDirectory(true);
        return sftpOutboundGateway;
    }

    /**
     * create by: qiushicai
     * create time: 2020/11/20
     *
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "abc")
    public MessageHandler abcHandler(SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory) {
        SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory);
        handler.setRemoteDirectoryExpression(new LiteralExpression(properties.getRemoteDir()));
        handler.setFileNameGenerator(message -> {
            if (message.getPayload() instanceof byte[]) {
                System.out.println("receive message:" + new String((byte[]) message.getPayload()));
                message.getHeaders().forEach((k, v) -> System.out.println("\t\t|---" + k + "=" + v));
                return "ok";
            } else {
                throw new IllegalArgumentException("byte[] expected as payload.");
            }
        });
        return handler;
    }

    /**
     *
     *  the #root object is the Message, which has two properties (headers and payload) that allow such expressions as payload, payload.thing, headers['my.header'], and so on
     *
     *  link{ https://stackoverflow.com/questions/46650004/spring-integration-ftp-create-dynamic-directory-with-remote-directory-expressi}
     *  link{ https://docs.spring.io/spring-integration/reference/html/spel.html}
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "toPathChannel")
    public MessageHandler pathHandler() {
        SftpMessageHandler handler = new SftpMessageHandler(sftpSessionFactory());
        // automatically create the remote directory
        handler.setAutoCreateDirectory(true);
        handler.setRemoteDirectoryExpression(new SpelExpressionParser().parseExpression("headers[path]"));
        handler.setFileNameGenerator(new FileNameGenerator() {
            @Override
            public String generateFileName(Message<?> message) {
                return (String) message.getHeaders().get("filename");
            }
        });
        return handler;
    }


    /**
     * <ul>
     * <li>ls (list files)
     * <li> nlst (list file names)
     * <li> get (retrieve a file)
     * <li> mget (retrieve multiple files)
     * <li> rm (remove file(s))
     * <li> mv (move and rename file)
     * <li> put (send a file)
     * <li> mput (send multiple files)
     * </ul>
     *
     * @author ：qiushicai
     * @date ：Created in 2020/11/20
     * @description： outbound gateway API
     * @version:
     */
    @MessagingGateway
    public interface SftpGateway {

        //ls (list files)
        @Gateway(requestChannel = "lsChannel")
        List<FileInfo> listFile(String dir);


        @Gateway(requestChannel = "nlstChannel")
        String nlstFile(String dir);



        @Gateway(requestChannel = "getChannel")
        File getFile(String dir);

        @Gateway(requestChannel = "mgetChannel")
        List<File> mgetFile(String dir);

        @Gateway(replyChannel = "rmChannel")
        boolean rmFile(String file);

        @Gateway(replyChannel = "mvChannel")
        boolean mv(String sourceFile, String targetFile);

        @Gateway(requestChannel = "putChannel")
        File putFile(String dir);

        @Gateway(requestChannel = "mputChannel")
        List<File> mputFile(String dir);

        @Gateway(requestChannel = "uploadChannel")
        void upload(File file);

        @Gateway(requestChannel = "uploadByteChannel")
        void upload(byte[] inputStream, String name);

        @Gateway(requestChannel = "toPathChannel")
        void upload(@Payload byte[] file, @Header("filename") String filename, @Header("path") String path);

        @Gateway(requestChannel = "downloadChannel")
        List<File> downloadFiles(String dir);

    }
}
