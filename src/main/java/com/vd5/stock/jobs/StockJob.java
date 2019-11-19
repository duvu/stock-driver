package com.vd5.stock.jobs;

import com.vd5.stock.StockDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author beou on 11/19/19 15:37
 */
public class StockJob implements Job {
    private static final Logger _log = LoggerFactory.getLogger(StockJob.class);

    public StockJob() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        _log.info("StockJob says {} executing at {}", jobKey, new Date());
        WebDriver driver = StockDriver.getDriver();
        String text = driver.findElement(By.id("FPT")).getText();
        _log.info("Text TD: {}", text);

    }

}
