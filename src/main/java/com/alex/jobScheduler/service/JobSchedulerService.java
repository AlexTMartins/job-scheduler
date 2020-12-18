package com.alex.jobScheduler.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alex.jobScheduler.model.Job;

@Service
public class JobSchedulerService {

	public List<List<Job>> scheduleJobs(List<Job> jobs) {
			
		sortJobsByMaxDate(jobs);
		
		return null;
	}
	
	public void sortJobsByMaxDate(List<Job> jobs) {
		
		jobs.sort(Comparator.comparing(Job::getDataMaximaDeConclusao));	
	}
	
	
}
