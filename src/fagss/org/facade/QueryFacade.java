package fagss.org.facade;

import org.json.JSONObject;

import fagss.org.db.UserDAO;

public class QueryFacade {
	
	public QueryFacade() {}
	
	public JSONObject addUser(JSONObject json) {
		UserDAO userDAO = new UserDAO();
		return userDAO.insert(json);
	}
	
	public JSONObject checkUser(JSONObject json) {
		UserDAO userDAO = new UserDAO();
		return userDAO.check(json);
	}

}
