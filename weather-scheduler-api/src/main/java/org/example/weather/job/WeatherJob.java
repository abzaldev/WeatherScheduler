package org.example.weather.job;

import org.example.weather.service.WeatherApiConsumerService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeatherJob implements Job {

    public WeatherJob() {}

    Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    WeatherApiConsumerService weatherApiConsumerService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        LOG.info("Job ** {} ** fired @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());

        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String city = jobDataMap.getString("city");
        weatherApiConsumerService.fetchWeatherByCityName(city);

        LOG.info("Next job scheduled @ {}", context.getNextFireTime());

    }
}
