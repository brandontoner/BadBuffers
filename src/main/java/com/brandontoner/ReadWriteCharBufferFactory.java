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
import java.nio.CharBuffer;

/**
 * Enumeration providing read-write implementations of {@link CharBufferFactory}.
 */
enum ReadWriteCharBufferFactory implements CharBufferFactory {
    /**
     * Allocates non-direct CharBuffers of the correct size.
     */
    NON_DIRECT_CORRECT_SIZE {
        @Override
        CharBuffer allocate(int length) {
            return CharBuffer.allocate(length);
        }
    },
    /**
     * Allocates non-direct CharBuffers with padding before the data.
     */
    NON_DIRECT_PADDING_BEFORE {
        @Override
        CharBuffer allocate(int length) {
            CharBuffer buffer = CharBuffer.allocate(length + 10);
            buffer.position(10);
            return buffer;
        }
    },
    /**
     * Allocates non-direct CharBuffers with padding after the data.
     */
    NON_DIRECT_PADDING_AFTER {
        @Override
        CharBuffer allocate(int length) {
            CharBuffer buffer = CharBuffer.allocate(length + 10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    },
    /**
     * Allocates non-direct CharBuffers with padding before and after the data.
     */
    NON_DIRECT_PADDING_BOTH {
        @Override
        CharBuffer allocate(int length) {
            CharBuffer buffer = CharBuffer.allocate(length + 20);
            buffer.position(10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    },
    /**
     * Allocates non-direct CharBuffers with correct size, but non-zero array offset.
     */
    NON_DIRECT_NON_ZERO_ARRAY_OFFSET {
        @Override
        CharBuffer allocate(int length) {
            char[] array = new char[length + 10];
            return CharBuffer.wrap(array, 10, length);
        }
    },
    /**
     * Allocates direct CharBuffers of the correct size.
     */
    DIRECT_CORRECT_SIZE {
        @Override
        CharBuffer allocate(int length) {
            return allocateDirect(length);
        }
    },
    /**
     * Allocates direct CharBuffers with padding before the data.
     */
    DIRECT_PADDING_BEFORE {
        @Override
        CharBuffer allocate(int length) {
            CharBuffer buffer = allocateDirect(length + 10);
            buffer.position(10);
            return buffer;
        }
    },
    /**
     * Allocates direct CharBuffers with padding after the data.
     */
    DIRECT_PADDING_AFTER {
        @Override
        CharBuffer allocate(int length) {
            CharBuffer buffer = allocateDirect(length + 10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    },
    /**
     * Allocates direct CharBuffers with padding before and after the data.
     */
    DIRECT_PADDING_BOTH {
        @Override
        CharBuffer allocate(int length) {
            CharBuffer buffer = allocateDirect(length + 20);
            buffer.position(10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    };

    /**
     * Allocates a direct CharBuffer with the given capacity.
     *
     * @param length capacity
     * @return direct CharBuffer.
     */
    private static CharBuffer allocateDirect(int length) {
        return ByteBuffer.allocateDirect(length * Character.BYTES).asCharBuffer();
    }

    @Override
    public CharBuffer copyOf(char[] array, int offset, int length) {
        CharBuffer buffer = allocate(length);
        buffer.duplicate().put(array, offset, length);
        return buffer;
    }

    /**
     * Allocates a CharBuffer with the given capacity.
     *
     * @param length capacity
     * @return CharBuffer with the given capacity
     */
    abstract CharBuffer allocate(int length);
}