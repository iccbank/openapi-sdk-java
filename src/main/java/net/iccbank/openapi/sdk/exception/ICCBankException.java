package net.iccbank.openapi.sdk.exception;

/**
 * @Author kevin
 * @Description 统一异常处理
 * @Date Created on 2020/8/27 10:39
 * @since 1.0.1
 */
public class ICCBankException extends RuntimeException{

    public static final String RUNTIME_ERROR = "RuntimeError";
    public static final String INPUT_ERROR = "InputError";
    public static final String EXEC_ERROR = "ExecuteError";
    public static final String BIZ_ERROR = "BizError";

    private String subCode;

    private String subMsg;

    public ICCBankException(){

    }

    public ICCBankException(String subCode, String subMsg){
        super("[ "+ subCode +" ]" + subMsg);
        this.subCode = subCode;
        this.subMsg = subMsg;
    }

    public ICCBankException(String subCode, String subMsg, Throwable e){
        super("[ "+ subCode +" ]" + subMsg, e);
        this.subCode = subCode;
        this.subMsg = subMsg;
    }

    public static ICCBankException buildException(String subCode, String subMsg){
        ICCBankException iccBankException = new ICCBankException(subCode, subMsg);
        return iccBankException;
    }

    public static ICCBankException buildException(String subCode, String subMsg, Throwable e){
        ICCBankException iccBankException = new ICCBankException(subCode, subMsg, e);
        return iccBankException;
    }

    public String getSubCode() {
        return subCode;
    }

    public String getSubMsg() {
        return subMsg;
    }
}
