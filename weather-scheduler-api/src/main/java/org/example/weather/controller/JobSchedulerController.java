package org.example.weather.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.weather.payload.JobInfo;
import org.example.weather.payload.AddJobRequest;
import org.example.weather.payload.DeleteJobRequest;
import org.example.weather.payload.PauseResumeJobRequest;
import org.example.weather.service.QuartzSchedulerService;
import org.quartz.SchedulerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Job Scheduler Controller")
public class JobSchedulerController {

    private QuartzSchedulerService quartzService;

    public JobSchedulerController(QuartzSchedulerService quartzService) {
        super();
        this.quartzService = quartzService;
    }

    @ApiOperation(value = "add job api", notes = "add a quartz job detail")
    @PostMapping(path = "/job")
    public ResponseEntity<Boolean> addJob(@RequestBody AddJobRequest request) {
        try {
            this.quartzService.addJob(request.getJobName(), request.getCityName(), request.getGroup(), request.getCron());
            return ResponseEntity.of(Optional.of(true));
        } catch (SchedulerException e) {
            return ResponseEntity.of(Optional.of(false));
        }
    }

    @ApiOperation(value = "list job api", notes = "list all quartz job detail")
    @GetMapping(path = "/jobs")
    public ResponseEntity<List<JobInfo>> listJobs() {
        try {
            var jobs = this.quartzService.listJob();
            return ResponseEntity.ok(jobs);
        } catch (SchedulerException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "delete job api", notes = "delete a quartz job detail")
    @DeleteMapping(path = "/job")
    public ResponseEntity<Boolean> deleteJob(@RequestBody DeleteJobRequest request) {
        try {
            this.quartzService.deleteJob(request.getJobName(), request.getGroup());
            return ResponseEntity.of(Optional.of(true));
        } catch (SchedulerException e) {
            e.printStackTrace();
            return ResponseEntity.of(Optional.of(false));
        }

    }

    @ApiOperation(value = "get job api", notes = "fetch a quartz job detail")
    @GetMapping(path = "/job")
    public ResponseEntity<JobInfo> getJob(@RequestParam String job, @RequestParam String group) {
        try {
            return ResponseEntity.ok(this.quartzService.getJob(job, group));
        } catch (SchedulerException e) {
           // return ResponseEntity.status(404).body(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @ApiOperation(value = "pause job api", notes = "pause a quartz job")
    @PutMapping(path = "/job/pause")
    public ResponseEntity<Boolean> pauseJob(@RequestBody PauseResumeJobRequest request) {
        try {
            var job = request.getJobName();
            var group = request.getGroup();
            return ResponseEntity.ok(this.quartzService.pauseJob(job, group));
        } catch (SchedulerException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(false);
        }
    }

    @ApiOperation(value = "resume job api", notes = "resume a quartz job")
    @PutMapping(path = "/job/resume")
    public ResponseEntity<Boolean> resumeJob(@RequestBody PauseResumeJobRequest request) {
        try {
            var job = request.getJobName();
            var group = request.getGroup();
            return ResponseEntity.ok(this.quartzService.resumeJob(job, group));
        } catch (SchedulerException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(false);
        }
    }


}
