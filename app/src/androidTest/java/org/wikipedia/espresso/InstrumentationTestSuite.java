package org.wikipedia.espresso;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.R;
import org.wikipedia.espresso.feed.ExploreFeedTest;
import org.wikipedia.espresso.onboarding.OnBoardingTest;
import org.wikipedia.espresso.page.PageActivityTest;
import org.wikipedia.espresso.search.SearchTest;
import org.wikipedia.espresso.util.CompareTools;
import org.wikipedia.main.MainActivity;

import static org.wikipedia.espresso.Constants.SCREENSHOT_COMPARE_PERCENT_TOLERANCE;
import static org.wikipedia.espresso.util.ViewTools.pressBack;
import static org.wikipedia.espresso.util.ViewTools.viewIsDisplayed;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class InstrumentationTestSuite {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
    }

    @Test
    public void instrumentationTests() throws Exception {

        // run OnBoarding on every tests
        OnBoardingTest.runOnBoarding();

        while (!viewIsDisplayed(R.id.fragment_feed_feed)) {
            // press back until back to the feed page
            pressBack();
        }
        ExploreFeedTest.testExploreFeed(getActivity());
        SearchTest.searchKeywordAndGo("Barack Obama");

        PageActivityTest.testArticleLoad(getActivity());

        while (!viewIsDisplayed(R.id.fragment_feed_feed)) {
            // press back until back to the feed page
            pressBack();
        }

        assertScreenshotWithinTolerance("FeaturedArticle");
        assertScreenshotWithinTolerance("FeaturedArticle_Landscape");
        assertScreenshotWithinTolerance("FeaturedImage");
        assertScreenshotWithinTolerance("FeaturedImage_Landscape");
        assertScreenshotWithinTolerance("MainPage");
        assertScreenshotWithinTolerance("MainPage_Landscape");
        assertScreenshotWithinTolerance("News");
        assertScreenshotWithinTolerance("News_Landscape");
        assertScreenshotWithinTolerance("OnThisDay");
        assertScreenshotWithinTolerance("OnThisDay_Landscape");
        assertScreenshotWithinTolerance("Randomizer");
        assertScreenshotWithinTolerance("Randomizer_Landscape");
        assertScreenshotWithinTolerance("Trending");
        assertScreenshotWithinTolerance("Trending_Landscape");
        assertScreenshotWithinTolerance("SearchSuggestionPage");
        assertScreenshotWithinTolerance("SearchPage");
        assertScreenshotWithinTolerance("Barack");
    }

    private void assertScreenshotWithinTolerance(String screenshotName) throws Exception {
        Assert.assertTrue("Screenshot " + screenshotName + " difference above tolerance.",
                CompareTools.compareScreenshotAgainstReference(screenshotName) > SCREENSHOT_COMPARE_PERCENT_TOLERANCE);
    }

    private Activity getActivity() {
        return mActivityTestRule.getActivity();
    }
}