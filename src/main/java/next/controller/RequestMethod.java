package next.controller;

public class RequestMethod {
	private String path;
	private String requestMethod;

	public RequestMethod(String path, String requestMethod) {
		this.path = path;
		this.requestMethod = requestMethod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((requestMethod == null) ? 0 : requestMethod.hashCode());
		return result;
	}	
}
