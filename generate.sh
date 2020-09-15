#!/bin/bash
set -uex

TYPES=`mktemp`
cat << EOF > "${TYPES}"
byte ByteBuffer Byte
char CharBuffer Character
double DoubleBuffer Double
float FloatBuffer Float
int IntBuffer Integer
long LongBuffer Long
short ShortBuffer Short
EOF

while read ARRAY_TYPE BUFFER_TYPE BOXED_TYPE; do

cat << EOF > "src/main/java/com/brandontoner/${BUFFER_TYPE}Factory.java"
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

import java.nio.${BUFFER_TYPE};
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Factory for ${BUFFER_TYPE}s.
 */
public interface ${BUFFER_TYPE}Factory extends BufferFactory<${ARRAY_TYPE}[], ${BUFFER_TYPE}> {
    /**
     * Gets a Collection of {@link ${BUFFER_TYPE}Factory}s which create non-readonly buffers.
     *
     * @return Collection of {@link ${BUFFER_TYPE}Factory}s which create non-readonly buffers
     */
    static Collection<${BUFFER_TYPE}Factory> readWriteFactories() {
        return Arrays.asList(ReadWrite${BUFFER_TYPE}Factory.values());
    }

    /**
     * Gets a Collection of {@link ${BUFFER_TYPE}Factory}s which create readonly buffers.
     *
     * @return Collection of {@link ${BUFFER_TYPE}Factory}s which create readonly buffers
     */
    static Collection<${BUFFER_TYPE}Factory> readOnlyFactories() {
        return readWriteFactories().stream().map(ReadOnly${BUFFER_TYPE}Factory::new).collect(Collectors.toList());
    }

    /**
     * Gets a Collection of {@link ${BUFFER_TYPE}Factory}s.
     *
     * @return Collection of {@link ${BUFFER_TYPE}Factory}s
     */
    static Collection<${BUFFER_TYPE}Factory> allFactories() {
        return Stream.concat(readWriteFactories().stream(), readOnlyFactories().stream()).collect(Collectors.toList());
    }

    /**
     * Allocates a ${BUFFER_TYPE} with the given size, i.e. {@link ${BUFFER_TYPE}#remaining()} will return {@code length}.
     *
     * @param length the number of elements that should be remaining in the buffer
     * @return buffer with specified number of elements
     */
    @Override
    ${BUFFER_TYPE} allocate(int length);

    /**
     * Creates a {@link ${BUFFER_TYPE}} with the given contents. The resulting buffer will be equal to
     * {@code ${BUFFER_TYPE}.wrap(array)}. The buffer will be independent of the provided array.
     *
     * @param array array to copy
     * @return ${BUFFER_TYPE} with given contents
     */
    @Override
    default ${BUFFER_TYPE} copyOf(final ${ARRAY_TYPE}[] array) {
        return copyOf(array, 0, array.length);
    }

    /**
     * Creates a {@link ${BUFFER_TYPE}} with the given contents. The resulting buffer will be equal to
     * {@code ${BUFFER_TYPE}.wrap(array, offset, length)}. The buffer will be independent of the provided array.
     *
     * @param array array to copy
     * @return ${BUFFER_TYPE} with given contents
     */
    @Override
    default ${BUFFER_TYPE} copyOf(final ${ARRAY_TYPE}[] array, final int offset, final int length) {
        ${BUFFER_TYPE} buffer = allocate(length);
        buffer.duplicate().put(array, offset, length);
        return buffer;
    }

    /**
     * Creates a ${BUFFER_TYPE} with the given contents. The resulting buffer will be equal to {@code buffer}.
     * The position, limit, mark, and contents of {@code buffer} will be unchanged.
     *
     * @param buffer buffer to copy
     * @return Buffer with given contents
     */
    @Override
    default ${BUFFER_TYPE} copyOf(final ${BUFFER_TYPE} buffer) {
        ${BUFFER_TYPE} output = allocate(buffer.remaining());
        output.duplicate().put(buffer.duplicate());
        return output;
    }
}
EOF

cat << EOF > "src/main/java/com/brandontoner/ReadOnly${BUFFER_TYPE}Factory.java"
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

import java.nio.${BUFFER_TYPE};

/**
 * Provides read-only implementation of {@link ${BUFFER_TYPE}Factory}.
 */
class ReadOnly${BUFFER_TYPE}Factory implements ${BUFFER_TYPE}Factory {
    /**
     * Delegate factory.
     */
    private final ${BUFFER_TYPE}Factory factory;

    /**
     * Constructor.
     *
     * @param factory delegate factory
     */
    ReadOnly${BUFFER_TYPE}Factory(final ${BUFFER_TYPE}Factory factory) {
        this.factory = factory;
    }

    @Override
    public ${BUFFER_TYPE} allocate(final int length) {
        return factory.allocate(length).asReadOnlyBuffer();
    }

    @Override
    public ${BUFFER_TYPE} copyOf(final ${ARRAY_TYPE}[] array, final int offset, final int length) {
        return factory.copyOf(array, offset, length).asReadOnlyBuffer();
    }

    @Override
    public ${BUFFER_TYPE} copyOf(final ${BUFFER_TYPE} buffer) {
        return factory.copyOf(buffer).asReadOnlyBuffer();
    }

    @Override
    public String toString() {
        return "READ_ONLY_" + factory;
    }
}
EOF

if [ "${BUFFER_TYPE}" == "ByteBuffer" ]; then
  AS_BUFFER_FUNC=""
else
  AS_BUFFER_FUNC=".as${BUFFER_TYPE}()"
fi

cat << EOF > "src/main/java/com/brandontoner/ReadWrite${BUFFER_TYPE}Factory.java"
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

EOF
{
  echo "import java.nio.ByteBuffer;";
  echo "import java.nio.${BUFFER_TYPE};";
} | sort -u >> "src/main/java/com/brandontoner/ReadWrite${BUFFER_TYPE}Factory.java"

cat << EOF >> "src/main/java/com/brandontoner/ReadWrite${BUFFER_TYPE}Factory.java"

/**
 * Enumeration providing read-write implementations of {@link ${BUFFER_TYPE}Factory}.
 */
enum ReadWrite${BUFFER_TYPE}Factory implements ${BUFFER_TYPE}Factory {
    /**
     * Allocates non-direct ${BUFFER_TYPE}s of the correct size.
     */
    NON_DIRECT_CORRECT_SIZE {
        @Override
        public ${BUFFER_TYPE} allocate(final int length) {
            return ${BUFFER_TYPE}.allocate(length);
        }
    },
    /**
     * Allocates non-direct ${BUFFER_TYPE}s with padding before the data.
     */
    NON_DIRECT_PADDING_BEFORE {
        @Override
        public ${BUFFER_TYPE} allocate(final int length) {
            ${BUFFER_TYPE} buffer = ${BUFFER_TYPE}.allocate(length + 10);
            buffer.position(10);
            return buffer;
        }
    },
    /**
     * Allocates non-direct ${BUFFER_TYPE}s with padding after the data.
     */
    NON_DIRECT_PADDING_AFTER {
        @Override
        public ${BUFFER_TYPE} allocate(final int length) {
            ${BUFFER_TYPE} buffer = ${BUFFER_TYPE}.allocate(length + 10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    },
    /**
     * Allocates non-direct ${BUFFER_TYPE}s with padding before and after the data.
     */
    NON_DIRECT_PADDING_BOTH {
        @Override
        public ${BUFFER_TYPE} allocate(final int length) {
            ${BUFFER_TYPE} buffer = ${BUFFER_TYPE}.allocate(length + 20);
            buffer.position(10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    },
    /**
     * Allocates non-direct ${BUFFER_TYPE}s with correct size, but non-zero array offset.
     */
    NON_DIRECT_NON_ZERO_ARRAY_OFFSET {
        @Override
        public ${BUFFER_TYPE} allocate(final int length) {
            ${ARRAY_TYPE}[] array = new ${ARRAY_TYPE}[length + 10];
            ${BUFFER_TYPE} buffer = ${BUFFER_TYPE}.wrap(array, 10, length).slice();
            assert buffer.arrayOffset() != 0 : "Array offset should be non-zero";
            return buffer;
        }
    },
    /**
     * Allocates direct ${BUFFER_TYPE}s of the correct size.
     */
    DIRECT_CORRECT_SIZE {
        @Override
        public ${BUFFER_TYPE} allocate(final int length) {
            return allocateDirect(length);
        }
    },
    /**
     * Allocates direct ${BUFFER_TYPE}s with padding before the data.
     */
    DIRECT_PADDING_BEFORE {
        @Override
        public ${BUFFER_TYPE} allocate(final int length) {
            ${BUFFER_TYPE} buffer = allocateDirect(length + 10);
            buffer.position(10);
            return buffer;
        }
    },
    /**
     * Allocates direct ${BUFFER_TYPE}s with padding after the data.
     */
    DIRECT_PADDING_AFTER {
        @Override
        public ${BUFFER_TYPE} allocate(final int length) {
            ${BUFFER_TYPE} buffer = allocateDirect(length + 10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    },
    /**
     * Allocates direct ${BUFFER_TYPE}s with padding before and after the data.
     */
    DIRECT_PADDING_BOTH {
        @Override
        public ${BUFFER_TYPE} allocate(final int length) {
            ${BUFFER_TYPE} buffer = allocateDirect(length + 20);
            buffer.position(10);
            buffer.limit(buffer.position() + length);
            return buffer;
        }
    };

    /**
     * Allocates a direct ${BUFFER_TYPE} with the given capacity.
     *
     * @param length capacity
     * @return direct ${BUFFER_TYPE}.
     */
    static ${BUFFER_TYPE} allocateDirect(final int length) {
        return ByteBuffer.allocateDirect(length * ${BOXED_TYPE}.BYTES)${AS_BUFFER_FUNC};
    }
}
EOF

done < "${TYPES}"
