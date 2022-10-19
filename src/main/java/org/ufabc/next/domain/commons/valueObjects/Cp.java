package org.ufabc.next.domain.commons.valueObjects;

import org.ufabc.next.domain.commons.exceptions.InvalidCpException;

public class Cp {
    private final float value;

    private void validate() throws InvalidCpException{
        if(this.value < 0){
            throw new InvalidCpException("cp: invalid value, must be greater 0");
        }
        if(this.value > 1){
            throw new InvalidCpException("cp: invalid value, must be lower 1");
        }
    }

    public Cp() {
        this.value = 0;
    }
    public Cp(float value) throws InvalidCpException{
        this.value = value;
        this.validate();
    }

    public float value(){
        return this.value;
    }
}
