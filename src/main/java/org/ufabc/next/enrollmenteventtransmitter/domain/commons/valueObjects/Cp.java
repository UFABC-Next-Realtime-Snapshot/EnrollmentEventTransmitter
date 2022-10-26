package org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.exceptions.InvalidCpException;

import static org.ufabc.next.enrollmenteventtransmitter.domain.commons.PreConditions.checkArgument;

public class Cp {
    private final float value;

    public Cp() {
        this.value = 0;
    }

    public Cp(float value) throws InvalidCpException {
        this.value = value;
        this.validate();
    }

    private void validate() throws InvalidCpException {
        checkArgument(this.value < 0, new InvalidCpException("cp: invalid value, must be greater 0"));
        checkArgument(this.value > 1, new InvalidCpException("cp: invalid value, must be lower 1"));
    }

    public float value() {
        return this.value;
    }
}
