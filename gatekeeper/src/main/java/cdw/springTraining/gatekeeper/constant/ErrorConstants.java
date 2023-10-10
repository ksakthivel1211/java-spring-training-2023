package cdw.springTraining.gatekeeper.constant;

/**
 * @author sakthivel
 * ErrorConstant class contains error responses of type string
 */
public class ErrorConstants {

    public static final String NO_REGISTRATION_REQUEST = "There are no registration request yet";
    public static final String INVALID_REGISTRATION_NAME = "Only Residents and Gate keepers are allowed to register";
    public static final String USER_BLACK_LISTED_INVALID_REGISTRATION = "Unable to register : The user has been black listed";
    public static final String USER_NOT_FOUND_BY_MAIL = "User with the given mail is not found";
    public static final String USER_NOT_FOUND_BY_ID = "User with the given id is not found";
    public static final String REQUEST_NOT_FOUND_BY_ID = "Request with the given id is not found";
    public static final String ONLY_RESIDENT_BOOKING = "only resident will be able to book visitor slot";
    public static final String INVALID_CHECKED_VARIABLE = "Invalid checked variable";
    public static final String INVALID_APPROVAL_STATUS = "Invalid approval status : Gate keeper can only approve or reject";
    public static final String SLOT_NOT_FOUND_BY_ID = "Visitor slot with the given request id is not found";
    public static final String SLOT_NOT_FOUND_BY_DATE = "Visitor slot with the given request date is not found";
    public static final String USER_ALREADY_BLACK_LISTED = "User has already been black listed";
    public static final String USER_ALREADY_APPROVED = "User has already been approved";
}
