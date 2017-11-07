package core.mvc.requestmapping;

import javax.servlet.http.HttpServletRequest;

import core.annotation.RequestMethod;

public class RequestLine {
	
	private String path;
	private RequestMethod method;
	
	public RequestLine(String path, RequestMethod method) {
		this.path = path;
		this.method = method;
	}
	
	public RequestLine(HttpServletRequest req) {
		this.path = req.getRequestURI();
		this.method = RequestMethod.getMethodEnum(req);
	}

	public String getPath() {
		return path;
	}

	public RequestMethod getMethod() {
		return method;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
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
		RequestLine other = (RequestLine) obj;
		if (method != other.getMethod())
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.getPath()))
			return false;
		return true;
	}

}
