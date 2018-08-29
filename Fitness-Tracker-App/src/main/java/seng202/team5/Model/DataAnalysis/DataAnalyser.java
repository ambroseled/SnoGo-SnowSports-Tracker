package seng202.team5.Model.DataAnalysis;

import seng202.team5.Model.Activity;

public class DataAnalyser {

    BasicMetrics basic = new BasicMetrics();
    AdvancedMetrics advanced = new AdvancedMetrics();

    public void processActivity(Activity activity) {
        basic.analyseActivity(activity);

    }
}
