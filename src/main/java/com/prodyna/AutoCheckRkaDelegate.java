package com.prodyna;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * Created by mfroehlich on 30.11.2015.
 */
public class AutoCheckRkaDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Auto-Checking rka.");
        Boolean valid = (Boolean) delegateExecution.getVariable(ProcessConstants.VARIABLE_autoCheckRkaValuesValid);

        System.out.println("Found autoCheckRkaSuccessful = " + valid);

        boolean autoCheckRkaSuccessful = valid != null && valid;
        delegateExecution.setVariable(ProcessConstants.VARIABLE_autoCheckRkaSuccessful, autoCheckRkaSuccessful);
    }
}
