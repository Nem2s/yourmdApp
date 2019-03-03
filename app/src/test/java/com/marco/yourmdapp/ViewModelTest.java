package com.marco.yourmdapp;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.marco.yourmdapp.testHelper.TestLifecycle;
import com.marco.yourmdapp.viewmodel.MainActivityViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.concurrent.CountDownLatch;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;


@RunWith(JUnit4.class)
public class ViewModelTest {

    MainActivityViewModel viewModel;

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Before
    public void init() {
        viewModel = new MainActivityViewModel();
    }

    @Test
    public void testSearchResult() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        TestLifecycle testLifecycle = TestLifecycle.initialized();
        viewModel.OnVenuesChanged().observe(testLifecycle,venues -> {
            assertEquals(17, venues.size());
            latch.countDown();
        });
        viewModel.searchVenues("Hello");
        testLifecycle.resume();
        latch.await();
    }

    @Test
    public void testRequestNotValid() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        TestLifecycle testLifecycle = TestLifecycle.initialized();
        viewModel.OnErrorOccured().observe(testLifecycle, error -> {
            if (latch.getCount() == 2) {
                assertNull(error);
            }
            else if (latch.getCount() == 1) {
                assertEquals("No venues found", error);
            }
            latch.countDown();
        });
        viewModel.searchVenues("OWKEOWKKO ODKO DKOWK OK");
        testLifecycle.resume();
        latch.await();
    }



}
