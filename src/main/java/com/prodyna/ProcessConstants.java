package com.prodyna;

/**
 * Created by mfroehlich on 30.11.2015.
 */
public class ProcessConstants {
    public static final String PROCESS_KEY = "Process_rka";

    public static final String UserTask_createRka = "UserTask_createRka";
    public static final String UserTask_manualCheckRka = "UserTask_manualCheckRka";
    public static final String ServiceTask_autoCheckRka  = "ServiceTask_autoCheckRka";
    public static final String SendTask_refuseRka = "SendTask_refuseRka";

    public static final String EndEvent_rkaAccepted = "EndEvent_rkaAccepted";

    public static final String ExclusiveGateway_autoCheckRkaSuccessful = "ExclusiveGateway_autoCheckRkaSuccessful";
    public static final String ExclusiveGateway_manualCheckRkaSuccessful = "ExclusiveGateway_manualCheckRkaSuccessful";
    public static final String SequenceFlow_manualCheckSuccessful = "SequenceFlow_manualCheckSuccessful";

    public static final String MessageFlow_rkaCreated = "MessageFlow_rkaCreated";
    public static final String MessageFlow_rkaFinalized = "MessageFlow_rkaFinalized";
    public static final String MessageFlow_rkaRefused = "MessageFlow_rkaRefused";
    public static final String MessageFlow_rkaAccepted = "MessageFlow_rkaAccepted";

    public static final String VARIABLE_autoCheckRkaValuesValid = "autoCheckRkaValuesValid";
    public static final String VARIABLE_autoCheckRkaSuccessful = "autoCheckRkaSuccessful";
    public static final String VARIABLE_manualCheckRkaSuccessful = "manualCheckRkaSuccessful";
}
