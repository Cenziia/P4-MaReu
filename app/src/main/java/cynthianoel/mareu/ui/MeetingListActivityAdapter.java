package cynthianoel.mareu.ui;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cynthianoel.mareu.R;
import cynthianoel.mareu.databinding.ItemMeetingBinding;
import cynthianoel.mareu.di.DI;
import cynthianoel.mareu.model.Meeting;
import cynthianoel.mareu.service.MeetingApiService;

public class MeetingListActivityAdapter extends RecyclerView.Adapter<MeetingListActivityAdapter.ViewHolder> {

    private final ArrayList<Meeting> mMeetings;

    private final MeetingApiService meetingApiService = DI.getMeetingApiService();

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
                meetingApiService.deleteMeeting(meeting);

               //mMeetings.remove(holder.getAbsoluteAdapterPosition());
               notifyItemRemoved(holder.getAbsoluteAdapterPosition());
               notifyItemRangeChanged(holder.getAbsoluteAdapterPosition(), mMeetings.size());
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

    public class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {

        //private MeetingApiService mMeetingApiService;
        protected final SimpleDateFormat hourFormat = new SimpleDateFormat("HH'h'mm", Locale.FRANCE);

        private final TextView mSubject;
        private final TextView mHour;
        private final TextView mMeetingRoom;
        private final TextView mEmails;
        private final ImageButton mDeleteButton;
        private final ImageView mImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mSubject = itemView.findViewById(R.id.subjectTxt);
            mHour = itemView.findViewById(R.id.hourTxt);
            mMeetingRoom = itemView.findViewById(R.id.meetingRoomTxt);
            mEmails = itemView.findViewById(R.id.emailsTxt);
            mDeleteButton = itemView.findViewById(R.id.deleteButton);
            mImageView = itemView.findViewById(R.id.circleImg);

            /*mDeleteButton.setOnClickListener(this);*/
        }

        /*public void onClick(View v) {
            if(v.equals(mDeleteButton)) {
                removeAt(mMeetings.get(getAbsoluteAdapterPosition()));
            }
        }*/

        public void displayMeeting(Meeting meeting){
            Date hourDate = meeting.getHourStart();
            String hour = hourFormat.format(hourDate);

            mSubject.setText(meeting.getSubject() + " - ");
            mHour.setText(String.format(hour) + " - ");
            mMeetingRoom.setText(meeting.getMeetingRoom());
            mEmails.setText(meeting.getParticipants());
            setCircleColor(meeting);
            mImageView.getDrawable().setColorFilter(meeting.getCircleColor(), PorterDuff.Mode.MULTIPLY);
        }

        public void setCircleColor(Meeting meeting){
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
            SimpleDateFormat sdfHour = new SimpleDateFormat("HH", Locale.FRANCE);
            Date meetingDate = meeting.getDate();
            Date meetingHour = meeting.getHourStart();
            String meetingDateString = sdfDate.format(meetingDate);
            String meetingHourString = sdfHour.format(meetingHour);

            // If meeting append in less than one hour on current day
            if (meetingDateString.equals(MeetingListActivity.today())
                    && meetingHourString.equals(MeetingListActivity.currentHour())
                    || meetingDateString.equals(MeetingListActivity.today())
                    && meetingHour.before(MeetingListActivity.addHour(1))
                    && meetingHour.after(MeetingListActivity.addHour(-1))) {
                meeting.setCircleColor(Color.RED);
            }
            // If meeting append in more than one day
            else if (meetingDate.after(MeetingListActivity.addDay(1))) {
                meeting.setCircleColor(Color.BLUE);
            }
            // If meeting time is passed
            else if (meetingHour.before(MeetingListActivity.addHour(-1))) {
                meeting.setCircleColor(Color.GRAY);
            }
            // If meeting is between one and three hours
            else if (meetingDateString.equals(MeetingListActivity.today())
                    && meetingHour.before(MeetingListActivity.addHour(3))
                    && meetingHour.after(MeetingListActivity.addHour(1))) {
                meeting.setCircleColor(Color.parseColor("#FFAC1C"));
            }
            // If meeting is today after 3 hours
            else if (meetingDateString.equals(MeetingListActivity.today())
                    && meetingHour.after(MeetingListActivity.addHour(3))) {
                meeting.setCircleColor(Color.GREEN);
            }
            // If meeting is tomorrow
            else if (meetingDateString.equals(MeetingListActivity.tomorrow())) {
                meeting.setCircleColor(Color.CYAN);
            }
        }

        /*public void removeAt(Meeting meeting) {
            Meeting position = mMeetings.get(getAbsoluteAdapterPosition());
            mMeetingApiService.deleteMeeting(meeting);
            //notifyItemRemoved(position);
            //notifyItemRangeChanged(mMeetings, mMeetings.size());
        }*/
    }

}
