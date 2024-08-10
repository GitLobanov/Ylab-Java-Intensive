package com.backend.util;

import com.backend.model.User;
import lombok.Getter;
import lombok.Setter;

public class Session {

    @Setter
    @Getter
    private User user;
    private boolean active = false;

    private static Session instance;

    public static Session getInstance (){
        if (instance == null){
            synchronized (Session.class){
                if (instance == null){
                    instance = new Session();
                }
            }
        }
        return instance;
    }

    public void activate (){
        this.active = true;
    }

    public void deactivate (){
        this.active = false;
    }

    public boolean isActive (){
        return this.active;
    }

    public void reset() {
        this.user = null;
        this.active = false;
        this.stage = Stage.HAVE_TO_LOGIN;
    }

    @Getter
    @Setter
    private Stage stage = Stage.HAVE_TO_LOGIN;

    public enum Stage {
        HAVE_TO_LOGIN, ADMIN, CLIENT, MANAGER, EXIT
    }

}
