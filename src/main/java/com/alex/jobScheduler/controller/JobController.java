package com.alex.jobScheduler.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alex.jobScheduler.model.Job;
import com.alex.jobScheduler.service.JobSchedulerService;

@RestController
public class JobController {
	
	@Autowired
	private JobSchedulerService jobSchedulerService; 

	@PostMapping("/job/schedule")
	public ResponseEntity<List<List<Job>>> post(@RequestBody List<@Valid Job> jobs) {

		List<List<Job>> scheduledJobs = jobSchedulerService.scheduleJobs(jobs);
		
		return new ResponseEntity<>(scheduledJobs, HttpStatus.OK);	
	}
}
