package com.example.um_event;

import android.content.Intent;

public class EventData {

    private String EventName;
    private String EventDate;
    private String EventTime;
    private String EventVenue;
    private String EventDetail;
    private String EventCategory;
    private Integer EventImage;

    public EventData(String eventName, String eventDate, String eventTime, String eventVenue, String eventDetail,
                     String eventCategory) {
        this.EventName = eventName;
        this.EventDate = eventDate;
        this.EventTime = eventTime;
        this.EventVenue = eventVenue;
        this.EventDetail = eventDetail;
        this.EventCategory = eventCategory;
      //  this.EventImage = eventImage;
    }

    public Integer getEventImage() {
        return EventImage;
    }

    public void setEventImage(Integer eventImage) {
        EventImage = eventImage;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }

    public String getEventTime() {
        return EventTime;
    }

    public void setEventTime(String eventTime) {
        EventTime = eventTime;
    }

    public String getEventVenue() {
        return EventVenue;
    }

    public void setEventVenue(String eventVenue) {
        EventVenue = eventVenue;
    }

    public String getEventDetail() {
        return EventDetail;
    }

    public void setEventDetail(String eventDetail) {
        EventDetail = eventDetail;
    }

    public String getEventCategory() {
        return EventCategory;
    }

    public void setEventCategory(String eventCategory) {
        EventCategory = eventCategory;
    }
}