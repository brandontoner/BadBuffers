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
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

class ShortBufferFactoryTest extends AbstractFactoryTest<short[], ShortBuffer, ShortBufferFactory> {
    @Override
    ShortBuffer wrap(final short[] array) {
        return ShortBuffer.wrap(array);
    }

    @Override
    ShortBuffer wrap(final short[] array, final int offset, final int length) {
        return ShortBuffer.wrap(array, offset, length);
    }

    @Override
    short[] randomArray(final int size) {
        short[] output = new short[size];
        for (int i = 0; i < output.length; i++) {
            output[i] = (short) ThreadLocalRandom.current().nextInt();
        }
        return output;
    }

    @Override
    Collection<ShortBufferFactory> allFactories() {
        return ShortBufferFactory.allFactories();
    }

    @Override
    Collection<ShortBufferFactory> readOnlyFactories() {
        return ShortBufferFactory.readOnlyFactories();
    }

    @Override
    Collection<ShortBufferFactory> readWriteFactories() {
        return ShortBufferFactory.readWriteFactories();
    }
}
