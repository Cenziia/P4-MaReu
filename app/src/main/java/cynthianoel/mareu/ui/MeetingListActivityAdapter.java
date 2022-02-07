package cynthianoel.mareu.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cynthianoel.mareu.R;
import cynthianoel.mareu.di.DI;
import cynthianoel.mareu.model.Meeting;
import cynthianoel.mareu.service.MeetingApiService;

public class MeetingListActivityAdapter extends RecyclerView.Adapter<MeetingListActivityAdapter.ViewHolder> {

    private final ArrayList<Meeting> mMeetings;

    public MeetingListActivityAdapter(ArrayList<Meeting> meetingArrayList) {
        this.mMeetings = meetingArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meeting meeting = mMeetings.get(position);
        holder.displayMeeting(mMeetings.get(position));
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mMeetings.remove(holder.getAbsoluteAdapterPosition());
                    notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                    notifyItemRangeChanged(holder.getAbsoluteAdapterPosition(), getItemCount());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(MeetingDetailActivity.newInstance(v.getContext(),meeting));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mSubject;
        private final TextView mHour;
        private final TextView mMeetingRoom;
        private final TextView mEmails;
        private final ImageButton mDeleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mSubject = itemView.findViewById(R.id.subjectTxt);
            mHour = itemView.findViewById(R.id.hourTxt);
            mMeetingRoom = itemView.findViewById(R.id.meetingRoomTxt);
            mEmails = itemView.findViewById(R.id.emailsTxt);
            mDeleteButton = itemView.findViewById(R.id.deleteButton);
        }

        public void displayMeeting(Meeting meeting){
            mSubject.setText(meeting.getSubject() + " - ");
            mHour.setText(meeting.getHourStart() + " - ");
            mMeetingRoom.setText(meeting.getMeetingRoom());
            mEmails.setText(meeting.getParticipants());
        }
    }

}
