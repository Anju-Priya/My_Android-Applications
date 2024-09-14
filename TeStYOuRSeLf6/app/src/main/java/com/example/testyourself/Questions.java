package com.example.testyourself;

public class Questions {

    public String mQuestions[] = {
            "Mercury is the first planet in the Solar System",
            "Venus is the third planet in the Solar System",
            "Earth is the fourth planet in the Solar System",
            "Mars is the first planet in the Solar System",
            "Jupiter is the seventh planet in the Solar System",
            "Saturn is the fifth planet in the Solar System",
            "Uranus is the seventh planet in the Solar System",
            "Neptune is the eight planet in the Solar System",
    };

    public String mAnswers[] = {
            "true",
            "false",
            "false",
            "false",
            "false",
            "false",
            "true",
            "true"
    };

    public String getQuestion(int number) {
        return mQuestions[number];
    }

    public String getAnswer(int number) {
        return mAnswers[number];
    }
}