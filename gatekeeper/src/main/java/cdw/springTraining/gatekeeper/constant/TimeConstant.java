package cdw.springTraining.gatekeeper.constant;

import lombok.Getter;
import org.springframework.stereotype.Service;

/**
 * @author sakthivel
 * TimeConstant class contains time constant value of type int
 */
public class TimeConstant {

    public static final int VISITOR_PASSKEY_TIME = 5*60*1000;
    public static final int USER_TOKEN_TIME = 30*60*1000;
}
