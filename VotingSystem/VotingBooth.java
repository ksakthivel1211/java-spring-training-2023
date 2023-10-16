import java.util.*;
import java.util.concurrent.*;

/**
 * @author sakthivel
 * VotingBooth class has the main implementation
 */
public class VotingBooth extends Thread{
    ArrayList<Voters> voterList = new ArrayList<>();
    ArrayList<Candidate> candidateList = new ArrayList<>();

    /**
     *
     * @param name - candidate name is passed as parameter
     */
    public void addCandidate(String name)
    {
        Candidate candidateObj = new Candidate();
        candidateObj.setCandidateName(name);
        candidateObj.setVoteCount(0);
        candidateList.add(candidateObj);
        System.out.println("Candidates added");
    }

    /**
     *
     * @param name - voters name is passed as parameter
     * @param voterId - voterId of the voter is passes as parameter
     */
    public void addVoter(String name,int voterId)
    {
        Voters voterObj = new Voters();
        voterObj.setName(name);
        voterObj.setVoterId(voterId);
        voterList.add(voterObj);
        System.out.println("Voters added");
    }

    /**
     * votingCheck method is responsible for the flow of voting
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void votingCheck() throws ExecutionException, InterruptedException{
            while(true) {
                System.out.println("1.Vote\n2.Display vote count\n3.Exit");
                Scanner sc = new Scanner(System.in);
                int choice = sc.nextInt();
                if(choice==1)
                {
                    for (Voters voters: voterList) {
                        if (voters.getFlag()) {
                            try {
                                ExecutorService service = Executors.newSingleThreadExecutor();
                                Future<?> result = service.submit(() -> {
                                    System.out.println("Welcome : " + voters.getName());
                                    candidateVoting();
                                });
                                result.get(20, TimeUnit.SECONDS);
                                System.out.println("within time");
                            } catch (TimeoutException e) {
                                System.out.println("Sry you have reached the maximum time limit");
                            }
                        }
                        else {
                            System.out.println(voters.getName()+ " has already voted");
                        }
                    }
                } else if (choice==2) {
                    displayVoteCount();
                } else if (choice==3) {
                    break;
                } else
                {
                    System.out.println("Invalid choice");
                }
//                sc.close();
            }
    }

    /**
     * candidateVoting method is responsible for validation and vote count updation
     */
    public void candidateVoting()
    {
        while(true) {
            Scanner sc = new Scanner(System.in);
            String userChoice = null;
            System.out.println("Choose a candidate to vote:");
            String finalUserChoice = userChoice;
            Thread warning = new Thread(()->{
//                int totalTime  = 20;
//               while(totalTime>0 && (finalUserChoice ==null|| finalUserChoice.isEmpty()))
//               {
                   try{
                       Thread.sleep(10000);
                       System.out.println("Please input the candidate name : ");
//                       totalTime-=10;
                   }
                   catch (InterruptedException e)
                   {
//                       break;
                       System.out.println("interrupted");
                   }
//               }
            });
            warning.start();
            int flag =0;
            for (Candidate candidatesObj:candidateList) {
                System.out.println("-> "+candidatesObj.getCandidateName());
            }
            userChoice = sc.next();
            warning.interrupt();
            for (Candidate candidateObj:candidateList) {
                if(candidateObj.getCandidateName().equals(userChoice)) {
                        System.out.println("Are you sure you want to vote for: " + userChoice + "\n Choose :  Y/N");
                        String confirm = sc.next();
                        if (confirm.equals("Y")) {
                            int voteCounts = candidateObj.getVoteCount() + 1;
                            candidateObj.setVoteCount(voteCounts);
                            System.out.println("Vote registered successfully ");
                            flag=1;
                        }
                        else if (confirm.equals("N")) {
                            System.out.println("You have canceled your confirmation");
                            flag=2;
                        }
                        else{
                            System.out.println("Invalid choice...");
                        }
            }
            }
            if(flag==1)
            {
                break;
            }
            else if (flag==2) {
                continue;
            }
            else{
                System.out.println("Invalid option");
            }
//            sc.close();
        }

    }

    /**
     * displayVoteCount method is responsible for displaying the candidates in descending order according to their vote count
     */
    public void displayVoteCount()
    {
        Collections.sort(candidateList);
        for (Candidate candidateObj:candidateList) {
            System.out.println(candidateObj);
        }
    }
}
