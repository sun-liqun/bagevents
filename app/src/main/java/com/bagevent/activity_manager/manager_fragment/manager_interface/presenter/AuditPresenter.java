package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.manager_interface.AuditInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.AuditImpls;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnAuditListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.AuditView;

/**
 * Created by zwj on 2016/7/7.
 */
public class AuditPresenter {

    private AuditInterface auditInterface;

    private AuditView auditView;

    public AuditPresenter(AuditView auditView) {
        this.auditView = auditView;
        this.auditInterface = new AuditImpls();
    }

    public void getAudit(final String audit){
        auditInterface.audit(auditView.mContext(),auditView.eventId() +"", auditView.attendeeId(), audit, auditView.upDateTime(), new OnAuditListener() {
            @Override
            public void showAuditPass() {
                auditView.showAuditSuccess();
            }

            @Override
            public void showAuditRefused() {
                auditView.showAuditFailed();
            }
        });
    }
}
