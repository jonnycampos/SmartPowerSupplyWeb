package com.sps.services.electricaldata;

import java.util.List;

public class ElectricalSampleList {

    private List<ElectricalSample> electricalSamples;

    public ElectricalSampleList(List<ElectricalSample> electricalSamples) {
        this.electricalSamples = electricalSamples;
    }

    public ElectricalSampleList() {}

    public java.util.List<ElectricalSample> getElectricalSampleList() {
        return electricalSamples;
    }

    public void setElectricalSampleList(List<ElectricalSample> electricalSamples) {
        this.electricalSamples = electricalSamples;
    }
}
