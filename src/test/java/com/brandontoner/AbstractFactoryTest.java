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

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.Buffer;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(Lifecycle.PER_CLASS)
abstract class AbstractFactoryTest<A, B extends Buffer, T extends BufferFactory<A, B>> {
    private static final int TEST_ARRAY_SIZE = 128;

    @ParameterizedTest
    @MethodSource("allFactories")
    void copyOf_empty(final T factory) {
        A array = randomArray(0);
        assertEquals(wrap(array), factory.copyOf(array));
    }

    @ParameterizedTest
    @MethodSource("allFactories")
    void copyOf_nonEmpty(final T factory) {
        A array = randomArray(TEST_ARRAY_SIZE);
        assertEquals(wrap(array), factory.copyOf(array));
    }

    @ParameterizedTest
    @MethodSource("allFactories")
    void copyOf_subsection(final T factory) {
        A data = randomArray(TEST_ARRAY_SIZE);
        int offset = 1;
        int length = TEST_ARRAY_SIZE - 2;
        assertEquals(wrap(data, offset, length), factory.copyOf(data, offset, length));
    }

    @ParameterizedTest
    @MethodSource("readOnlyFactories")
    void copyOf_isReadOnly(final T factory) {
        assertTrue(factory.copyOf(randomArray(0)).isReadOnly());
    }

    @ParameterizedTest
    @MethodSource("readWriteFactories")
    void copyOf_isReadWrite(final T factory) {
        assertFalse(factory.copyOf(randomArray(0)).isReadOnly());
    }

    /**
     * Creates a Buffer wrapping the given array.
     *
     * @param array array to wrap
     * @return Buffer wrapping array
     */
    abstract B wrap(A array);

    /**
     * Creates a Buffer wrapping the given array.
     *
     * @param array  The array that will back the new buffer
     * @param offset The offset of the subarray to be used
     * @param length The length of the subarray to be used
     * @return Buffer wrapping array
     */
    abstract B wrap(A array, int offset, int length);

    /**
     * Creates a random array of a given size.
     *
     * @param size size of the array
     * @return random array
     */
    abstract A randomArray(int size);

    /**
     * @return collection of all BufferFactories
     */
    abstract Collection<T> allFactories();

    /**
     * @return collection of read-only BufferFactories
     */
    abstract Collection<T> readOnlyFactories();

    /**
     * @return collection of read-write BufferFactories
     */
    abstract Collection<T> readWriteFactories();
}
