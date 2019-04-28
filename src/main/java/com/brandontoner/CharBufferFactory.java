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

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Factory for CharBuffers.
 */
public interface CharBufferFactory {
    /**
     * @return Collection of {@link CharBufferFactory}s which create non-readonly buffers
     */
    static Collection<CharBufferFactory> readWriteFactories() {
        return Arrays.asList(ReadWriteCharBufferFactory.values());
    }

    /**
     * @return Collection of {@link CharBufferFactory}s which create readonly buffers
     */
    static Collection<CharBufferFactory> readOnlyFactories() {
        return readWriteFactories().stream()
                                   .map(factory -> new CharBufferFactory() {
                                       @Override
                                       public CharBuffer copyOf(char[] array, int offset, int length) {
                                           return factory.copyOf(array, offset, length).asReadOnlyBuffer();
                                       }

                                       @Override
                                       public String toString() {
                                           return "READ_ONLY_" + factory.toString();
                                       }
                                   })
                                   .collect(Collectors.toList());
    }

    /**
     * @return Collection of {@link CharBufferFactory}s
     */
    static Collection<CharBufferFactory> allFactories() {
        return Stream.concat(readWriteFactories().stream(), readOnlyFactories().stream()).collect(Collectors.toList());
    }

    /**
     * Creates a [@link CharBuffer} with the given contents. The resulting buffer will be equal to
     * {@code CharBuffer.wrap(array)}. The buffer will be independent of the provided array.
     *
     * @param array array to copy
     * @return CharBuffer with given contents
     */
    default CharBuffer copyOf(final char[] array) {
        return copyOf(array, 0, array.length);
    }

    /**
     * Creates a [@link CharBuffer} with the given contents. The resulting buffer will be equal to
     * {@code CharBuffer.wrap(array, offset, length)}. The buffer will be independent of the provided array.
     *
     * @param array array to copy
     * @return CharBuffer with given contents
     */
    CharBuffer copyOf(char[] array, int offset, int length);
}
