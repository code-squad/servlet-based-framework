package next.controller;

public class RequestMethod {
	String path;
	String requestMethod;

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestMethod other = (RequestMethod) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (requestMethod != other.requestMethod)
			return false;
		return true;
	}
	
	
}
