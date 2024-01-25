package com.example.sqlite;

public class Settings{

    private boolean notifications;
    private boolean export;

    public Settings(){
        this.notifications = false;
        this.export = false;
    }

    public void notifcationsOn(){
        this.notifications = true;
    }

    public void notifcationsOff(){
        this.notifications = false;
    }

    public void exportOn(){
        this.export = true;
    }

    public void exportOff(){
        this.export = false;
    }

    public boolean getNotifications(){
        return this.notifications;
    }

    public boolean getExport(){
        return this.export;
    }
    public String toString () {
    return ("Notification:"+notifications+",Export:"+ export );
   }
}
