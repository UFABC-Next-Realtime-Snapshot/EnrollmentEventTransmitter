package org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.exceptions.InvalidCrException;

public class Cr {
    private final float value;

    private void validate() throws InvalidCrException {
        if(this.value < 0)
            throw new InvalidCrException("CR: negative value");
        if (this.value > 4)
            throw new InvalidCrException("CR: value greater than 4");
    }

    public Cr() {
        this.value = 0;
    }
    public Cr(float value) throws InvalidCrException {
        this.value = value;
        this.validate();
    }

    public float value() {
        return this.value;
    }
}
