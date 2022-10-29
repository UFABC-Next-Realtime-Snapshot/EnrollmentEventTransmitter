package org.ufabc.next.enrollmenteventtransmitter.infrastructure.jdbc.framework;

import java.util.Objects;

public class FakeEntity {
    private final Long id;
    private final String data;
    private final String anotherData;
    private final int fakeData;

    public FakeEntity(Long id, String data, String anotherData, int fakeData) {
        this.id = id;
        this.data = data;
        this.anotherData = anotherData;
        this.fakeData = fakeData;
    }

    public Long id() {
        return id;
    }

    public String data() {
        return data;
    }

    public String anotherData() {
        return anotherData;
    }

    public int fakeData() {
        return fakeData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FakeEntity that = (FakeEntity) o;
        return fakeData == that.fakeData && Objects.equals(id, that.id) && Objects.equals(data, that.data) && Objects.equals(anotherData, that.anotherData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, anotherData, fakeData);
    }
}
