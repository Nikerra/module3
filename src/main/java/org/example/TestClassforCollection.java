package org.example;

public class TestClassforCollection {
    @Override
    public String toString() {
        return "testValue=" + testValue;
    }

    public TestClassforCollection(int testValue) {
        this.testValue = testValue;
    }

    public int getTestValue() {
        return testValue;
    }

    public void setTestValue(int testValue) {
        this.testValue = testValue;
    }

    private int testValue;
}
