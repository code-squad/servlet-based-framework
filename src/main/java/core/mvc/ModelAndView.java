package core.mvc;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class ModelAndView {
	private View view;
	private Map<String, Object> model = new HashMap<String, Object>();

	public ModelAndView() {
	}

	public ModelAndView(View view) {
		this.view = view;
	}

	public void addObject(String attributeName, Object attributeValue) {
		model.put(attributeName, attributeValue);
	}

	public void addObject(Map<String, Object> addModel) {
		model.putAll(addModel);
	}

	public void addObject(HttpServletRequest request) {
		model.putAll(createModel(request));
	}

	public Map<String, Object> getModel() {
		return Collections.unmodifiableMap(model);
	}

	public View getView() {
		return view;
	}

	private Map<String, Object> createModel(HttpServletRequest request) {
		Enumeration<String> names = request.getAttributeNames();
		Map<String, Object> model = new HashMap<>();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			model.put(name, request.getAttribute(name));
		}
		return model;
	}
}
