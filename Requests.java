/**
 * Request class has all the information of the block and apply credit card requests
 */
public class Requests
{
    public String name;
    public int PAN;
    public String type;
    public int cardNo;
    public String cardType;

    public Requests(String name, int PAN,String type,String cardType) {
        this.name = name;
        this.PAN = PAN;
        this.type=type;
        this.cardType= cardType;
    }
    public Requests(int cardNo,int pan,String type)
    {
        this.cardNo = cardNo;
        this.PAN = pan;
        this.type=type;
    }
}