package cdw.springTraining.gatekeeper.constant;

import lombok.Getter;
import org.springframework.stereotype.Service;

/**
 * @author sakthivel
 * SuccessConstant class contains success responses of type string
 */
public class SuccessConstants {

    public static final String USER_REGISTERED_SUCCESS = "User request has been successfully saved";
    public static final String RESIDENT_CHECKED = "The resident has checked-";
    public static final String VISITOR_SLOT_BOOKED = "Slot for the visitor has been booked";
    public static final String VISITOR_SLOT_CANCELLED = "The visitor slot has been cancelled";
    public static final String VISITOR_SLOT_APPROVAL = "The visitor slot has been : ";
    public static final String USER_BLACK_LISTED = "The user has been black listed";
    public static final String REQUEST_ACCEPTED = "Request has been accepted and user has been added";
    public static final String REQUEST_REJECTED = "Request has been rejected";
    public static final String USER_DELETED = "User has been deleted successfully";
    public static final String USER_UPDATED = "User details has been updated";
}
