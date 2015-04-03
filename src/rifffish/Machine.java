package rifffish;

public class Machine {
	private int id = -1;
	private String name = null;
	private String config_name = null;
	private String mode = null;
	private String currency = null;
	
	
	// Yay for autogenerated Getters & Setters
	
	
	public Machine(String name, String configName, String mode, String currency) {
		this.name = name;
		this.config_name = configName;
		this.mode = mode;
		this.currency = currency;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getConfigName() {
		return config_name;
	}
	
	public void setConfigName(String config_name) {
		this.config_name = config_name;
	}
	
	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
	
}