package com.bagevent.home.home_interface.presenter;

import com.bagevent.home.home_interface.ExportCollectionInterface;
import com.bagevent.home.home_interface.OnExportCollection;
import com.bagevent.home.home_interface.impls.ExportCollectionImpls;
import com.bagevent.home.home_interface.view.ExportCollectView;

/**
 * Created by zwj on 2016/7/14.
 */
public class ExportCollectPresenter {
    private ExportCollectionInterface exportCollection;
    private ExportCollectView exportCollectView;

    public ExportCollectPresenter(ExportCollectView exportCollectView) {
        this.exportCollectView = exportCollectView;
        this.exportCollection = new ExportCollectionImpls();
    }

    public void export() {
        exportCollection.exportCollect(exportCollectView.mContext(),exportCollectView.collectionId(), exportCollectView.eventId(), exportCollectView.userEmail(), new OnExportCollection() {
            @Override
            public void exportSuccess() {
                exportCollectView.showExportSuccess();
            }

            @Override
            public void exportFailed() {
                exportCollectView.showExportFailed();
            }
        });
    }
}
