package org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.exceptions.InvalidCrException;

import static org.ufabc.next.enrollmenteventtransmitter.domain.commons.PreConditions.checkArgument;

public class Cr {
    private final float value;

    public Cr() {
        this.value = 0;
    }

    public Cr(float value) throws InvalidCrException {
        this.value = value;
        this.validate();
    }

    private void validate() throws InvalidCrException {
        checkArgument(this.value < 0, new InvalidCrException("CR: negative value"));
        checkArgument(this.value > 4, new InvalidCrException("CR: value greater than 4"));
    }

    public float value() {
        return this.value;
    }
}
