package cdw.springtraining.gatekeeper.constant;

/**
 * @author sakthivel
 * ErrorConstant class contains error responses of type string
 */
public class ErrorConstants {

    public static final String NO_REGISTRATION_REQUEST = "There are no registration request yet";
    public static final String INVALID_REGISTRATION_NAME = "Only Residents and Gate keepers can be register";
    public static final String USER_BLACK_LISTED_INVALID_REGISTRATION = "Unable to register : The user has been black listed";
    public static final String USER_NOT_FOUND_BY_MAIL = "User with the given mail is not found";
    public static final String USER_NOT_FOUND_BY_ID = "User with the given id is not found";
    public static final String REQUEST_NOT_FOUND_BY_ID = "Request with the given id is not found";
    public static final String ONLY_RESIDENT_BOOKING = "only resident will be able to book visitor slot";
    public static final String INVALID_CHECKED_VARIABLE = "Invalid checked variable";
    public static final String ONLY_RESIDENT_CHECKING = "only residents are allowed to checking";
    public static final String INVALID_APPROVAL_STATUS = "Invalid approval status : Gate keeper can only approve or reject";
    public static final String SLOT_NOT_FOUND_BY_ID = "Visitor slot with the given request id is not found";
    public static final String SLOT_NOT_FOUND_BY_DATE = "Visitor slot with the given request date is not found";
    public static final String VISITOR_SLOT_REJECTED = "Visitor slot has been rejected so can't be deleted";
    public static final String VISITOR_SLOT_NOT_ACCEPTED_NO_VISITOR_PASS = "Visitor pass cannot be generated cause the slot has been";
    public static final String VISITOR_SLOT_DELETE_AFTER_TIME = "Visitor slot can't be deleted after the slot time";
    public static final String USER_ALREADY_BLACK_LISTED = "User has already been black listed";
    public static final String USER_ALREADY_EXISTS = "User has already exists";
    public static final String REGISTRATION_REQUEST_ALREADY_EXISTS = "Registration request has already exists";
    public static final String USER_ALREADY_APPROVED_AND_ADDED = "User has already been approved and added to the user base";
    public static final String USER_APPROVAL_ALREADY_DONE = "User has already been ";
    public static final String VISITOR_APPROVAL_ALREADY_DONE = "Visitor has already been ";
    public static final String IN_TIME_OUT_TIME_CONTRADICTION = "In time should be lesser than out time";
    public static final String RESIDENT_BLACK_LIST_ONLY_VISITOR = "Resident can only black-list a visitor and not a user";
    public static final String ADMIN_BLACK_LIST_ONLY_VISITOR = "Admin can only black-list a gate-keeper and visitor";
    public static final String ONLY_ADMIN_RESIDENT_BLACK_LIST = "Only admin and gatekeeper are allowed to black list";
}
