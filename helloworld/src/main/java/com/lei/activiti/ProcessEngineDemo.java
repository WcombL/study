package com.lei.activiti;

import org.activiti.engine.*;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessEngineDemo {
    private static final Logger logger = LoggerFactory.getLogger(ProcessEngineDemo.class);

    public static void main(String[] args) {
        ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg-8.0.xml");

        ProcessEngine processEngine = cfg.buildProcessEngine();


        // 根据流程定义开启一个流程实例，查询流程实例和执行流程
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 操作或管理流程定义和部署
        RepositoryService repositoryService = processEngine.getRepositoryService();
        TaskService taskService = processEngine.getTaskService();
        ManagementService managementService = processEngine.getManagementService();
        IdentityService identityService = processEngine.getIdentityService();
        HistoryService historyService = processEngine.getHistoryService();
        FormService formService = processEngine.getFormService();
        DynamicBpmnService dynamicBpmnService = processEngine.getDynamicBpmnService();

        // 部署
        // deploy(processEngine, "VacationRequest.bpmn20.xml");

        // 开始流程实例
        // startInstance(processEngine);

        // 完成任务
        // completeTask(processEngine);

        // 启用或禁用流程
        // enableOrDisableProcess(processEngine, false);

        // query API
        queryAPI(processEngine);

        // 变量存放在表，ACT_RU_VARIABLE

    }

    public static void deploy(ProcessEngine processEngine, String xml) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment()
                .addClasspathResource(xml)
                .deploy();

        logger.info("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());
    }

    public static void startInstance(ProcessEngine processEngine) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employeeName", "Kermit");
        variables.put("numberOfDays", new Integer(4));
        variables.put("vacationMotivation", "I'm really tired!");

        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("vacationRequest", variables);

        // Verify that we started a new process instance
        logger.info("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());
    }

    public static void completeTask(ProcessEngine processEngine) {
        // Fetch all tasks for the management group
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
        for (Task task : tasks) {
            logger.info("Task available: " + task.getName());
        }

        Task task = tasks.get(0);

        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("vacationApproved", "false");
        taskVariables.put("managerMotivation", "We have a tight deadline!");
        taskService.complete(task.getId(), taskVariables);
    }

    public static void enableOrDisableProcess(ProcessEngine processEngine, boolean status) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        if (status) {
            // 启用
            repositoryService.activateProcessDefinitionByKey("vacationRequest");
        } else {
            // 禁用
            repositoryService.suspendProcessDefinitionByKey("vacationRequest");
        }

        try {
            RuntimeService runtimeService = processEngine.getRuntimeService();
            runtimeService.startProcessInstanceByKey("vacationRequest");
        } catch (ActivitiException e) {
            e.printStackTrace();
        }
    }

    public static void queryAPI(ProcessEngine processEngine) {

        TaskService taskService = processEngine.getTaskService();

        List<Task> tasks = taskService.createTaskQuery()
                .taskAssignee("kermit")
                .processVariableValueEquals("orderId", "0815")
                .orderByTaskDueDate().asc()
                .list();

        ManagementService managementService = processEngine.getManagementService();
        List<Task> nativeTasks = taskService.createNativeTaskQuery()
                .sql("SELECT count(*) FROM " + managementService.getTableName(Task.class) + " T WHERE T.NAME_ = #{taskName}")
                .parameter("taskName", "gonzoTask")
                .list();

        long count = taskService.createNativeTaskQuery()
                .sql("SELECT count(*) FROM " + managementService.getTableName(Task.class) + " T1, "
                        + managementService.getTableName(VariableInstanceEntity.class) + " V1 WHERE V1.TASK_ID_ = T1.ID_")
                .count();
    }
}
