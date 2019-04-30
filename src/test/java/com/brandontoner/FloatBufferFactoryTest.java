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

import java.nio.FloatBuffer;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

class FloatBufferFactoryTest extends AbstractFactoryTest<float[], FloatBuffer, FloatBufferFactory> {
    @Override
    FloatBuffer wrap(final float[] array) {
        return FloatBuffer.wrap(array);
    }

    @Override
    FloatBuffer wrap(final float[] array, final int offset, final int length) {
        return FloatBuffer.wrap(array, offset, length);
    }

    @Override
    float[] randomArray(final int size) {
        float[] output = new float[size];
        for (int i = 0; i < output.length; i++) {
            output[i] = ThreadLocalRandom.current().nextFloat();
        }
        return output;
    }

    @Override
    Collection<FloatBufferFactory> allFactories() {
        return FloatBufferFactory.allFactories();
    }

    @Override
    Collection<FloatBufferFactory> readOnlyFactories() {
        return FloatBufferFactory.readOnlyFactories();
    }

    @Override
    Collection<FloatBufferFactory> readWriteFactories() {
        return FloatBufferFactory.readWriteFactories();
    }
}
