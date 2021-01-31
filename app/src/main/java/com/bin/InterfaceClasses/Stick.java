package com.bin.test_11;

public class Stick {
    private String name;
    private Joint start;
    private Joint end;

    public String getName() {
        return name;
    }

    public Stick(String inputName, Joint inputStart, Joint inputEnd) {
        this.name =inputName;
        this.start = inputStart;
        this.end = inputEnd;
    }

    public Joint getStart() {
        return start;
    }

    public Joint getEnd() {
        return end;
    }

    public void update(Joint newStart, Joint newEnd) {
        this.start = newStart;
        this.end = newEnd;
    }
}
