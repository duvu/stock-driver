package com.vd5.stock;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Properties;

/**
 * @author beou on 11/19/19 22:53
 */
public class RTConfig {
    private static final Logger _log = LoggerFactory.getLogger(RTConfig.class);
    public static final String PACKAGE          = RTConstants.PACKAGE_NAME;
    public static final String CRON_STRING      = PACKAGE + ".data.quartz.cron";
    public static final String JOB_NAME         = PACKAGE + ".data.quartz.job_name";
    public static final String GROUP_NAME       = PACKAGE + ".data.quartz.group_name";
    public static final String STOCK_URL        = PACKAGE + ".url";

    private static final Properties prop = new Properties();
    public static void init() {
        configuration();
    }

    private static void configuration() {
        org.apache.commons.configuration2.builder.fluent.Parameters params = new org.apache.commons.configuration2.builder.fluent.Parameters();
        File props = new File("resources/application.properties");
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(params.fileBased().setFile(props));
        try {
            Configuration config = builder.getConfiguration();
            loadProperties(config);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
    private static void loadProperties(Configuration config) {
        _log.info("loading configuration");

        String url = config.getString(STOCK_URL);
        prop.setProperty(STOCK_URL, url);

        String cronStr = config.getString(CRON_STRING);
        prop.setProperty(CRON_STRING, cronStr);

        String jobName = config.getString(JOB_NAME);
        prop.setProperty(JOB_NAME, jobName);

        String groupName = config.getString(GROUP_NAME);
        prop.setProperty(GROUP_NAME, groupName);
    }

    public static String getValue(String key) {
        return prop.getProperty(key);
    }

    // methods to get string
    public static String getCronString() {
        String cstr = getValue(CRON_STRING);
        return StringUtils.isNotBlank(cstr) ? cstr : RTConstants.DEFAULT_CRON_STRING;
    }

    public static String getJobName() {
        String jobName = getValue(JOB_NAME);
        return StringUtils.isNotBlank(jobName) ? jobName : RTConstants.DEFAULT_JOB_NAME;
    }

    public static String getGroupName() {
        String groupName = getValue(GROUP_NAME);
        return StringUtils.isNotBlank(groupName) ? groupName : RTConstants.DEFAULT_GROUP_NAME;
    }
}
