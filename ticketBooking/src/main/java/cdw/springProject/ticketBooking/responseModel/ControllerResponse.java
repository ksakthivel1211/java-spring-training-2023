package cdw.springProject.ticketBooking.responseModel;

public class ControllerResponse {
    private String successMessage;

    public ControllerResponse(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    @Override
    public String toString() {
        return "controllerResponse{" +
                "successMessage='" + successMessage + '\'' +
                '}';
    }
}
