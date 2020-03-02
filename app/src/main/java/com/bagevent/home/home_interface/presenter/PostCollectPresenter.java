package com.bagevent.home.home_interface.presenter;

import com.bagevent.home.data.ExportData;
import com.bagevent.home.home_interface.OnPostCollectionInfo;
import com.bagevent.home.home_interface.PostCollectionInfoInterface;
import com.bagevent.home.home_interface.impls.PostCollectionInfoImpls;
import com.bagevent.home.home_interface.view.PostCollectionView;

/**
 * Created by zwj on 2016/7/14.
 */
public class PostCollectPresenter {
    private PostCollectionInfoInterface postCollection;
    private PostCollectionView postCollectionView;

    public PostCollectPresenter(PostCollectionView postCollectionView) {
        this.postCollectionView = postCollectionView;
        this.postCollection = new PostCollectionInfoImpls();
    }

    public void postCollection() {
        postCollection.postCollectionInfo(postCollectionView.mContext(),postCollectionView.collectionId(), postCollectionView.barcode(), postCollectionView.checkInTime(), new OnPostCollectionInfo() {
            @Override
            public void postSuccess(ExportData response) {
                postCollectionView.showPostSuccess(response);
            }

            @Override
            public void postFailed() {
                postCollectionView.showPostFailed();
            }
        });
    }
}
