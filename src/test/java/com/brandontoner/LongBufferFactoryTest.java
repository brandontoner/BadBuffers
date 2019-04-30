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
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

class LongBufferFactoryTest extends AbstractFactoryTest<long[], LongBuffer, LongBufferFactory> {
    @Override
    LongBuffer wrap(final long[] array) {
        return LongBuffer.wrap(array);
    }

    @Override
    LongBuffer wrap(final long[] array, final int offset, final int length) {
        return LongBuffer.wrap(array, offset, length);
    }

    @Override
    long[] randomArray(final int size) {
        return ThreadLocalRandom.current().longs(size).toArray();
    }

    @Override
    Collection<LongBufferFactory> allFactories() {
        return LongBufferFactory.allFactories();
    }

    @Override
    Collection<LongBufferFactory> readOnlyFactories() {
        return LongBufferFactory.readOnlyFactories();
    }

    @Override
    Collection<LongBufferFactory> readWriteFactories() {
        return LongBufferFactory.readWriteFactories();
    }
}
