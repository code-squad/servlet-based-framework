package core.ref;

import core.annotation.Controller;

@Controller
public class Student {
    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}