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

import java.nio.LongBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Factory for LongBuffers.
 */
public interface LongBufferFactory extends BufferFactory<long[], LongBuffer> {
    /**
     * Gets a Collection of {@link LongBufferFactory}s which create non-readonly buffers.
     *
     * @return Collection of {@link LongBufferFactory}s which create non-readonly buffers
     */
    static Collection<LongBufferFactory> readWriteFactories() {
        return Arrays.asList(ReadWriteLongBufferFactory.values());
    }

    /**
     * Gets a Collection of {@link LongBufferFactory}s which create readonly buffers.
     *
     * @return Collection of {@link LongBufferFactory}s which create readonly buffers
     */
    static Collection<LongBufferFactory> readOnlyFactories() {
        return readWriteFactories().stream().map(ReadOnlyLongBufferFactory::new).collect(Collectors.toList());
    }

    /**
     * Gets a Collection of {@link LongBufferFactory}s.
     *
     * @return Collection of {@link LongBufferFactory}s
     */
    static Collection<LongBufferFactory> allFactories() {
        return Stream.concat(readWriteFactories().stream(), readOnlyFactories().stream()).collect(Collectors.toList());
    }

    /**
     * Allocates a LongBuffer with the given size, i.e. {@link LongBuffer#remaining()} will return {@code length}.
     *
     * @param length the number of elements that should be remaining in the buffer
     * @return buffer with specified number of elements
     */
    @Override
    LongBuffer allocate(int length);

    /**
     * Creates a {@link LongBuffer} with the given contents. The resulting buffer will be equal to
     * {@code LongBuffer.wrap(array)}. The buffer will be independent of the provided array.
     *
     * @param array array to copy
     * @return LongBuffer with given contents
     */
    @Override
    default LongBuffer copyOf(final long[] array) {
        return copyOf(array, 0, array.length);
    }

    /**
     * Creates a {@link LongBuffer} with the given contents. The resulting buffer will be equal to
     * {@code LongBuffer.wrap(array, offset, length)}. The buffer will be independent of the provided array.
     *
     * @param array array to copy
     * @return LongBuffer with given contents
     */
    @Override
    default LongBuffer copyOf(final long[] array, final int offset, final int length) {
        LongBuffer buffer = allocate(length);
        buffer.duplicate().put(array, offset, length);
        return buffer;
    }

    /**
     * Creates a LongBuffer with the given contents. The resulting buffer will be equal to {@code buffer}.
     * The position, limit, mark, and contents of {@code buffer} will be unchanged.
     *
     * @param buffer buffer to copy
     * @return Buffer with given contents
     */
    @Override
    default LongBuffer copyOf(final LongBuffer buffer) {
        LongBuffer output = allocate(buffer.remaining());
        output.duplicate().put(buffer.duplicate());
        return output;
    }
}
