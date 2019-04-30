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
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

class IntBufferFactoryTest extends AbstractFactoryTest<int[], IntBuffer, IntBufferFactory> {
    @Override
    IntBuffer wrap(final int[] array) {
        return IntBuffer.wrap(array);
    }

    @Override
    IntBuffer wrap(final int[] array, final int offset, final int length) {
        return IntBuffer.wrap(array, offset, length);
    }

    @Override
    int[] randomArray(final int size) {
        return ThreadLocalRandom.current().ints(size).toArray();
    }

    @Override
    Collection<IntBufferFactory> allFactories() {
        return IntBufferFactory.allFactories();
    }

    @Override
    Collection<IntBufferFactory> readOnlyFactories() {
        return IntBufferFactory.readOnlyFactories();
    }

    @Override
    Collection<IntBufferFactory> readWriteFactories() {
        return IntBufferFactory.readWriteFactories();
    }
}
