package ru.coursework.gradebook.record.attendance;

public enum AttendanceStatus {
    PRESENT("+"),
    ABSENT("Н"),
    LATE("О");

    private final String displayName;

    AttendanceStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
