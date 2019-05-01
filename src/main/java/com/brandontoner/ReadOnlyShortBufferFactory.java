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

import java.nio.ShortBuffer;

/**
 * Provides read-only implementation of {@link ShortBufferFactory}.
 */
class ReadOnlyShortBufferFactory implements ShortBufferFactory {
    /**
     * Delegate factory.
     */
    private final ShortBufferFactory factory;

    /**
     * Constructor.
     *
     * @param factory delegate factory
     */
    ReadOnlyShortBufferFactory(final ShortBufferFactory factory) {
        this.factory = factory;
    }

    @Override
    public ShortBuffer allocate(final int length) {
        return factory.allocate(length).asReadOnlyBuffer();
    }

    @Override
    public ShortBuffer copyOf(final short[] array, final int offset, final int length) {
        return factory.copyOf(array, offset, length).asReadOnlyBuffer();
    }

    @Override
    public ShortBuffer copyOf(final ShortBuffer buffer) {
        return factory.copyOf(buffer).asReadOnlyBuffer();
    }

    @Override
    public String toString() {
        return "READ_ONLY_" + factory;
    }
}
