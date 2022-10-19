package org.ufabc.next.domain.commons.valueObjects;

import org.ufabc.next.domain.commons.exceptions.InvalidCr;

public class Cr {
    private final float value;

    private void validate() throws InvalidCr {
        if(this.value < 0)
            throw new InvalidCr("CR: negative value");
        if (this.value > 4)
            throw new InvalidCr("CR: value greater than 4");
    }

    public Cr(float value) throws InvalidCr {
        this.value = value;
        this.validate();
    }

    public float value() {
        return this.value;
    }
}
