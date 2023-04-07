package cc.onelooker.kaleido.config;

import cc.onelooker.kaleido.config.properties.ThreadPoolProperties;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @author Lion Li
 **/
@Configuration
public class ThreadPoolConfig {

    @Resource
    private ThreadPoolProperties threadPoolProperties;

    @Bean(name = "threadPoolTaskExecutor")
    @ConditionalOnProperty(prefix = "thread-pool", name = "enabled", havingValue = "true")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 最大线程数20：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
        // 核心线程数10：线程池创建时候初始化的线程数
        executor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
        // 缓冲队列200：用来缓冲执行任务的队列
        executor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
        // 允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(threadPoolProperties.getKeepAliveSeconds());
        RejectedExecutionHandler handler = (RejectedExecutionHandler) ReflectUtils.newInstance(threadPoolProperties.getRejectedExecutionHandler().getClazz());
        executor.setRejectedExecutionHandler(handler);
        return executor;
    }

    /**
     * 执行周期性或定时任务
     */
    @Bean(name = "scheduledExecutorService")
    protected ScheduledExecutorService scheduledExecutorService() {
        return new ScheduledThreadPoolExecutor(threadPoolProperties.getCorePoolSize(),
                new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
            }
        };
    }
}
