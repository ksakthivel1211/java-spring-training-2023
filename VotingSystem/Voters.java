public class Voters {
    private String name;
    private int voterId;
    private boolean flag;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.flag = true;
    }

    public int getVoterId() {
        return voterId;
    }
    public boolean getFlag()
    {
        return flag;
    }

    public void setVoterId(int voterId) {
        this.voterId = voterId;
    }

}
