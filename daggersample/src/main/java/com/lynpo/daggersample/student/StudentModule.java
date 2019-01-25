package com.lynpo.daggersample.student;

import android.content.Context;
import android.content.SharedPreferences;

import com.lynpo.daggersample.model.Student;

import dagger.Module;
import dagger.Provides;

/**
 * SampleModule
 * *
 * Create by fujw on 2019/1/21.
 */
@Module
public class StudentModule {

    @Provides
    static String provideName() {
        return "David";
    }

    @Provides
    static Student provideStudent(String name) {
        return new Student(name);
    }

    @Provides
    static SharedPreferences provideSp(StudentActivity activity) {
        return activity.getSharedPreferences("def", Context.MODE_PRIVATE);
    }
}
