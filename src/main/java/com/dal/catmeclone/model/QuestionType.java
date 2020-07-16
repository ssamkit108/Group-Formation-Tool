package com.dal.catmeclone.model;

public enum QuestionType {

    NUMERIC("NUMERIC"), FREETEXT("FREETEXT"), MULTIPLECHOICEONE("MULTIPLECHOICEONE"),
    MULTIPLECHOICEMANY("MULTIPLECHOICEMANY");

    public final String type;

    QuestionType(String name) {
        this.type = name;
    }
}
