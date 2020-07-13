/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class SleepDatabaseTest {

    private lateinit var _db: SleepDatabase
    val db: SleepDatabase
        get() = _db

    private lateinit var sleepDao: SleepDatabaseDao

    // For LiveData Testing use observer
    // Reference
    // https://androidx.tech/artifacts/arch.core/core-testing/
    // https://developer.android.com/jetpack/androidx/releases/arch
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
//        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        _db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), SleepDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        sleepDao = db.sleepDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        val night = SleepNight()
        sleepDao.insert(night)
        val tonight = sleepDao.getTonight()
        assertEquals(tonight?.sleepQuality, -1)
    }

    @Test
    @Throws(Exception::class)
    fun getNightById() {
        val night = SleepNight(9999)
        night.sleepQuality = 1111
        sleepDao.insert(night)
        val tonight = sleepDao.get(9999)
        assertEquals(tonight?.sleepQuality, 1111)
    }

    @Test
    @Throws(Exception::class)
    fun getAllNights() {
        val createData = {
            val night = SleepNight()
            sleepDao.insert(night)
        }

//        createData()
//        createData()
//        createData()


        val night = SleepNight()
        sleepDao.insert(night)


        val allNights = sleepDao.getAllNights()

//        Log.wtf("SleepDatabaseTest", "getAllNights => ${allNights.size}")

        assertThat(allNights.getOrAwaitValue().size, `is`(1))
//        assertEquals(allNights.size, 1)
    }
}