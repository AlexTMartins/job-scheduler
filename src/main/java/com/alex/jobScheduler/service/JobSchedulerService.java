package com.alex.jobScheduler.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alex.jobScheduler.model.Job;

@Service
public class JobSchedulerService {

	public List<List<Job>> scheduleJobs(List<Job> jobs) {
			
		sortJobsByMaxDate(jobs);
		return groupJobsByLimitTime(jobs, getMaxHoursToExecute());		
	}
	
	public List<List<Job>> groupJobsByLimitTime(List<Job> jobs, int limitTimeInHours){
	
		List<List<Job>> groupedJobs = new ArrayList<List<Job>>();
		
		analyzeJobsToGroup(groupedJobs,jobs, limitTimeInHours);
		
		return groupedJobs;
	}
	
	public void analyzeJobsToGroup(List<List<Job>> groupedJobs, List<Job> jobs, int limitTimeInHours) {

		List<Job> notGroupedJobs = new ArrayList<Job>();
		List<Job> jobsToGroup = new ArrayList<Job>();
		
		int timeCounter = 0; 
		
		for (Job job : jobs) {
			
			if(isJobAbleToGroup(limitTimeInHours, timeCounter, job)) {
				jobsToGroup.add(job);
			}else {
				notGroupedJobs.add(job);
			}
			
			timeCounter += job.getTempoEstimado();
			
			if (isMaxTimeExceeded(limitTimeInHours, timeCounter)) {
				
				groupedJobs.add(jobsToGroup);
				jobsToGroup.clear();
				timeCounter=0;
						
			}
        }
	}

	private boolean isJobAbleToGroup(int limitTimeInHours, int timeCounter, Job job) {
		return job.getTempoEstimado() + timeCounter <= limitTimeInHours;
	}
	
	private boolean isMaxTimeExceeded(int limitTimeInHours, int timeCounter) {
		return timeCounter>=limitTimeInHours;
	}
	
	public void sortJobsByMaxDate(List<Job> jobs) {
		jobs.sort(Comparator.comparing(Job::getDataMaximaDeConclusao));	
	}
	
	private int getMaxHoursToExecute(){
		return 8;
	}
}
