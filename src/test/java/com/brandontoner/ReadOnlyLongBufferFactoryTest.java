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

import java.nio.LongBuffer;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReadOnlyLongBufferFactoryTest {
    private static final String FACTORY_METHOD = "com.brandontoner.LongBufferFactory#readOnlyFactories";

    @ParameterizedTest
    @MethodSource(FACTORY_METHOD)
    void copyOf_empty(final LongBufferFactory factory) {
        assertEquals(LongBuffer.allocate(0), factory.copyOf(new long[0]));
    }

    @ParameterizedTest
    @MethodSource(FACTORY_METHOD)
    void copyOf_nonEmpty(final LongBufferFactory factory) {
        long[] data = randomArray();
        assertEquals(LongBuffer.wrap(data), factory.copyOf(data));
    }

    @ParameterizedTest
    @MethodSource(FACTORY_METHOD)
    void copyOf_subsection(final LongBufferFactory factory) {
        long[] data = randomArray();
        assertEquals(LongBuffer.wrap(data, 1, data.length - 2), factory.copyOf(data, 1, data.length - 2));
    }

    @ParameterizedTest
    @MethodSource(FACTORY_METHOD)
    void copyOf_isReadOnly(final LongBufferFactory factory) {
        assertTrue(factory.copyOf(new long[0]).isReadOnly());
    }

    private static long[] randomArray() {
        return ThreadLocalRandom.current().longs(128).toArray();
    }
}
