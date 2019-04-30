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
import java.nio.DoubleBuffer;

/**
 * Enumeration providing read-write implementations of {@link DoubleBufferFactory}.
 */
enum ReadWriteDoubleBufferFactory implements DoubleBufferFactory {
    /**
     * Allocates non-direct DoubleBuffers of the correct size.
     */
    NON_DIRECT_CORRECT_SIZE {
        @Override
        DoubleBuffer allocate(final int length) {
            return DoubleBuffer.allocate(length);
        }
    },
    /**
     * Allocates non-direct DoubleBuffers with padding before the data.
     */
    NON_DIRECT_PADDING_BEFORE {
        @Override
        DoubleBuffer allocate(final int length) {
            DoubleBuffer buffer = DoubleBuffer.allocate(length + 10);
            buffer.position(10);
            return buffer;
        }
    },
    /**
     * Allocates non-direct DoubleBuffers with padding after the data.
     */
    NON_DIRECT_PADDING_AFTER {
        @Override
        DoubleBuffer allocate(final int length) {
            DoubleBuffer buffer = DoubleBuffer.allocate(length + 10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    },
    /**
     * Allocates non-direct DoubleBuffers with padding before and after the data.
     */
    NON_DIRECT_PADDING_BOTH {
        @Override
        DoubleBuffer allocate(final int length) {
            DoubleBuffer buffer = DoubleBuffer.allocate(length + 20);
            buffer.position(10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    },
    /**
     * Allocates non-direct DoubleBuffers with correct size, but non-zero array offset.
     */
    NON_DIRECT_NON_ZERO_ARRAY_OFFSET {
        @Override
        DoubleBuffer allocate(final int length) {
            double[] array = new double[length + 10];
            return DoubleBuffer.wrap(array, 10, length);
        }
    },
    /**
     * Allocates direct DoubleBuffers of the correct size.
     */
    DIRECT_CORRECT_SIZE {
        @Override
        DoubleBuffer allocate(final int length) {
            return allocateDirect(length);
        }
    },
    /**
     * Allocates direct DoubleBuffers with padding before the data.
     */
    DIRECT_PADDING_BEFORE {
        @Override
        DoubleBuffer allocate(final int length) {
            DoubleBuffer buffer = allocateDirect(length + 10);
            buffer.position(10);
            return buffer;
        }
    },
    /**
     * Allocates direct DoubleBuffers with padding after the data.
     */
    DIRECT_PADDING_AFTER {
        @Override
        DoubleBuffer allocate(final int length) {
            DoubleBuffer buffer = allocateDirect(length + 10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    },
    /**
     * Allocates direct DoubleBuffers with padding before and after the data.
     */
    DIRECT_PADDING_BOTH {
        @Override
        DoubleBuffer allocate(final int length) {
            DoubleBuffer buffer = allocateDirect(length + 20);
            buffer.position(10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    };

    /**
     * Allocates a direct DoubleBuffer with the given capacity.
     *
     * @param length capacity
     * @return direct DoubleBuffer.
     */
    private static DoubleBuffer allocateDirect(final int length) {
        return ByteBuffer.allocateDirect(length * Double.BYTES).asDoubleBuffer();
    }

    @Override
    public DoubleBuffer copyOf(final double[] array, final int offset, final int length) {
        DoubleBuffer buffer = allocate(length);
        buffer.duplicate().put(array, offset, length);
        return buffer;
    }

    /**
     * Allocates a DoubleBuffer with the given capacity.
     *
     * @param length capacity
     * @return DoubleBuffer with the given capacity
     */
    abstract DoubleBuffer allocate(int length);
}
