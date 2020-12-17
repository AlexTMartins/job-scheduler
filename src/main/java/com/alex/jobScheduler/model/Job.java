package com.alex.jobScheduler.model;

import java.time.LocalDateTime;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@AllArgsConstructor
@Getter
public class Job {

	private long id;
	
	@NonNull
	private String descricao;
	
	@NonNull
	private LocalDateTime dataMaximaDeConclusao;
	
	@Min(value = 1, message = "tempo deve ser maior que 1h")
    @Max(value = 8, message = "tempo deve ser menor que 8h")
	private int tempoEstimado;
	
}