/*
 * Copyright 2019 Brandon Toner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.brandontoner;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.FloatBuffer;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ReadWriteFloatBufferFactoryTest {
    private static final String FACTORY_METHOD = "com.brandontoner.FloatBufferFactory#readWriteFactories";

    @ParameterizedTest
    @MethodSource(FACTORY_METHOD)
    void copyOf_empty(final FloatBufferFactory factory) {
        assertEquals(FloatBuffer.allocate(0), factory.copyOf(new float[0]));
    }

    @ParameterizedTest
    @MethodSource(FACTORY_METHOD)
    void copyOf_nonEmpty(final FloatBufferFactory factory) {
        float[] data = randomArray();
        assertEquals(FloatBuffer.wrap(data), factory.copyOf(data));
    }

    @ParameterizedTest
    @MethodSource(FACTORY_METHOD)
    void copyOf_subsection(final FloatBufferFactory factory) {
        float[] data = randomArray();
        assertEquals(FloatBuffer.wrap(data, 1, data.length - 2), factory.copyOf(data, 1, data.length - 2));
    }

    @ParameterizedTest
    @MethodSource(FACTORY_METHOD)
    void copyOf_isReadWrite(final FloatBufferFactory factory) {
        assertFalse(factory.copyOf(new float[0]).isReadOnly());
    }

    private static float[] randomArray() {
        float[] output = new float[128];
        for (int i = 0; i < output.length; i++) {
            output[i] = ThreadLocalRandom.current().nextFloat();
        }
        return output;
    }
}
