package core.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModelAndView {
	private View view;
	private Map<String, Object> model = new HashMap<>();

	public ModelAndView(View view) {
		this.view = view;
	}

	public View getView() {
		return view;
	}

	public void setAttribute(String key, Object value) {
		model.put(key, value);
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public void render(HttpServletRequest request, HttpServletResponse response) throws Exception {
		view.render(model, request, response);
	}
}
