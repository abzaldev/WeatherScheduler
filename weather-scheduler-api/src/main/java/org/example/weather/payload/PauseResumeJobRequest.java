package org.example.weather.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.StringJoiner;

@ApiModel("pause or resume job request")
public class PauseResumeJobRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 3515248087232024755L;

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

    public PauseResumeJobRequest() {
        super();
    }

    public PauseResumeJobRequest(String jobName, String group) {
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
