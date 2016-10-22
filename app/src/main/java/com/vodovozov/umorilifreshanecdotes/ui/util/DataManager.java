package com.vodovozov.umorilifreshanecdotes.ui.util;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.vodovozov.umorilifreshanecdotes.dataBase.UmoriliDataBase;
import com.vodovozov.umorilifreshanecdotes.dataBase.entityes.AllJokesEntity;
import com.vodovozov.umorilifreshanecdotes.rest.models.QueryServerModel;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import java.util.List;

@EBean
public class DataManager {

    public void loadQuotes(final List<QueryServerModel> quotes) {

        FlowManager.getDatabase(UmoriliDataBase.class).executeTransaction(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                for (QueryServerModel quote : quotes) {
                    if (quote.getLink() != null) {
                        AllJokesEntity allJokesEntity = new AllJokesEntity();
                        allJokesEntity.setId(quote.getLink());
                        allJokesEntity.setText(quote.getElementPureHtml());
                        allJokesEntity.setFavorites(false);
                        allJokesEntity.save(databaseWrapper);
                    }
                }
            }

            ;
        });
    }
}
