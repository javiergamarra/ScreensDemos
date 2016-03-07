package com.ezentis.ezentistrackingapp;

/**
 * @author Javier Gamarra
 */
public enum State {

    NOTHING(""), START_DAY("Start day"), START_PAUSE("Start pause"),
    END_PAUSE("End pause"), END_DAY("End day");

    private String _value;


    State(String value) {
        _value = value;
    }

    public static State fromInt(int state) {
        return State.values()[state];
    }

    public String getValue() {
        return _value;
    }
}
