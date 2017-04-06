package id.web.fahrifirdaus.jobseeker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobHolder> {

    private List<Job> jobsList;

    public JobAdapter(List<Job> jobsList) {
        this.jobsList = jobsList;
    }

    @Override
    public JobAdapter.JobHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.job_list, parent, false);

        return new JobHolder(itemView);
    }

    @Override
    public void onBindViewHolder(JobAdapter.JobHolder holder, int position) {
        Job job = jobsList.get(position);
        holder.jobTitle.setText(job.title);
        holder.jobLocation.setText(job.location);
        holder.jobSalary.setText(Integer.toString(job.salary));
    }

    @Override
    public int getItemCount() {
        return jobsList.size();
    }

    public class JobHolder extends RecyclerView.ViewHolder {
        public TextView jobTitle, jobLocation, jobSalary;
        public JobHolder(View itemView) {
            super(itemView);
            jobTitle = (TextView) itemView.findViewById(R.id.txt_job_title);
            jobLocation = (TextView) itemView.findViewById(R.id.txt_job_location);
            jobSalary = (TextView) itemView.findViewById(R.id.txt_job_salary);
        }
    }
}
