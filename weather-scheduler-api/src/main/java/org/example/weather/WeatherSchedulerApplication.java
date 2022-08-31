package org.example.weather;

import org.example.weather.job.WeatherJob;
import org.quartz.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication
public class WeatherSchedulerApplication {

    public static void main(String[] args) {
        var app = new SpringApplication(WeatherSchedulerApplication.class);
        app.run(args);
    }

//    private Scheduler scheduler;
//
//    public WeatherSchedulerApplication(Scheduler scheduler) {
//        super();
//        this.scheduler = scheduler;
//    }

    @Bean
    public CommandLineRunner run(Scheduler scheduler) throws Exception {
        return (String[] args) -> {
            String cityName = "London";
            String cronExpression = "0 * * ? * *"; // every 1 min
            String jobName = "N" + getRandomNumberUsingInts(100, 10000) + " " + cityName + " WJ _" + cronExpression + "_ ";
            String groupName = "weather-group";


            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, groupName);
            JobDetail jobDetail = JobBuilder
                    .newJob(WeatherJob.class)
                    .withIdentity(jobName, groupName)
                    .usingJobData("city", "London")
                    .build();
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            System.out.println("Job added: " + jobName);
        };
    }

    public int getRandomNumberUsingInts(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }
}
