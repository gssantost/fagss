package fagss.org.facade;

import org.json.JSONArray;
import org.json.JSONObject;

import fagss.org.db.Comment;
import fagss.org.db.Media;
import fagss.org.db.User;

public class QueryFacade {
	
	private User user;
	private Media media;
	private Comment comment;
	
	public QueryFacade() {
		this.user = new User();
		this.media = new Media();
		this.comment = new Comment();
	}
	
	public JSONObject addUser(JSONObject json) {
		return this.user.insert(json);
	}
	
	public JSONObject checkUser(JSONObject json) {
		return this.user.check(json);
	}
	
	public JSONObject upload(JSONObject json) {
		return this.media.setMedia(json);
	}
	
	public JSONArray getAllVideos(JSONObject json) {
		return this.media.getAllVideos(json);
	}
	
	public JSONObject getMedia(JSONObject json) {
		return this.media.getMedia(json);
	}
	
	public String getVideo(int id) {
		return this.media.getVideo(id);
	}
	
	public JSONObject setComment(JSONObject json) {
		return this.comment.setComment(json);
	}
	
	public JSONObject banComment(JSONObject json) {
		return this.comment.banComment(json);
	}
	
	public JSONObject removeVideo(int id) {
		return this.media.removeVideo(id);
	}
	
	public JSONObject deleteComment(JSONObject json) {
		return this.comment.deleteComment(json);
	}
	
	public boolean isUserVideo(JSONObject json) {
		return this.media.isUserVideo(json);
	}
	
	public boolean isUserComment(JSONObject json) {
		return this.comment.isUserComment(json);
	}
}
