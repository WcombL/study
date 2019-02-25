package com.lei.activiti;

import org.activiti.engine.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ProcessEngineService {

	private static ProcessEngine engine;

	@Value(value = "${com.lei.activiti.engine.enable:true}")
	private boolean processEngineEnable;

	@PostConstruct
	public void postConstruct() {
		if (processEngineEnable) {
			ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg-8.0.xml");
			engine = cfg.buildProcessEngine();
		}
	}

	public ProcessEngine getEngine() {
		return engine;
	}

	@Bean
	public RuntimeService getRuntimeService() {
		return engine.getRuntimeService();
	}

	@Bean
	public IdentityService getIdentityService() {
		return engine.getIdentityService();
	}

	@Bean
	public TaskService getTaskService() {
		return engine.getTaskService();
	}

	@Bean
	public HistoryService getHistoryService() {
		return engine.getHistoryService();
	}

	@Bean
	public RepositoryService getRepositoryService() {
		return engine.getRepositoryService();
	}
}
