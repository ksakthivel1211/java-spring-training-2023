public class Candidate implements Comparable<Candidate>{
    private String candidateName;
    private int voteCount;

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public int compareTo(Candidate canObj) {
        int compareVote = canObj.getVoteCount();
        return compareVote - this.voteCount;
    }

    @Override
    public String toString()
    {
        return candidateName + " : " + voteCount ;
    }
}
