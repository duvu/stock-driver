package com.vd5.stock;

import com.vd5.stock.jobs.StockJob;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.File;

public class Main extends Application {
    private static Scheduler scheduler;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

    }

    @Override
    public void stop() throws Exception {
        Log.info("StockDriver", "Exiting ...");
        if (scheduler != null) {
            scheduler.shutdown();
        }
        StockDriver.stop();
        super.stop();
    }

    public static void main(String[] args) {
        RTConfig.init();
        StockDriver.init();
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobDetail job = JobBuilder.newJob(StockJob.class).withIdentity(RTConfig.getJobName(), RTConfig.getGroupName()).build();
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(RTConfig.getJobName(), RTConfig.getGroupName())
                    .withSchedule(CronScheduleBuilder.cronSchedule(RTConfig.getCronString())).build();
            scheduler.scheduleJob(job, cronTrigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
            Log.error("Exception", e.getMessage());
        }

        launch(args);
    }
}
