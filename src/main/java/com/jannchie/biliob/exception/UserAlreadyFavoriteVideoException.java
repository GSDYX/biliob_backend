package com.jannchie.biliob.exception;

import javax.validation.Valid;

/**
 * @author jannchie
 */
public class UserAlreadyFavoriteVideoException extends Exception {
    private Long aid;
    public UserAlreadyFavoriteVideoException(@Valid Long aid) {
        this.aid = aid;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }
}