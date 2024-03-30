# JInOut - Java Input/Output utility library

## Usage

Compatible with Java 8 and higher. Add Maven dependency to your project:
```
<dependency>
    <groupId>com.progralink</groupId>
    <artifactId>jinout</artifactId>
    <version>1.0.0</version>
</dependency>
```


## Functionalities

### I/O Streams

#### `IOStreams` utility
Some helpers for `InputStream` and `OutputStream`. Static Methods below:
- `int compare(InputStream in1, InputStream in2)`
- `long consume(InputStream in)`
- `long consume(InputStream in, long length)`
- `boolean equals(InputStream in2, InputStream in2)`
- `Long getRemainingByteLength(InputStream in)` *returns `null` when it is not possible to get remaining byte length*
- `long transfer(InputStream in, OutputStream out)`
- `byte readByte(InputStream in)`
- `int readByteAsInt(InputStream in)`
- `byte[] readExactly(InputStream in, int length)`
- `byte[] readFully(InputStream in, int expectedLength)`
- `byte[] readFully(InputStream in)`
- `void skipExactly(InputStream in, long n)`

#### Input Streams

##### Source Input Streams

- `FileChannelInputStream`

##### Input Stream Decorators (wrappers)
- `EndHandlingInputStream`
- `LengthAwareInputStream` *(extends `PositionAwareInputStream`)* 
- `PositionAwareInputStream` *(extends `EndHandlingInputStream`)*

#### Output Streams

##### Target Output Streams

- `NullOutputStream`

##### Output Stream Decorators (wrappers)
- `PositionAwareOutputStream`


### `Iterators` utility
Static Methods below:
- `Iterator<E> of(E... data)`
- `Iterator<E> filter(Iterator<E> iterator, Predicate<E> filter)`
- `Iterator<B> map(Iterator<A> iterator, Function<A,B> mapper)`
- `Stream<E> asStream(Iterator<E> iterator)`
- `Iterable<E> asIterable(Iterator<E> iterator)`
- `Iterable<E> asIterable(Supplier<Iterator<E>> iteratorSupplier)`


### `Bytes` utility
Static Methods below:

- `byte[] clean(byte[] data)`
- `byte[] clone(byte[] data)`
- `byte[] clone(byte[] data, int offset)`
- `byte[] clone(byte[] data, int offset, int length)`
- `int compare(byte[] a, byte[] b)`
- `byte[] concat(byte[]... args)`
- `boolean equals(byte[] array1, byte[] array2)`
- `byte[] generateIncremental(int byteLength)`
- `byte[] generateRandom(int byteLength)`
- `byte[] sub(byte[] source, int offset)`
- `byte[] sub(byte[] source, int offset, int length)`
- `String print(byte[] data)`
- `byte[] xor(byte[] left, byte[] right)`
- `byte[] zeroes(int length)`

### `BasicDataTypes` utility
Handles serialization and deserialization of primitive data types and strings.

#### Value to Byte Array
- `byte[] fromByte(byte value)`
- `void fromByte(byte value, byte[] target, int offset)`
- `byte[] fromDouble(double value)`
- `void fromDouble(double value, byte[] target, int offset)`
- `byte[] fromFloat(float value)`
- `void fromFloat(float value, byte[] target, int offset)`
- `byte[] fromInt(int value)`
- `void fromInt(int value, byte[] target, int offset)`
- `byte[] fromLong(long value)`
- `void fromLong(long value, byte[] target, int offset)`
- `byte[] fromShort(short value)`
- `void fromShort(byte value, byte[] target, int offset)`
- `byte[] fromString(String string)`
- `byte[] fromString(String string, boolean withEndOfStringMark)`
- `byte[] fromStringBase64(String base64)`
- `byte[] fromStringBase64URL(String base64)`

#### Byte Array to Value
- `double toDouble(byte[] data)`
- `double toDouble(byte[] data, int offset)`
- `float toFloat(byte[] data)`
- `float toFloat(byte[] data, int offset)`
- `int toInt(byte[] data)`
- `int toInt(byte[] data, int offset)`
- `long toLong(byte[] data)`
- `long toLong(byte[] data, int offset)`
- `short toShort(byte[] data)`
- `short toShort(byte[] data, int offset)`
- `String toString(byte[] bytes, Charset charset)`
- `String toStringUTF8(byte[] bytes)`
- `String toStringBase64(byte[] bytes)`
- `String toStringBase64(byte[] bytes, boolean withPadding)`
- `String toStringBase64URL(byte[] bytes)`
- `String toStringBase64URL(byte[] bytes, boolean withPadding)`
- `String toStringBytes(byte[] bytes)`
- `String toStringHex(byte[] bytes)`

#### Value to Output Stream
- `void writeDouble(OutputStream out, double value)`
- `void writeFloat(OutputStream out, float value)`
- `void writeInt(OutputStream out, int value)`
- `void writeLong(OutputStream out, long value)`
- `void writeShort(OutputStream out, short value)`
- `void writeString(OutputStream out, String string, boolean withEndOfStringMark)`
- `void writeString(OutputStream out, String string, Charset charset, boolean withEndOfStringMark)`

#### Input Stream to Value
- `byte readByte(InputStream in)`
- `int readByteAsInt(InputStream in)`
- `double readDouble(InputStream in)`
- `float readFloat(InputStream in)`
- `int readInt(InputStream in)`
- `long readLong(InputStream in)`
- `short readShort(InputStream in)`
- `String readString(InputStream in)`
- `String readString(InputStream in, Charset charset)`


### Hashes
#### `Hash` interface
- `byte[] compute(InputStream in)`
- `byte[] compute(byte[] data)`
- `byte[] compute(byte[] data, int offset, int length)`

#### `Hash` implementations
- Adler-32 (`Hash.Adler32`)
- CRC32 (`Hash.CRC32`)
- CRC32C (`Hash.CRC32C`) *supported from Java 9+*
- MD2 (`Hash.MD2`)
- MD5 (`Hash.MD5`)
- SHA-1 (`Hash.SHA1`)
- SHA-224 (`Hash.SHA2_224`)
- SHA-256 (`Hash.SHA2_256`)
- SHA-384 (`Hash.SHA2_384`)
- SHA-512 (`Hash.SHA2_512`)
- SHA3-224 (`Hash.SHA2_224`) *supported from Java 9+*
- SHA3-256 (`Hash.SHA2_256`) *supported from Java 9+*
- SHA3-384 (`Hash.SHA2_384`) *supported from Java 9+*
- SHA3-512 (`Hash.SHA2_512`) *supported from Java 9+*

#### Loading hashes
Hash can be dynamically found using `Hash.forName()` static method. It uses Service Loader so it's possible to provide custom implementation.

### Unicode

#### BOM
Enumeration containing definition, magic (starting) bytes, associated charset and matcher for Unicode BOM:
- `UTF_8`
- `UTF_16BE`
- `UTF_16LE`
- `UTF_32BE`
- `UTF_32LE`

Methods:
- `int getByteLength()`
- `Charset getCharset()`
- `byte[] getMagicBytes()`
- `boolean matches(byte[] data)`
- `byte[] toBytesWithBOM(String string)`

Static Methods:
- `BOM forCharset(Charset charset)` *returns `null` when none match*
- `BOM findMatching(byte[] data)` *returns `null` when none match*

#### StringFromBytesBuilder
Creates String from appendable bytes, handles Unicode BOM automatically if needed.

Methods:
- `append(byte[] bytes)`
- `append(byte[] bytes, int offset, int length)`
- `int getLength()`
- `String toString()` *will try to detect Unicode character set based on BOM*
- `String toStringUTF8()` *forces UTF-8, skips BOM when found* 
- `String toString(Charset charset)`


## License
Licensed under GNU LESSER GENERAL PUBLIC LICENSE (LGPL) 3.0: https://www.gnu.org/licenses/lgpl-3.0-standalone.html


## Need More?
If you need support or another licensing type, please contact us: https://www.progralink.com/contact/
