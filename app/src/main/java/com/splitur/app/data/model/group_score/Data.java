package com.splitur.app.data.model.group_score;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("group_title")
	private String groupTitle;

	@SerializedName("group_split_score")
	private List<GroupSplitScoreItem> groupSplitScore;

	public String getGroupTitle(){
		return groupTitle;
	}

	public List<GroupSplitScoreItem> getGroupSplitScore(){
		return groupSplitScore;
	}
}