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

import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Factory for IntBuffers.
 */
public interface IntBufferFactory extends BufferFactory<int[], IntBuffer> {
    /**
     * Gets a Collection of {@link IntBufferFactory}s which create non-readonly buffers.
     *
     * @return Collection of {@link IntBufferFactory}s which create non-readonly buffers
     */
    static Collection<IntBufferFactory> readWriteFactories() {
        return Arrays.asList(ReadWriteIntBufferFactory.values());
    }

    /**
     * Gets a Collection of {@link IntBufferFactory}s which create readonly buffers.
     *
     * @return Collection of {@link IntBufferFactory}s which create readonly buffers
     */
    static Collection<IntBufferFactory> readOnlyFactories() {
        return readWriteFactories().stream().map(ReadOnlyIntBufferFactory::new).collect(Collectors.toList());
    }

    /**
     * Gets a Collection of {@link IntBufferFactory}s.
     *
     * @return Collection of {@link IntBufferFactory}s
     */
    static Collection<IntBufferFactory> allFactories() {
        return Stream.concat(readWriteFactories().stream(), readOnlyFactories().stream()).collect(Collectors.toList());
    }

    /**
     * Allocates a IntBuffer with the given size, i.e. {@link IntBuffer#remaining()} will return {@code length}.
     *
     * @param length the number of elements that should be remaining in the buffer
     * @return buffer with specified number of elements
     */
    @Override
    IntBuffer allocate(int length);

    /**
     * Creates a {@link IntBuffer} with the given contents. The resulting buffer will be equal to
     * {@code IntBuffer.wrap(array)}. The buffer will be independent of the provided array.
     *
     * @param array array to copy
     * @return IntBuffer with given contents
     */
    @Override
    default IntBuffer copyOf(final int[] array) {
        return copyOf(array, 0, array.length);
    }

    /**
     * Creates a {@link IntBuffer} with the given contents. The resulting buffer will be equal to
     * {@code IntBuffer.wrap(array, offset, length)}. The buffer will be independent of the provided array.
     *
     * @param array array to copy
     * @return IntBuffer with given contents
     */
    @Override
    default IntBuffer copyOf(final int[] array, final int offset, final int length) {
        IntBuffer buffer = allocate(length);
        buffer.duplicate().put(array, offset, length);
        return buffer;
    }

    /**
     * Creates a IntBuffer with the given contents. The resulting buffer will be equal to {@code buffer}.
     * The position, limit, mark, and contents of {@code buffer} will be unchanged.
     *
     * @param buffer buffer to copy
     * @return Buffer with given contents
     */
    @Override
    default IntBuffer copyOf(final IntBuffer buffer) {
        IntBuffer output = allocate(buffer.remaining());
        output.duplicate().put(buffer.duplicate());
        return output;
    }
}
