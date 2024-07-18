package com.et.sftp.config.file.sync;

import com.et.sftp.config.SftpProperties;
import com.jcraft.jsch.ChannelSftp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.filters.AcceptOnceFileListFilter;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.filters.SftpSimplePatternFileListFilter;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizer;
import org.springframework.integration.sftp.inbound.SftpInboundFileSynchronizingMessageSource;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;
import java.io.File;

/**
 * 文件同步器
 *
 * @author : qiushicai
 * date : Created in 2020/11/20
 */
@Configuration
public class FileSyncConfiguration {

    @Resource
    SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory;

    @Resource
    private SftpProperties properties;

    @Bean
    public SftpInboundFileSynchronizer sftpInboundFileSynchronizer(SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory) {
        SftpInboundFileSynchronizer fileSynchronizer = new SftpInboundFileSynchronizer(sftpSessionFactory);
        fileSynchronizer.setDeleteRemoteFiles(false);
        fileSynchronizer.setRemoteDirectory(properties.getRemoteDir());
        fileSynchronizer.setFilter(new SftpSimplePatternFileListFilter("*.*"));
        return fileSynchronizer;
    }


    /**
     * @return MessageSource
     * @create by: qiushicai
     * @description: 配置Inbound Channel Adapter,监控sftp服务器文件的状态。一旦由符合条件的文件生成，就将其同步到本地服务器。
     * 需要条件：inboundFileChannel的bean；轮询的机制；文件同步bean,SftpInboundFileSynchronizer；
     * @create time: 2020/11/20 10:06
     * @Param: sftpInboundFileSynchronizer
     */
    @Bean
    @InboundChannelAdapter(channel = "fileSynchronizerChannel",
            poller = @Poller(cron = "0/5 * * * * *",
                    //fixedDelay = "5000",
                    maxMessagesPerPoll = "1"))
    public MessageSource<File> sftpMessageSource(SftpInboundFileSynchronizer sftpInboundFileSynchronizer) {
        SftpInboundFileSynchronizingMessageSource source =
                new SftpInboundFileSynchronizingMessageSource(sftpInboundFileSynchronizer);
        source.setLocalDirectory(new File(properties.getLocalDir()));
        source.setAutoCreateLocalDirectory(true);
        source.setLocalFilter(new AcceptOnceFileListFilter<>());
        source.setMaxFetchSize(-1);
        return source;
    }

    /**
     * create by: qiushicai
     * description: TODO
     * create time: 2020/11/20
     *
     * @return MessageHandler
     */
    @Bean
    @ServiceActivator(inputChannel = "fileSynchronizerChannel")
    public MessageHandler handler() {
        // 同步时打印文件信息
        return (m) -> {
            System.out.println(m.getPayload());
            m.getHeaders()
                    .forEach((key, value) -> System.out.println("\t\t|---" + key + "=" + value));
        };
    }
}
