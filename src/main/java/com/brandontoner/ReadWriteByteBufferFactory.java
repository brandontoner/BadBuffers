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
        ByteBuffer allocate(int length) {
            return ByteBuffer.allocate(length);
        }
    },
    /**
     * Allocates non-direct ByteBuffers with padding before the data.
     */
    NON_DIRECT_PADDING_BEFORE {
        @Override
        ByteBuffer allocate(int length) {
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
        ByteBuffer allocate(int length) {
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
        ByteBuffer allocate(int length) {
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
        ByteBuffer allocate(int length) {
            byte[] array = new byte[length + 10];
            return ByteBuffer.wrap(array, 10, length);
        }
    },
    /**
     * Allocates direct ByteBuffers of the correct size.
     */
    DIRECT_CORRECT_SIZE {
        @Override
        ByteBuffer allocate(int length) {
            return ByteBuffer.allocateDirect(length);
        }
    },
    /**
     * Allocates direct ByteBuffers with padding before the data.
     */
    DIRECT_PADDING_BEFORE {
        @Override
        ByteBuffer allocate(int length) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(length + 10);
            buffer.position(10);
            return buffer;
        }
    },
    /**
     * Allocates direct ByteBuffers with padding after the data.
     */
    DIRECT_PADDING_AFTER {
        @Override
        ByteBuffer allocate(int length) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(length + 10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    },
    /**
     * Allocates direct ByteBuffers with padding before and after the data.
     */
    DIRECT_PADDING_BOTH {
        @Override
        ByteBuffer allocate(int length) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(length + 20);
            buffer.position(10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    };

    @Override
    public ByteBuffer copyOf(byte[] array, int offset, int length) {
        ByteBuffer buffer = allocate(length);
        buffer.duplicate().put(array, offset, length);
        return buffer;
    }

    /**
     * Allocates a ByteBuffer with the given capacity.
     *
     * @param length capacity
     * @return ByteBuffer with the given capacity
     */
    abstract ByteBuffer allocate(int length);
}
