package sjtu.zookeeper.config;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import sjtu.zookeeper.service.ZKService;
import sjtu.zookeeper.service.ZKServiceImplementation;
import sjtu.zookeeper.watchers.*;

@Configuration
public class BeanConfig {

    @Bean(name = "zkService")
    @Scope("singleton")
    public ZKService zkService() {
        String zkHostPort = System.getProperty("zk.url");
        return new ZKServiceImplementation(zkHostPort);
    }

    @Bean(name = "allnodeslistener")
    @Scope("singleton")
    public IZkChildListener allNodesListener() {
        return new AllnodesListener();
    }

    @Bean(name = "livenodeslistener")
    @Scope("singleton")
    public IZkChildListener liveNodesListener() {
        return new LivenodesListener();
    }

    @Bean(name = "masterlistener")
    @ConditionalOnProperty(name = "leader.algo", havingValue = "1")
    @Scope("singleton")
    public IZkChildListener masterListener() {
        MasterListener masterListener = new MasterListener();
        masterListener.setZkService(zkService());
        return masterListener;
    }

    @Bean(name = "masterlistener")
    @ConditionalOnProperty(name = "leader.algo", havingValue = "2", matchIfMissing = true)
    @Scope("singleton")
    public IZkChildListener masterListener2() {
        MasterListener2 masterListener = new MasterListener2();
        masterListener.setZkService(zkService());
        return masterListener;
    }

    @Bean(name = "connectstatelistener")
    @Scope("singleton")
    public IZkStateListener connectStateChangeListener() {
        ConnectStateListener connectStateListener = new ConnectStateListener();
        connectStateListener.setZkService(zkService());
        return connectStateListener;
    }
}
