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

import java.nio.CharBuffer;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

class CharBufferFactoryTest extends AbstractFactoryTest<char[], CharBuffer, CharBufferFactory> {
    @Override
    CharBuffer wrap(final char[] array) {
        return CharBuffer.wrap(array);
    }

    @Override
    CharBuffer wrap(final char[] array, final int offset, final int length) {
        return CharBuffer.wrap(array, offset, length);
    }

    @Override
    char[] randomArray(final int size) {
        char[] output = new char[size];
        for (int i = 0; i < output.length; i++) {
            output[i] = (char) ThreadLocalRandom.current().nextInt();
        }
        return output;
    }

    @Override
    Collection<CharBufferFactory> allFactories() {
        return CharBufferFactory.allFactories();
    }

    @Override
    Collection<CharBufferFactory> readOnlyFactories() {
        return CharBufferFactory.readOnlyFactories();
    }

    @Override
    Collection<CharBufferFactory> readWriteFactories() {
        return CharBufferFactory.readWriteFactories();
    }
}
