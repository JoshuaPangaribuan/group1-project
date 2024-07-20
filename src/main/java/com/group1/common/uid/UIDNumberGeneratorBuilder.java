package com.group1.common.uid;

final class  UIDNumberGenerator<T extends Number> implements IUIDGenerator<T> {
    private int length;

    @Override
    public T Generate() {
        throw new UnsupportedOperationException("Unimplemented method 'Generate'");
    }

    public void setLength(int length) {
        this.length = length;
    }

    protected int getLength() {
        return length;
    }
}

public class UIDNumberGeneratorBuilder<T extends Number> {
    private int length;

    public UIDNumberGeneratorBuilder<T> setLength(int length) {
        this.length = length;
        return this;
    }

    public IUIDGenerator<T> build() {
        UIDNumberGenerator<T> generator = new UIDNumberGenerator<T>();
        generator.setLength(length);
        return generator;
    }
}