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
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

class ByteBufferFactoryTest extends AbstractFactoryTest<byte[], ByteBuffer, ByteBufferFactory> {
    @Override
    ByteBuffer wrap(final byte[] array) {
        return ByteBuffer.wrap(array);
    }

    @Override
    ByteBuffer wrap(final byte[] array, final int offset, final int length) {
        return ByteBuffer.wrap(array, offset, length);
    }

    @Override
    byte[] randomArray(final int size) {
        byte[] data = new byte[size];
        ThreadLocalRandom.current().nextBytes(data);
        return data;
    }

    @Override
    Collection<ByteBufferFactory> allFactories() {
        return ByteBufferFactory.allFactories();
    }

    @Override
    Collection<ByteBufferFactory> readOnlyFactories() {
        return ByteBufferFactory.readOnlyFactories();
    }

    @Override
    Collection<ByteBufferFactory> readWriteFactories() {
        return ByteBufferFactory.readWriteFactories();
    }
}
