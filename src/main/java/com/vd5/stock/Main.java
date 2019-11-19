package com.vd5.stock;

import com.vd5.stock.jobs.StockJob;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class Main extends Application {
    private static Scheduler scheduler;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/sample.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

    }

    @Override
    public void stop() throws Exception {
        System.out.println("Exiting ...");
        if (scheduler != null) {
            scheduler.shutdown();
        }
        StockDriver.stop();
        super.stop();
    }

    public static void main(String[] args) {
        StockDriver.init();
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobDetail job = JobBuilder.newJob(StockJob.class).withIdentity("job1", "group1").build();
            //SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow().build();
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("job1", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?")).build();
            scheduler.scheduleJob(job, cronTrigger);
            scheduler.start();
            //
        } catch (SchedulerException e) {
            e.printStackTrace();
            System.out.println("Exception ...");
        }


        launch(args);
    }


}
