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

import java.nio.CharBuffer;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReadOnlyCharBufferFactoryTest {
    private static final String FACTORY_METHOD = "com.brandontoner.CharBufferFactory#readOnlyFactories";

    @ParameterizedTest
    @MethodSource(FACTORY_METHOD)
    void copyOf_empty(final CharBufferFactory factory) {
        assertEquals(CharBuffer.allocate(0), factory.copyOf(new char[0]));
    }

    @ParameterizedTest
    @MethodSource(FACTORY_METHOD)
    void copyOf_nonEmpty(final CharBufferFactory factory) {
        char[] data = randomArray();
        assertEquals(CharBuffer.wrap(data), factory.copyOf(data));
    }

    @ParameterizedTest
    @MethodSource(FACTORY_METHOD)
    void copyOf_subsection(final CharBufferFactory factory) {
        char[] data = randomArray();
        assertEquals(CharBuffer.wrap(data, 1, data.length - 2), factory.copyOf(data, 1, data.length - 2));
    }

    @ParameterizedTest
    @MethodSource(FACTORY_METHOD)
    void copyOf_isReadOnly(final CharBufferFactory factory) {
        assertTrue(factory.copyOf(new char[0]).isReadOnly());
    }

    private static char[] randomArray() {
        return UUID.randomUUID().toString().toCharArray();
    }
}
