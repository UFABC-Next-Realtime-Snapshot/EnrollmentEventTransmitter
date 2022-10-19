package org.ufabc.next.domain.student;

import org.ufabc.next.domain.commons.valueObjects.Cp;
import org.ufabc.next.domain.commons.valueObjects.Cr;
import org.ufabc.next.domain.commons.valueObjects.Ra;
import org.ufabc.next.domain.commons.valueObjects.Shift;

public interface IStudent {
    String name();
    Ra ra();
    Cr cr();
    Cp cp();
    Shift shift();
}