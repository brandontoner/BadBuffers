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
import java.nio.FloatBuffer;

/**
 * Enumeration providing read-write implementations of {@link FloatBufferFactory}.
 */
enum ReadWriteFloatBufferFactory implements FloatBufferFactory {
    /**
     * Allocates non-direct FloatBuffers of the correct size.
     */
    NON_DIRECT_CORRECT_SIZE {
        @Override
        FloatBuffer allocate(final int length) {
            return FloatBuffer.allocate(length);
        }
    },
    /**
     * Allocates non-direct FloatBuffers with padding before the data.
     */
    NON_DIRECT_PADDING_BEFORE {
        @Override
        FloatBuffer allocate(final int length) {
            FloatBuffer buffer = FloatBuffer.allocate(length + 10);
            buffer.position(10);
            return buffer;
        }
    },
    /**
     * Allocates non-direct FloatBuffers with padding after the data.
     */
    NON_DIRECT_PADDING_AFTER {
        @Override
        FloatBuffer allocate(final int length) {
            FloatBuffer buffer = FloatBuffer.allocate(length + 10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    },
    /**
     * Allocates non-direct FloatBuffers with padding before and after the data.
     */
    NON_DIRECT_PADDING_BOTH {
        @Override
        FloatBuffer allocate(final int length) {
            FloatBuffer buffer = FloatBuffer.allocate(length + 20);
            buffer.position(10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    },
    /**
     * Allocates non-direct FloatBuffers with correct size, but non-zero array offset.
     */
    NON_DIRECT_NON_ZERO_ARRAY_OFFSET {
        @Override
        FloatBuffer allocate(final int length) {
            float[] array = new float[length + 10];
            return FloatBuffer.wrap(array, 10, length);
        }
    },
    /**
     * Allocates direct FloatBuffers of the correct size.
     */
    DIRECT_CORRECT_SIZE {
        @Override
        FloatBuffer allocate(final int length) {
            return allocateDirect(length);
        }
    },
    /**
     * Allocates direct FloatBuffers with padding before the data.
     */
    DIRECT_PADDING_BEFORE {
        @Override
        FloatBuffer allocate(final int length) {
            FloatBuffer buffer = allocateDirect(length + 10);
            buffer.position(10);
            return buffer;
        }
    },
    /**
     * Allocates direct FloatBuffers with padding after the data.
     */
    DIRECT_PADDING_AFTER {
        @Override
        FloatBuffer allocate(final int length) {
            FloatBuffer buffer = allocateDirect(length + 10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    },
    /**
     * Allocates direct FloatBuffers with padding before and after the data.
     */
    DIRECT_PADDING_BOTH {
        @Override
        FloatBuffer allocate(final int length) {
            FloatBuffer buffer = allocateDirect(length + 20);
            buffer.position(10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    };

    /**
     * Allocates a direct FloatBuffer with the given capacity.
     *
     * @param length capacity
     * @return direct FloatBuffer.
     */
    private static FloatBuffer allocateDirect(final int length) {
        return ByteBuffer.allocateDirect(length * Float.BYTES).asFloatBuffer();
    }

    @Override
    public FloatBuffer copyOf(final float[] array, final int offset, final int length) {
        FloatBuffer buffer = allocate(length);
        buffer.duplicate().put(array, offset, length);
        return buffer;
    }

    /**
     * Allocates a FloatBuffer with the given capacity.
     *
     * @param length capacity
     * @return FloatBuffer with the given capacity
     */
    abstract FloatBuffer allocate(int length);
}
