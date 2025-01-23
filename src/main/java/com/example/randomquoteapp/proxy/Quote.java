package com.example.randomquoteapp.proxy;

public record Quote(String type, QuoteValue value) {

    @Override
    public String toString() {
        return "Type: " + type + ", " + value.id().toString() + ": " + value.quote() + "\n";
    }
}