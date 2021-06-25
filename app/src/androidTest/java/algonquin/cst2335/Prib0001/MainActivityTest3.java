package algonquin.cst2335.Prib0001;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest3 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);



    @Test
    public void testFindMissingUpperCase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        appCompatEditText.perform(replaceText("password123#$*"));


        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.logintextview));

        textView.check(matches(withText("You shall not pass!")));
    }


    @Test
    public void testFindMissingUwpperCase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        appCompatEditText.perform(replaceText("PASSWORD123#$*"));


        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.logintextview));

        textView.check(matches(withText("You shall not pass!")));
    }


    @Test
    public void testFindMissingSpecialCase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        appCompatEditText.perform(replaceText("$@*&%^@**&##$*"));


        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.logintextview));

        textView.check(matches(withText("You shall not pass!")));
    }

    @Test
    public void testFindMissingNumber() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        appCompatEditText.perform(replaceText("PAssw"));


        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.logintextview));

        textView.check(matches(withText("You shall not pass!")));
    }

    @Test
    public void testPasswordMeetsRequirements() {
        ViewInteraction appCompatEditText = onView(withId(R.id.editText));
        appCompatEditText.perform(replaceText("Pass12#$"));


        ViewInteraction materialButton = onView(withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.logintextview));

        textView.check(matches(withText("Your password is complex enough")));
    }
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
