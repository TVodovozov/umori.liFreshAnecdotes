package com.vodovozov.umorilifreshanecdotes.ui;

import android.util.Log;

import com.vodovozov.umorilifreshanecdotes.rest.RestService;
import com.vodovozov.umorilifreshanecdotes.rest.models.QueryServerModel;
import com.vodovozov.umorilifreshanecdotes.ui.util.DataManager;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.io.IOException;
import java.util.List;

@EBean
public class BackgroundTask {

    private final static String LOG_TAG = BackgroundTask.class.getSimpleName();
    MainActivity_ mainActivity_;

    @Bean
    protected DataManager dataManager;

    @Background
    void connectionServer(String site, String name, int num) {
        RestService restService = new RestService();
        try {
            List<QueryServerModel> queryServerModel = restService.queryResponse(site, name, num);
            dataManager.loadQuotes(queryServerModel);

        } catch (IOException e) {
            e.printStackTrace();
            showUnknownError();
        }

    }

    @UiThread
    void showUnknownError() {
        mainActivity_.showUnknownError();
    }
}


