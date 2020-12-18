package com.alex.jobScheduler.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alex.jobScheduler.model.Job;

@Service
public class JobSchedulerService {

	public List<List<Long>> scheduleJobs(List<Job> jobs) {
			
		sortJobsByMaxDate(jobs);
		return groupJobsByLimitTime(jobs, getMaxHoursToExecute());		
	}
	
	public List<List<Long>> groupJobsByLimitTime(List<Job> jobs, int limitTimeInHours){
	
		List<List<Long>> groupedJobs = new ArrayList<List<Long>>();
		analyzeJobsToGroup(groupedJobs,jobs, limitTimeInHours);
		
		return groupedJobs;
	}
	
	public void analyzeJobsToGroup(List<List<Long>> groupedJobs, List<Job> jobs, int limitTimeInHours) {

		List<Job> notGroupedJobs = new ArrayList<Job>();
		List<Long> jobsToGroup = new ArrayList<Long>();
		
		int timeGrouped = 0; 
		
		for (Job job : jobs) {
			
			if(isJobAbleToGroup(limitTimeInHours, timeGrouped, job)) {
				jobsToGroup.add(job.getId());
				timeGrouped += job.getTempoEstimado();
			}else {
				notGroupedJobs.add(job);
			}
			
			if (isMaxTimeExceeded(limitTimeInHours, timeGrouped)) {
				
				groupedJobs.add(jobsToGroup);
				jobsToGroup = new ArrayList<Long>();
				timeGrouped=0;		
			}
        }
		
		if(hasMoreJobsToAnalize(notGroupedJobs)) {
			
			if(hasJobsToGroup(jobsToGroup)) {
				groupedJobs.add(jobsToGroup);
			}
			analyzeJobsToGroup(groupedJobs, notGroupedJobs, limitTimeInHours);
		}
		
		if(isTheLastGroup(notGroupedJobs, jobsToGroup, timeGrouped)) {
			groupedJobs.add(jobsToGroup);
		}
	}

	private boolean hasJobsToGroup(List<Long> jobsToGroup) {
		return !jobsToGroup.isEmpty();
	}
	private boolean hasMoreJobsToAnalize(List<Job> jobsToGroup) {
		return !jobsToGroup.isEmpty();
	}
	
	public void sortJobsByMaxDate(List<Job> jobs) {
		jobs.sort(Comparator.comparing(Job::getDataMaximaDeConclusao));	
	}
	
	private boolean isTheLastGroup(List<Job> notGroupedJobs, List<Long> jobsToGroup, int timeGrouped) {
		return timeGrouped > 0 && notGroupedJobs.isEmpty() && hasJobsToGroup(jobsToGroup);
	}
	
	private boolean isJobAbleToGroup(int limitTimeInHours, int timeCounter, Job job) {
		return job.getTempoEstimado() + timeCounter <= limitTimeInHours;
	}
	
	private boolean isMaxTimeExceeded(int limitTimeInHours, int timeCounter) {
		return timeCounter>=limitTimeInHours;
	}

	private int getMaxHoursToExecute(){
		return 8;
	}
}
