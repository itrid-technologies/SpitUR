package split.com.app.data.model.group_score;

import com.google.gson.annotations.SerializedName;


public class GroupSplitScoreItem{

	@SerializedName("user_id")
	private int userId;

	@SerializedName("split_score")
	private int splitScore;


	@SerializedName("split_group_user_id")
	private SplitGroupUserId splitGroupUserId;

	@SerializedName("id")
	private int id;

	public int getUserId(){
		return userId;
	}

	public int getSplitScore(){
		return splitScore;
	}

	public SplitGroupUserId getSplitGroupUserId(){
		return splitGroupUserId;
	}

	public int getId(){
		return id;
	}
}