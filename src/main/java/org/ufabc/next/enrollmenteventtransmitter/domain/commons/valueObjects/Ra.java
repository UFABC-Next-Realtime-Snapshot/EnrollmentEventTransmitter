package org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects;

import java.util.Objects;

public class Ra {
    private final String value;

    public Ra(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ra ra = (Ra) o;
        return Objects.equals(value, ra.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
