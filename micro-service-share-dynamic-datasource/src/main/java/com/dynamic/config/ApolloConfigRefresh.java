package com.dynamic.config;

import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Set;

@Component
public class ApolloConfigRefresh implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ApolloConfigRefresh.class);

    private static final String LOGGER_TAG = "logging.level.";

    @Autowired
    private DynamicDataSource dynamicDataSource;

    @Autowired
    private DataSourceManager dataSourceManager;

    @Autowired
    private ApplicationContext applicationContext;

    @SuppressWarnings("all")
    @Autowired
    private LoggingSystem loggingSystem;

    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent) {
        boolean dataSourceConfigChanged = false;
        for (String changedKey : changeEvent.changedKeys()) {
            if (changedKey.startsWith("spring.datasource.")) {
                dataSourceConfigChanged = true;
                break;
            }else if(containsIgnoreCase(changedKey, LOGGER_TAG)){
                ConfigChange configChange = changeEvent.getChange(changedKey);
                String newValue = configChange.getNewValue();
                if(newValue != null){
                    LogLevel logLevel = LogLevel.valueOf(newValue.toUpperCase());
                    loggingSystem.setLogLevel(changedKey.replace(LOGGER_TAG, ""), logLevel);
                    logger.info("{}:{}", changedKey, logLevel);
                }
            }
        }

        if (dataSourceConfigChanged) {
            refreshDataSource(changeEvent.changedKeys());
        }
    }

    private synchronized void refreshDataSource(Set<String> changedKeys) {
        try {
            logger.info("Refreshing data source");

            /**
             * rebind configuration beans, e.g. DataSourceProperties
             * @see org.springframework.cloud.context.properties.ConfigurationPropertiesRebinder#onApplicationEvent
             */
            this.applicationContext.publishEvent(new EnvironmentChangeEvent(changedKeys));

            DataSource newDataSource = dataSourceManager.createAndTestDataSource();
            dynamicDataSource.setDataSource(newDataSource);

            logger.info("Finished refreshing data source");
        } catch (Throwable ex) {
            logger.error("Refreshing data source failed", ex);
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private static boolean containsIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        int len = searchStr.length();
        int max = str.length() - len;
        for (int i = 0; i <= max; i++) {
            if (str.regionMatches(true, i, searchStr, 0, len)) {
                return true;
            }
        }
        return false;
    }
}
