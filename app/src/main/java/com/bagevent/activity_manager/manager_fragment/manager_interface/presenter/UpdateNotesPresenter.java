package com.bagevent.activity_manager.manager_fragment.manager_interface.presenter;

import com.bagevent.activity_manager.manager_fragment.manager_interface.UpdateNotesInterface;
import com.bagevent.activity_manager.manager_fragment.manager_interface.impls.UpdateNotesImpl;
import com.bagevent.activity_manager.manager_fragment.manager_interface.manager_listener.OnEditNotesListener;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.UpdateAttendeeNotes;
import com.bagevent.activity_manager.manager_fragment.manager_interface.view.UpdateOrderNotesView;

/**
 * Created by WenJie on 2017/11/2.
 */

public class UpdateNotesPresenter {

    private UpdateNotesInterface updateNotes;
    private UpdateOrderNotesView orderNotesView;
    private UpdateAttendeeNotes attendeeNotes;

    public UpdateNotesPresenter(UpdateOrderNotesView orderNotesView) {
        this.updateNotes = new UpdateNotesImpl();
        this.orderNotesView = orderNotesView;
    }

    public UpdateNotesPresenter(UpdateAttendeeNotes attendeeNotes) {
        this.updateNotes = new UpdateNotesImpl();
        this.attendeeNotes = attendeeNotes;
    }

    public void updateAttendeeNotes() {
        updateNotes.editAttendeeNotes(attendeeNotes.mContext(), attendeeNotes.eventId(), Integer.parseInt(attendeeNotes.attendeeId()), attendeeNotes.notes(), new OnEditNotesListener() {
            @Override
            public void showEditNotesSuccess(String response) {
                attendeeNotes.editNotesSuccess(response);
            }

            @Override
            public void showEditNotesFailed(String errInfo) {
                attendeeNotes.editNotesFailed(errInfo);
            }
        });
    }

    public void updateOrderNotes() {
        updateNotes.editOrderNotes(orderNotesView.mContext(), orderNotesView.eventId(), orderNotesView.orderNumber(), orderNotesView.notes(), new OnEditNotesListener() {
            @Override
            public void showEditNotesSuccess(String response) {
                orderNotesView.editNotesSuccess(response);
            }

            @Override
            public void showEditNotesFailed(String errInfo) {
                orderNotesView.editNotesFailed(errInfo);
            }
        });
    }
}
