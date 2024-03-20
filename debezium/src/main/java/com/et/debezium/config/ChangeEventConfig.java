package com.et.debezium.config;

import cn.hutool.core.io.FileUtil;
import com.et.debezium.handler.ChangeEventHandler;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.debezium.connector.mysql.MySqlConnector;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.util.concurrent.*;

/**
 * @author lei
 * @create 2021-06-22 15:36
 * @desc sql server 实时同步
 **/
@Configuration
@Log4j2
public class ChangeEventConfig {
    private final ChangeEventHandler changeEventHandler;

    @Value("${timely.offset-file-name}")
    private String offsetFileName;
    @Value("${timely.offset-file-clean:true}")
    private Boolean offsetFileDelete;
    @Value("${timely.offset-time}")
    private String offsetTime;
    @Value("${timely.history-file-name}")
    private String historyFileName;
    @Value("${timely.offline.instance-name}")
    private String instanceName;
    @Value("${timely.offline.logic-name}")
    private String logicName;
    @Value("${timely.offline.ip}")
    private String ip;
    @Value("${timely.offline.port}")
    private String port;
    @Value("${timely.offline.username}")
    private String username;
    @Value("${timely.offline.password}")
    private String password;
    @Value("${timely.offline.include-table}")
    private String includeTable;
    @Value("${timely.offline.include-db}")
    private String includeDb;
    @Value("${timely.offline.server-id}")
    private String serverId;

    @Autowired
    public ChangeEventConfig(ChangeEventHandler changeEventHandler) {
        this.changeEventHandler = changeEventHandler;
    }

    @Bean
    public void cleanFile() {
        if (offsetFileDelete && FileUtil.exist(offsetFileName)) {
            FileUtil.del(offsetFileName);
        }
    }

    /**
     * Debezium 配置.
     *
     * @return configuration
     */
        @Bean
        io.debezium.config.Configuration debeziumConfig() {
            return io.debezium.config.Configuration.create()
    //            连接器的Java类名称
                    .with("connector.class", MySqlConnector.class.getName())
    //            偏移量持久化，用来容错 默认值
                    .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
    //                偏移量持久化文件路径 默认/tmp/offsets.dat  如果路径配置不正确可能导致无法存储偏移量 可能会导致重复消费变更
    //                如果连接器重新启动，它将使用最后记录的偏移量来知道它应该恢复读取源信息中的哪个位置。
                    .with("offset.storage.file.filename", offsetFileName)
    //                捕获偏移量的周期
                    .with("offset.flush.interval.ms", offsetTime)
    //               连接器的唯一名称
                    .with("name", instanceName)
    //                数据库的hostname
                    .with("database.hostname", ip)
    //                端口
                    .with("database.port", port)
    //                用户名
                    .with("database.user", username)
    //                密码
                    .with("database.password", password)
    //                 包含的数据库列表
                    .with("database.include.list", includeDb)
    //                是否包含数据库表结构层面的变更，建议使用默认值true
                    .with("include.schema.changes", "false")
    //                mysql.cnf 配置的 server-id
                    .with("database.server.id", serverId)
    //                	MySQL 服务器或集群的逻辑名称
                    .with("database.server.name", logicName)
    //                历史变更记录
                    .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
    //                历史变更记录存储位置，存储DDL
                    .with("database.history.file.filename", historyFileName)
                    .build();
        }
  /*  @Bean
    io.debezium.config.Configuration debeziumConfig() {
        return io.debezium.config.Configuration.create()
                //  连接器的Java类名称
                .with("connector.class", SqlServerConnector.class.getName())
                // 偏移量持久化，用来容错 默认值
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                // 要存储偏移量的文件路径 默认/tmp/offsets.dat  如果路径配置不正确可能导致无法存储偏移量 可能会导致重复消费变更
                // 如果连接器重新启动，它将使用最后记录的偏移量来知道它应该恢复读取源信息中的哪个位置。
                .with("offset.storage.file.filename", offsetFileName)
                // 尝试提交偏移量的时间间隔。默认值为 1分钟
                .with("offset.flush.interval.ms", offsetTime)
                // 监听连接器实例的 唯一名称
                .with("name", instanceName)
                // SQL Server 实例的地址
                .with("database.hostname", ip)
                // SQL Server 实例的端口号
                .with("database.port", port)
                // SQL Server 用户的名称
                .with("database.user", username)
                // SQL Server 用户的密码
                .with("database.password", password)
                // 要从中捕获更改的数据库的名称
                .with("database.dbname", includeDb)
                // 是否包含数据库表结构层面的变更 默认值true
                .with("include.schema.changes", "false")
                // Debezium 应捕获其更改的所有表的列表
                .with("table.include.list", includeTable)
                // SQL Server 实例/集群的逻辑名称，形成命名空间，用于连接器写入的所有 Kafka 主题的名称、Kafka Connect 架构名称以及 Avro 转换器时对应的 Avro 架构的命名空间用来
                .with("database.server.name", logicName)
                // 负责数据库历史变更记录持久化Java 类的名称
                .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
                // 历史变更记录存储位置，存储DDL
                .with("database.history.file.filename", historyFileName)
                .build();
    }*/

    /**
     * 实例化sql server 实时同步服务类，执行任务
     *
     * @param configuration
     * @return
     */
    @Bean
    SqlServerTimelyExecutor sqlServerTimelyExecutor(io.debezium.config.Configuration configuration) {
        SqlServerTimelyExecutor sqlServerTimelyExecutor = new SqlServerTimelyExecutor();
        DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine = DebeziumEngine
                .create(ChangeEventFormat.of(Connect.class))
                .using(configuration.asProperties())
                .notifying(changeEventHandler::handlePayload)
                .build();
        sqlServerTimelyExecutor.setDebeziumEngine(debeziumEngine);
        return sqlServerTimelyExecutor;
    }

    /**
     * @author lei
     * @version 1.0
     * @date 2021-06-22 15:39
     * @desc 同步执行服务类
     */
    @Data
    @Log4j2
    public static class SqlServerTimelyExecutor implements InitializingBean, SmartLifecycle {
        private final ExecutorService executor = ThreadPoolEnum.INSTANCE.getInstance();
        private DebeziumEngine<?> debeziumEngine;

        @Override
        public void start() {
            log.warn(ThreadPoolEnum.SQL_SERVER_LISTENER_POOL + "线程池开始执行 debeziumEngine 实时监听任务!");
            executor.execute(debeziumEngine);
        }

        @SneakyThrows
        @Override
        public void stop() {
            log.warn("debeziumEngine 监听实例关闭!");
            debeziumEngine.close();
            Thread.sleep(2000);
            log.warn(ThreadPoolEnum.SQL_SERVER_LISTENER_POOL + "线程池关闭!");
            executor.shutdown();
        }

        @Override
        public boolean isRunning() {
            return false;
        }

        @Override
        public void afterPropertiesSet() {
            Assert.notNull(debeziumEngine, "DebeZiumEngine 不能为空!");
        }

        public enum ThreadPoolEnum {
            /**
             * 实例
             */
            INSTANCE;

            public static final String SQL_SERVER_LISTENER_POOL = "sql-server-listener-pool";
            /**
             * 线程池单例
             */
            private final ExecutorService es;

            /**
             * 枚举 (构造器默认为私有）
             */
            ThreadPoolEnum() {
                final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(SQL_SERVER_LISTENER_POOL + "-%d").build();
                es = new ThreadPoolExecutor(8, 16, 60,
                        TimeUnit.SECONDS, new ArrayBlockingQueue<>(256),
                        threadFactory, new ThreadPoolExecutor.DiscardPolicy());
            }

            /**
             * 公有方法
             *
             * @return ExecutorService
             */
            public ExecutorService getInstance() {
                return es;
            }
        }
    }

}
