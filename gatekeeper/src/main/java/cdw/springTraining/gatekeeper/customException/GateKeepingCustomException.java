package cdw.springTraining.gatekeeper.customException;

public class GateKeepingCustomException extends RuntimeException{

    public GateKeepingCustomException(String message) {
        super(message);
    }

    public GateKeepingCustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public GateKeepingCustomException(Throwable cause) {
        super(cause);
    }
}
