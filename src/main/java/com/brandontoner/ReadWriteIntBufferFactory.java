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
import java.nio.IntBuffer;

/**
 * Enumeration providing read-write implementations of {@link IntBufferFactory}.
 */
enum ReadWriteIntBufferFactory implements IntBufferFactory {
    /**
     * Allocates non-direct IntBuffers of the correct size.
     */
    NON_DIRECT_CORRECT_SIZE {
        @Override
        public IntBuffer allocate(final int length) {
            return IntBuffer.allocate(length);
        }
    },
    /**
     * Allocates non-direct IntBuffers with padding before the data.
     */
    NON_DIRECT_PADDING_BEFORE {
        @Override
        public IntBuffer allocate(final int length) {
            IntBuffer buffer = IntBuffer.allocate(length + 10);
            buffer.position(10);
            return buffer;
        }
    },
    /**
     * Allocates non-direct IntBuffers with padding after the data.
     */
    NON_DIRECT_PADDING_AFTER {
        @Override
        public IntBuffer allocate(final int length) {
            IntBuffer buffer = IntBuffer.allocate(length + 10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    },
    /**
     * Allocates non-direct IntBuffers with padding before and after the data.
     */
    NON_DIRECT_PADDING_BOTH {
        @Override
        public IntBuffer allocate(final int length) {
            IntBuffer buffer = IntBuffer.allocate(length + 20);
            buffer.position(10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    },
    /**
     * Allocates non-direct IntBuffers with correct size, but non-zero array offset.
     */
    NON_DIRECT_NON_ZERO_ARRAY_OFFSET {
        @Override
        public IntBuffer allocate(final int length) {
            int[] array = new int[length + 10];
            return IntBuffer.wrap(array, 10, length);
        }
    },
    /**
     * Allocates direct IntBuffers of the correct size.
     */
    DIRECT_CORRECT_SIZE {
        @Override
        public IntBuffer allocate(final int length) {
            return allocateDirect(length);
        }
    },
    /**
     * Allocates direct IntBuffers with padding before the data.
     */
    DIRECT_PADDING_BEFORE {
        @Override
        public IntBuffer allocate(final int length) {
            IntBuffer buffer = allocateDirect(length + 10);
            buffer.position(10);
            return buffer;
        }
    },
    /**
     * Allocates direct IntBuffers with padding after the data.
     */
    DIRECT_PADDING_AFTER {
        @Override
        public IntBuffer allocate(final int length) {
            IntBuffer buffer = allocateDirect(length + 10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    },
    /**
     * Allocates direct IntBuffers with padding before and after the data.
     */
    DIRECT_PADDING_BOTH {
        @Override
        public IntBuffer allocate(final int length) {
            IntBuffer buffer = allocateDirect(length + 20);
            buffer.position(10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    };

    /**
     * Allocates a direct IntBuffer with the given capacity.
     *
     * @param length capacity
     * @return direct IntBuffer.
     */
    static IntBuffer allocateDirect(final int length) {
        return ByteBuffer.allocateDirect(length * Integer.BYTES).asIntBuffer();
    }
}
