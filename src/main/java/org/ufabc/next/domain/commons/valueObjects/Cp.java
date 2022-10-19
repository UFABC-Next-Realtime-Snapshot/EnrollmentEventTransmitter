package org.ufabc.next.domain.commons.valueObjects;

import org.ufabc.next.domain.commons.exceptions.InvalidCp;

public class Cp {
    private final float value;

    private void validate() throws InvalidCp{
        if(this.value < 0){
            throw new InvalidCp("cp: invalid value, must be greater 0");
        }
        if(this.value > 1){
            throw new InvalidCp("cp: invalid value, must be lower 1");
        }
    }

    public Cp(float value) throws InvalidCp{
        this.value = value;
        this.validate();
    }

    public float value(){
        return this.value;
    }
}
