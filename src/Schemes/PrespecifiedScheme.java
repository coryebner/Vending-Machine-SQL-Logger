package Schemes;

public class PrespecifiedScheme extends OfflineScheme{
	
	private int numberOfTransactions = 0;
	
	public PrespecifiedScheme(int numberOfTransactions){
		this.type = "PRESPECIFIED";
		this.numberOfTransactions = numberOfTransactions;
	}
}