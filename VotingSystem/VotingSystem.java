import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class VotingSystem {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        VotingBooth boothObj = new VotingBooth();
        boothObj.addCandidate("admk");
        boothObj.addCandidate("dmk");
        boothObj.addVoter("sakthi",123);
        boothObj.addVoter("sam",456);
        boothObj.addVoter("ram",789);
        System.out.println("Welcome to voting booth");
        boothObj.votingCheck();
    }
}
