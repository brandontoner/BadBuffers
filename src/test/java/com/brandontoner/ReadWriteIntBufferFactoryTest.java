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

import java.nio.IntBuffer;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ReadWriteIntBufferFactoryTest {
    private static final String FACTORY_METHOD = "com.brandontoner.IntBufferFactory#readWriteFactories";

    @ParameterizedTest
    @MethodSource(FACTORY_METHOD)
    void copyOf_empty(final IntBufferFactory factory) {
        assertEquals(IntBuffer.allocate(0), factory.copyOf(new int[0]));
    }

    @ParameterizedTest
    @MethodSource(FACTORY_METHOD)
    void copyOf_nonEmpty(final IntBufferFactory factory) {
        int[] data = randomArray();
        assertEquals(IntBuffer.wrap(data), factory.copyOf(data));
    }

    @ParameterizedTest
    @MethodSource(FACTORY_METHOD)
    void copyOf_subsection(final IntBufferFactory factory) {
        int[] data = randomArray();
        assertEquals(IntBuffer.wrap(data, 1, data.length - 2), factory.copyOf(data, 1, data.length - 2));
    }

    @ParameterizedTest
    @MethodSource(FACTORY_METHOD)
    void copyOf_isReadWrite(final IntBufferFactory factory) {
        assertFalse(factory.copyOf(new int[0]).isReadOnly());
    }

    private static int[] randomArray() {
        return ThreadLocalRandom.current().ints(128).toArray();
    }
}
