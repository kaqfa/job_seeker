package id.web.fahrifirdaus.jobseeker;

import java.io.Serializable;

public class Job implements Serializable {

    public String jobid;
    public String title;
    public String description;
    public int salary;
    public String postDate;
    public String endDate;
    public String category;
    public String location;
    public String provider;
    public int registered;

    public Job(String jobid, String title, String description, int salary,
               String postDate, String endDate, String category,
               String location, String provider, int registered) {
        this.jobid = jobid;
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.postDate = postDate;
        this.endDate = endDate;
        this.category = category;
        this.location = location;
        this.provider = provider;
        this.registered = registered;
    }
}
