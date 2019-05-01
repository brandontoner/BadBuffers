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


import java.nio.Buffer;

/**
 * Base interface for all Buffer Factories, defines required methods for all factories.
 *
 * @param <A> Array type
 * @param <B> Buffer type
 */
interface BufferFactory<A, B extends Buffer> {
    /**
     * Allocates a Buffer with the given size, i.e. {@link Buffer#remaining()} will return {@code length}.
     *
     * @param length the number of elements that should be remaining in the buffer
     * @return buffer with specified number of elements
     */
    B allocate(int length);

    /**
     * Creates a Buffer with the given contents. The resulting buffer will be equal to
     * {@code Buffer.wrap(array)}. The buffer will be independent of the provided array.
     *
     * @param array array to copy
     * @return Buffer with given contents
     */
    B copyOf(A array);

    /**
     * Creates a Buffer with the given contents. The resulting buffer will be equal to
     * {@code Buffer.wrap(array, offset, length)}. The buffer will be independent of the provided array.
     *
     * @param array array to copy
     * @return Buffer with given contents
     */
    B copyOf(A array, int offset, int length);

    /**
     * Creates a Buffer with the given contents. The resulting buffer will be equal to {@code buffer}.
     * The position, limit, mark, and contents of {@code buffer} will be unchanged.
     *
     * @param buffer buffer to copy
     * @return Buffer with given contents
     */
    B copyOf(B buffer);
}
