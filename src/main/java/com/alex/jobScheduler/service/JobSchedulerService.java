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

		List notGroupedJobs = new ArrayList<String>();
		List jobsToGroup = new ArrayList<String>();
		
		int timeCounter = 0; 
		
		jobs.forEach((job) -> {
			if(job.getTempoEstimado() + timeCounter <= limitTimeInHours) {
				jobsToGroup.add(job);
			}
			
        });
		
	}
	
	public void sortJobsByMaxDate(List<Job> jobs) {
		jobs.sort(Comparator.comparing(Job::getDataMaximaDeConclusao));	
	}
	
	private int getMaxHoursToExecute(){
		return 8;
	}
}
