package com.dorbit.web_crawler;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BaseTest {

    protected static <T> T random(Class<T> randomClass) {
        EasyRandom easyRandom = new EasyRandom();
        return easyRandom.nextObject(randomClass);
    }
}

