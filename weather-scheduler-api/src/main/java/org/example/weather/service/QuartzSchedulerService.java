package org.example.weather.service;

import org.example.weather.payload.JobInfo;
import org.example.weather.job.WeatherJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuartzSchedulerService {

    private Logger LOG = LoggerFactory.getLogger(getClass());
    private Scheduler scheduler;

    public QuartzSchedulerService(Scheduler scheduler) {
        super();
        this.scheduler = scheduler;
    }

    public void addJob(String jobName, String cityName, String group, String cron) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, group);
        JobDetail jobDetail = JobBuilder
                .newJob(WeatherJob.class)
                .withIdentity(jobName, group)
                .usingJobData("city", cityName)
                .build();
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(triggerKey)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }


    public JobInfo getJob(String jobName, String group) throws SchedulerException {
        var triggerKey = TriggerKey.triggerKey(jobName, group);
        var jobKey = JobKey.jobKey(jobName, group);
        var jobDetail = scheduler.getJobDetail(jobKey);
        var triggers = scheduler.getTriggersOfJob(jobKey);
        if (triggers.isEmpty()) {
            throw new SchedulerException("no trigger");
        }
        var trigger = triggers.get(0);
        var triggerState = scheduler.getTriggerState(trigger.getKey());
        if (!(trigger instanceof CronTrigger)) {
            throw new SchedulerException("no cronTrigger");
        }
        var cronTrigger = (CronTrigger) trigger;
        return new JobInfo().setJobName(jobName).setGroup(group).setState(triggerState.name()).setCron(cronTrigger.getCronExpression()).setTimezone(cronTrigger.getTimeZone().getID()).setClassName(jobDetail.getJobClass().getName());

    }


    public boolean modifyJob(String name, String group, String cron) throws SchedulerException {
        var triggerKey = TriggerKey.triggerKey(name, group);
        var scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        var cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, cronTrigger);
        return false;
    }


    public boolean deleteJob(String jobName, String group) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, group);
        scheduler.pauseTrigger(triggerKey);
        scheduler.unscheduleJob(triggerKey);
        JobKey jobKey = JobKey.jobKey(jobName, group);
        boolean b = scheduler.deleteJob(jobKey);
        LOG.info("Job deleted successfully " + jobName);
        return true;
    }


    public boolean pauseJob(String jobName, String group) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, group);
        scheduler.pauseJob(jobKey);
        return true;
    }


    public boolean resumeJob(String jobName, String group) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, group);
        scheduler.resumeJob(jobKey);
        return true;
    }


    public List<JobInfo> listJob() throws SchedulerException {
        List<String> jobGroupNames = scheduler.getJobGroupNames();
        ArrayList<JobInfo> jobInfos = new ArrayList<>(jobGroupNames.size());
        jobGroupNames.forEach(groupName -> {
            try {
                var jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
                jobKeys.forEach(jobKey -> {
                    try {
                        var jobName = jobKey.getName();
                        var jobGroup = jobKey.getGroup();
                        var jobDetail = scheduler.getJobDetail(jobKey);
                        var triggers = scheduler.getTriggersOfJob(jobKey);
                        triggers.forEach(trigger -> {
                            try {
                                var triggerState = scheduler.getTriggerState(trigger.getKey());
                                if (trigger instanceof CronTrigger) {
                                    var cronTrigger = (CronTrigger) trigger;
                                    jobInfos.add(new JobInfo().setJobName(jobName).setGroup(jobGroup).setState(triggerState.name()).setCron(cronTrigger.getCronExpression()).setTimezone(cronTrigger.getTimeZone().getID()).setClassName(jobDetail.getJobClass().getName()));
                                }
                            } catch (SchedulerException e) {
                                // TODO add logger
                                e.printStackTrace();
                            }
                        });
                    } catch (SchedulerException e) {
                        // TODO add logger
                        e.printStackTrace();
                    }
                });
            } catch (SchedulerException e) {
                // TODO add logger
                e.printStackTrace();
            }
        });
        return jobInfos;
    }



}
