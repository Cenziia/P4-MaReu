package cynthianoel.mareu.di;

import cynthianoel.mareu.data.DummyMeetingApiService;
import cynthianoel.mareu.service.MeetingApiService;

public class DI {

    private static final MeetingApiService service = new DummyMeetingApiService();

    /**
     * Get an instance on @{@link MeetingApiService}
     *
     * @return MeetingApiService
     */
    public static MeetingApiService getMeetingApiService(){
        return service;
    }

}
