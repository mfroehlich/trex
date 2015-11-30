package com.prodyna.nonarquillian;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.complete;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.init;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.task;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.prodyna.ProcessConstants;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
public class ReisekostenTest {

    @Rule
    public ProcessEngineRule rule = new ProcessEngineRule();

    // enable more detailed logging
    static {
        // LogUtil.readJavaUtilLoggingConfigFromClasspath(); // process engine
        // LogFactory.useJdkLogging(); // MyBatis
    }

    @Before
    public void setup() {
        init(rule.getProcessEngine());
    }

    /**
     * Just tests if the process definition is deployable.
     */
    @Test
    @Deployment(resources = "reisekosten.bpmn")
    public void testParsingAndDeployment() {
    }

    @Test
    @Deployment(resources = "reisekosten.bpmn")
    public void testStartWorkflowAndReach_UserTask_createRka() {
        ProcessInstance processInstance = rule.getProcessEngine().getRuntimeService()
                .startProcessInstanceByKey(ProcessConstants.PROCESS_KEY);
        assertThat(processInstance).isStarted().isWaitingAtExactly(ProcessConstants.UserTask_createRka);
    }

    @Test
    @Deployment(resources = "reisekosten.bpmn")
    public void testStartWorkflow_AutoCheckSuccessful() {
        ProcessInstance processInstance = rule.getProcessEngine().getRuntimeService()
                .startProcessInstanceByKey(ProcessConstants.PROCESS_KEY);
        assertThat(processInstance).isStarted().isWaitingAtExactly(ProcessConstants.UserTask_createRka);

        complete(task(), Variables.createVariables().putValue(ProcessConstants.VARIABLE_autoCheckRkaValuesValid, true));

        assertThat(processInstance).isWaitingAtExactly(ProcessConstants.UserTask_manualCheckRka);
    }

    @Test
    @Deployment(resources = "reisekosten.bpmn")
    public void testStartWorkflow_AutoCheckNotSuccessful() {
        ProcessInstance processInstance = rule.getProcessEngine().getRuntimeService()
                .startProcessInstanceByKey(ProcessConstants.PROCESS_KEY);
        assertThat(processInstance).isStarted().isWaitingAtExactly(ProcessConstants.UserTask_createRka);

        complete(task(), Variables.createVariables().putValue(ProcessConstants.VARIABLE_autoCheckRkaValuesValid, false));

        assertThat(processInstance).hasPassed(ProcessConstants.ServiceTask_autoCheckRka,
                ProcessConstants.ExclusiveGateway_autoCheckRkaSuccessful, ProcessConstants.SendTask_refuseRka)
                .isWaitingAtExactly(ProcessConstants.UserTask_createRka);
    }

    @Test
    @Deployment(resources = "reisekosten.bpmn")
    public void testStartWorkflow_AutoCheckSuccessful_ManualCheckNotSuccessful() {
        ProcessInstance processInstance = rule.getProcessEngine().getRuntimeService()
                .startProcessInstanceByKey(ProcessConstants.PROCESS_KEY);
        assertThat(processInstance).isStarted().isWaitingAtExactly(ProcessConstants.UserTask_createRka);

        complete(task(), Variables.createVariables().putValue(ProcessConstants.VARIABLE_autoCheckRkaValuesValid, true));

        assertThat(processInstance).isWaitingAtExactly(ProcessConstants.UserTask_manualCheckRka);

        complete(task(),
                Variables.createVariables().putValue(ProcessConstants.VARIABLE_manualCheckRkaSuccessful, false));

        assertThat(processInstance).isWaitingAtExactly(ProcessConstants.UserTask_createRka).hasPassed(
                ProcessConstants.ExclusiveGateway_manualCheckRkaSuccessful, ProcessConstants.SendTask_refuseRka);
    }

    @Test
    @Deployment(resources = "reisekosten.bpmn")
    public void testStartWorkflow_AutoCheckSuccessful_ManualCheckSuccessful() {
        ProcessInstance processInstance = rule.getProcessEngine().getRuntimeService()
                .startProcessInstanceByKey(ProcessConstants.PROCESS_KEY);
        assertThat(processInstance).isStarted().isWaitingAtExactly(ProcessConstants.UserTask_createRka);

        complete(task(), Variables.createVariables().putValue(ProcessConstants.VARIABLE_autoCheckRkaValuesValid, true));

        assertThat(processInstance).isWaitingAtExactly(ProcessConstants.UserTask_manualCheckRka);

        complete(task(),
                Variables.createVariables().putValue(ProcessConstants.VARIABLE_manualCheckRkaSuccessful, true));

        assertThat(processInstance).isEnded();
    }
}
