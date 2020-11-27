package com.hlxd.microcloud.util;

import com.alibaba.otter.canal.client.kafka.KafkaCanalConnector;
import com.alibaba.otter.canal.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * CREATED BY IDEA
 *
 * @Author taojun
 * @Date 2020/11/1713:33
 * @VERSION 1.0
 * @COMPANY HLXD
 * @PROJECT product_factory_master
 */
public class CanalKafkaClientUtil {
    protected final static Logger logger  = LoggerFactory.getLogger(CanalKafkaClientUtil.class);

    private KafkaCanalConnector connector;

    private static volatile boolean         running = false;

    private Thread                          thread  = null;

    private Thread.UncaughtExceptionHandler handler = (t, e) -> logger.error("parse events has an error", e);

    public CanalKafkaClientUtil(String zkServers, String servers, String topic, Integer partition, String groupId){
        connector = new KafkaCanalConnector(servers, topic, partition, groupId, null, false);
    }

    public static void main(String[] args) {
        try {
            final CanalKafkaClientUtil kafkaCanalClientUtil = new CanalKafkaClientUtil(AbstractKafkaStatic.zkServers,
                    AbstractKafkaStatic.servers,
                    AbstractKafkaStatic.topic,
                    AbstractKafkaStatic.partition,
                    AbstractKafkaStatic.groupId);
            logger.info("## start the kafka consumer: {}-{}", AbstractKafkaStatic.topic, AbstractKafkaStatic.groupId);
            kafkaCanalClientUtil.start();
            logger.info("## the canal kafka consumer is running now ......");
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    logger.info("## stop the kafka consumer");
                    kafkaCanalClientUtil.stop();
                } catch (Throwable e) {
                    logger.warn("##something goes wrong when stopping kafka consumer:", e);
                } finally {
                    logger.info("## kafka consumer is down.");
                }
            }));
            while (running)
                ;
        } catch (Throwable e) {
            logger.error("## Something goes wrong when starting up the kafka consumer:", e);
            System.exit(0);
        }
    }

    public void start() {
        Assert.notNull(connector, "connector is null");
        thread = new Thread(this::process);
        thread.setUncaughtExceptionHandler(handler);
        thread.start();
        running = true;
    }

    public void stop() {
        if (!running) {
            return;
        }
        running = false;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    private void process() {
        while (!running) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        while (running) {
            try {
                connector.connect();
                connector.subscribe();
                while (running) {
                    try {
                        List<Message> messages = connector.getListWithoutAck(100L, TimeUnit.MILLISECONDS); // 获取message
                        if (messages == null) {
                            continue;
                        }
                        for (Message message : messages) {
                            long batchId = message.getId();
                            int size = message.getEntries().size();
                            if (batchId == -1 || size == 0) {

                            } else {
                                logger.info(message.toString());
                            }
                        }
                        connector.ack(); // 提交确认
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        connector.unsubscribe();
        connector.disconnect();
    }
}
