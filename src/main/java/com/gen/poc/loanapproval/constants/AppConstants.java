package com.gen.poc.loanapproval.constants;

public class AppConstants {

    public final static String MSGEVNT_SIGNED_DOC_RECEIVED = "MSGEVNT_SIGNED_DOC_RECEIVED";

    public final static String DOC_SIGN_CORRELATION_KEY = MSGEVNT_SIGNED_DOC_RECEIVED + "-%s";

    public final static String MSGEVNT_MISSING_APP_DATA_RECIEVED_AKNWLG = "MSGEVNT_MISSING_APP_DATA_RECIEVED_AKNWLG";

    public final static String APP_UPDATED_CORRELATION_KEY = MSGEVNT_MISSING_APP_DATA_RECIEVED_AKNWLG + "-%s";

    public final static String MSGEVNT_AKNOWLEDGE_MISSING_DOC_PROVIDED = "MSGEVNT_AKNOWLEDGE_MISSING_DOC_PROVIDED";

    public final static String MISSING_DOC_CORRELATION_KEY = MSGEVNT_AKNOWLEDGE_MISSING_DOC_PROVIDED + "-%s";


    public final static String CAN_CANCEL = "CAN_CANCEL";

    public final static String CAN_APPROVEORREJECT = "CAN_APPROVEORREJECT";

    public final static String CAN_ACKNOWLEDGE_CORRECTION = "CAN_ACKNOWLEDGE_CORRECTION";

    public final static String CAN_ACKNOWLEDGE_DOC_PROVIDED = "CAN_ACKNOWLEDGE_DOC_PROVIDED";



}
