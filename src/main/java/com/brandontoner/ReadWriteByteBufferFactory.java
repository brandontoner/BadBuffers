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

import java.nio.ByteBuffer;

/**
 * Enumeration providing read-write implementations of {@link ByteBufferFactory}.
 */
enum ReadWriteByteBufferFactory implements ByteBufferFactory {
    /**
     * Allocates non-direct ByteBuffers of the correct size.
     */
    NON_DIRECT_CORRECT_SIZE {
        @Override
        public ByteBuffer allocate(final int length) {
            return ByteBuffer.allocate(length);
        }
    },
    /**
     * Allocates non-direct ByteBuffers with padding before the data.
     */
    NON_DIRECT_PADDING_BEFORE {
        @Override
        public ByteBuffer allocate(final int length) {
            ByteBuffer buffer = ByteBuffer.allocate(length + 10);
            buffer.position(10);
            return buffer;
        }
    },
    /**
     * Allocates non-direct ByteBuffers with padding after the data.
     */
    NON_DIRECT_PADDING_AFTER {
        @Override
        public ByteBuffer allocate(final int length) {
            ByteBuffer buffer = ByteBuffer.allocate(length + 10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    },
    /**
     * Allocates non-direct ByteBuffers with padding before and after the data.
     */
    NON_DIRECT_PADDING_BOTH {
        @Override
        public ByteBuffer allocate(final int length) {
            ByteBuffer buffer = ByteBuffer.allocate(length + 20);
            buffer.position(10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    },
    /**
     * Allocates non-direct ByteBuffers with correct size, but non-zero array offset.
     */
    NON_DIRECT_NON_ZERO_ARRAY_OFFSET {
        @Override
        public ByteBuffer allocate(final int length) {
            byte[] array = new byte[length + 10];
            ByteBuffer buffer = ByteBuffer.wrap(array, 10, length).slice();
            assert buffer.arrayOffset() != 0 : "Array offset should be non-zero";
            return buffer;
        }
    },
    /**
     * Allocates direct ByteBuffers of the correct size.
     */
    DIRECT_CORRECT_SIZE {
        @Override
        public ByteBuffer allocate(final int length) {
            return allocateDirect(length);
        }
    },
    /**
     * Allocates direct ByteBuffers with padding before the data.
     */
    DIRECT_PADDING_BEFORE {
        @Override
        public ByteBuffer allocate(final int length) {
            ByteBuffer buffer = allocateDirect(length + 10);
            buffer.position(10);
            return buffer;
        }
    },
    /**
     * Allocates direct ByteBuffers with padding after the data.
     */
    DIRECT_PADDING_AFTER {
        @Override
        public ByteBuffer allocate(final int length) {
            ByteBuffer buffer = allocateDirect(length + 10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    },
    /**
     * Allocates direct ByteBuffers with padding before and after the data.
     */
    DIRECT_PADDING_BOTH {
        @Override
        public ByteBuffer allocate(final int length) {
            ByteBuffer buffer = allocateDirect(length + 20);
            buffer.position(10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    };

    /**
     * Allocates a direct ByteBuffer with the given capacity.
     *
     * @param length capacity
     * @return direct ByteBuffer.
     */
    static ByteBuffer allocateDirect(final int length) {
        return ByteBuffer.allocateDirect(length * Byte.BYTES);
    }
}
