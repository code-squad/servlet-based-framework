package next;

public enum ColorCodes {

	ANSI_RESET("\u001B[0m"), ANSI_GREEN("\u001B[32m"), ANSI_PURPLE("\u001B[35m"), ANSI_CYAN("\u001B[36m");

	private String code;

	private ColorCodes(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

}
