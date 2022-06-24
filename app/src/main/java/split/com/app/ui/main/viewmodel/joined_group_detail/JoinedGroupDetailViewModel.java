package split.com.app.ui.main.viewmodel.joined_group_detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import split.com.app.data.model.basic_model.BasicModel;
import split.com.app.data.repository.split_score.ScoreRepository;

public class JoinedGroupDetailViewModel extends ViewModel {

    private MutableLiveData<BasicModel> data;
    private ScoreRepository scoreRepository;
    String group_id , admin_id, score;

    public JoinedGroupDetailViewModel(String group , String admin , String score_value) {
        this.group_id = group;
        this.admin_id = admin;
        this.score = score_value;
        scoreRepository = new ScoreRepository();
    }

    public void init() {
        if (this.data != null) {
            // ViewModel is created per Fragment so
            // we know the userId won't change
            return;
        }
        data = scoreRepository.uploadScore(group_id,admin_id,score);
    }

    public MutableLiveData<BasicModel> getData() {
        return this.data;
    }
}
