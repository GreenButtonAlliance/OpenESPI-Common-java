package org.energyos.espi.common.test;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;

public class IsEmpty extends TypeSafeMatcher<List> {

    @Override
    public boolean matchesSafely(List list) {
        return list.isEmpty();
    }

    public void describeTo(Description description) {
        description.appendText("empty list");
    }

    @Factory
    public static <T> Matcher<List> isEmpty() {
        return new IsEmpty();
    }

}
