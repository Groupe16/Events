package com.localisation.events.model;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by Zalila on 2015-04-18.
 */
public class FriendRequest {
    private int id;
    private String message;
    private User sender, receiver;
    private Date date;
    private Time time;

}
