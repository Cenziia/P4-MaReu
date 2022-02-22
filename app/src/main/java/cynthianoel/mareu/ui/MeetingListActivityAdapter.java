package cynthianoel.mareu.ui;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
        //holder.mImageView.setColorFilter(R.color.redCircle);

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mMeetings.remove(holder.getAbsoluteAdapterPosition());
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected static final SimpleDateFormat hourFormat = new SimpleDateFormat("HH'h'mm", Locale.FRANCE);

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
        }

        public void displayMeeting(Meeting meeting){
            Date hourDate = meeting.getHourStart();
            String hour = hourFormat.format(hourDate);

            mSubject.setText(meeting.getSubject() + " - ");
            mHour.setText(String.format(hour) + " - ");
            mMeetingRoom.setText(meeting.getMeetingRoom());
            mEmails.setText(meeting.getParticipants());

            //mImageView.getDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
            setCircleColor(meeting);
        }

        public void setCircleColor(Meeting meeting){
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = meeting.getDate();
            String date2 = sdf.format(date1);
            Calendar cal = Calendar.getInstance();
            Date cal1 = cal.getTime();
            String cal2 = sdf.format(cal1);
            System.out.println(date2);
            System.out.println(date1);
            System.out.println(cal2);
            if (/*date.equals("22/02/2022")*/ date2.equals(cal2)) {
                //meeting.setCircleColor(Color.GREEN);
                mImageView.getDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
            } else {
                mImageView.getDrawable().setColorFilter(meeting.getCircleColor(), PorterDuff.Mode.MULTIPLY);
            }
        }
    }

}
