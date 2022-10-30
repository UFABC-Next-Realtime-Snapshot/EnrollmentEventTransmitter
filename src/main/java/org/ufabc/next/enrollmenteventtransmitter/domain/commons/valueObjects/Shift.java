package org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects;

import java.util.Arrays;

public enum Shift {
    MORNING('M'), NIGHT('N');

    private final char initial;

    Shift(char initial) {
        this.initial = initial;
    }

    public char initial() {
        return initial;
    }

    public static Shift fromInitial(char initial) {
        return Arrays.stream(Shift.values())
                .filter(shift -> shift.initial == initial)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String
                        .format("Initial %s Shift nonexistent", initial)));
    }
}
