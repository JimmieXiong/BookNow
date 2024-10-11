package edu.metrostate.booknow.Models;

public class TimeSlot {

    private int slotId;
    private String slotLabel;

    public TimeSlot() {
    }

    public TimeSlot(int slotId, String slotLabel) {
        this.slotId = slotId;
        this.slotLabel = slotLabel;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public String getSlotLabel() {
        return slotLabel;
    }

    public void setSlotLabel(String slotLabel) {
        this.slotLabel = slotLabel;
    }

    @Override
    public String toString() {
        return "TimeSlot [slotId=" + slotId + ", slotLabel=" + slotLabel + "]";
    }
}
