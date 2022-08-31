package org.example.weather.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.StringJoiner;

@ApiModel("delete job request")
public class DeleteJobRequest implements Serializable  {

    @Serial
    private static final long serialVersionUID = -119673591373760086L;

    @ApiModelProperty(name = "jod name", dataType = "String", required = true, example="job1")
    private String jobName;

    @ApiModelProperty(name = "group name", dataType = "String", required = true, example="group1")
    private String group;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public DeleteJobRequest() {
        super();
    }

    public DeleteJobRequest(String jobName, String group) {
        super();
        this.jobName = jobName;
        this.group = group;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DeleteJobRequest.class.getSimpleName() + "[", "]")
                .add("jobName='" + jobName + "'")
                .add("group='" + group + "'")
                .toString();
    }
}
