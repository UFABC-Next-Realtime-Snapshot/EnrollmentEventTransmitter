package org.ufabc.next.enrollmenteventtransmitter.application.viewmodels;

import org.ufabc.next.enrollmenteventtransmitter.domain.commons.valueObjects.Ra;

public class StudentViewModel {
    public final String ra;
    public final String name;

    public StudentViewModel(
        Ra ra,
        String name
    ){
        this.ra = ra.toString();
        this.name = name;
    }
}
