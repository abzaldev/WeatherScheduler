package org.example.weather.payload;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("Add job request")
public class AddJobRequest implements Serializable {

    @ApiModelProperty(name = "jod name", dataType = "String", required = true, example="job1")
    private String jobName;

    @ApiModelProperty(name = "city name", dataType = "String", required = true, example="Almaty")
    private String cityName;

    @ApiModelProperty(name = "group name", dataType = "String", required = true, example="group1")
    private String group;

    @ApiModelProperty(name = "job cron name", dataType = "String", required = true, example = "*/5 * * * * ?")
    private String cron;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public AddJobRequest() {
        super();
    }

    public AddJobRequest(String jobName, String cityName, String group, String cron) {
        super();
        this.jobName = jobName;
        this.cityName = cityName;
        this.group = group;
        this.cron = cron;
    }
}
