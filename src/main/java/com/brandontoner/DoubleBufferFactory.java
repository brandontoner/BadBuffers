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

import java.nio.DoubleBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Factory for DoubleBuffers.
 */
public interface DoubleBufferFactory extends BufferFactory<double[], DoubleBuffer> {
    /**
     * @return Collection of {@link DoubleBufferFactory}s which creates non-readonly buffers
     */
    static Collection<DoubleBufferFactory> readWriteFactories() {
        return Arrays.asList(ReadWriteDoubleBufferFactory.values());
    }

    /**
     * @return Collection of {@link DoubleBufferFactory}s which create readonly buffers
     */
    static Collection<DoubleBufferFactory> readOnlyFactories() {
        return readWriteFactories().stream().map(ReadOnlyDoubleBufferFactory::new).collect(Collectors.toList());
    }

    /**
     * @return Collection of {@link DoubleBufferFactory}s
     */
    static Collection<DoubleBufferFactory> allFactories() {
        return Stream.concat(readWriteFactories().stream(), readOnlyFactories().stream()).collect(Collectors.toList());
    }

    /**
     * Allocates a DoubleBuffer with the given size, i.e. {@link DoubleBuffer#remaining()} will return {@code length}.
     *
     * @param length the number of elements that should be remaining in the buffer
     * @return buffer with specified number of elements
     */
    @Override
    DoubleBuffer allocate(int length);

    /**
     * Creates a [@link DoubleBuffer} with the given contents. The resulting buffer will be equal to
     * {@code DoubleBuffer.wrap(array)}. The buffer will be independent of the provided array.
     *
     * @param array array to copy
     * @return DoubleBuffer with given contents
     */
    @Override
    default DoubleBuffer copyOf(final double[] array) {
        return copyOf(array, 0, array.length);
    }

    /**
     * Creates a [@link DoubleBuffer} with the given contents. The resulting buffer will be equal to
     * {@code DoubleBuffer.wrap(array, offset, length)}. The buffer will be independent of the provided array.
     *
     * @param array array to copy
     * @return DoubleBuffer with given contents
     */
    @Override
    default DoubleBuffer copyOf(final double[] array, final int offset, final int length) {
        DoubleBuffer buffer = allocate(length);
        buffer.duplicate().put(array, offset, length);
        return buffer;
    }

    /**
     * Creates a DoubleBuffer with the given contents. The resulting buffer will be equal to {@code buffer}.
     * The position, limit, mark, and contents of {@code buffer} will be unchanged.
     *
     * @param buffer buffer to copy
     * @return Buffer with given contents
     */
    @Override
    default DoubleBuffer copyOf(final DoubleBuffer buffer) {
        DoubleBuffer output = allocate(buffer.remaining());
        output.duplicate().put(buffer.duplicate());
        return output;
    }
}
