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
Some helpers for `InputStream` and `OutputStream`:
- `int compare(InputStream in1, InputStream in2)`
- `long consume(InputStream in)`
- `long consume(InputStream in, long length)`
- `boolean equals(InputStream in2, InputStream in2)`
- `Long getRemainingByteLength(InputStream in)`
- `long transfer(InputStream in, OutputStream out)`
- `byte readByte(InputStream in)`
- `int readByteAsInt(InputStream in)`
- `byte[] readExactly(InputStream in, int length)`
- `byte[] readFully(InputStream in, int expectedLength)`
- `byte[] readFully(InputStream in)`
- `void skipExactly(InputStream in, long n)`

#### Input Streams

- `FileChannelInputStream`
- `LengthAwareInputStream`
- `PositionAwareInputStream`
- `PositionAwareOutputStream`

#### Output Streams

- `NullOutputStream`
- `PositionAwareOutputStream`


### `Iterators` utility
- `Iterator<E> of(E... data)`
- `Iterator<E> filter(Iterator<E> iterator, Predicate<E> filter)`
- `Iterator<B> map(Iterator<A> iterator, Function<A,B> mapper)`
- `Stream<E> asStream(Iterator<E> iterator)`
- `Iterable<E> asIterable(Iterator<E> iterator)`
- `Iterable<E> asIterable(Supplier<Iterator<E>> iteratorSupplier)`


### `Bytes` utility

#### General
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

#### FROM value to byte array
- `byte[] fromByte(byte value)`
- `byte[] fromDouble(double value)`
- `byte[] fromFloat(float value)`
- `byte[] fromInt(int value)`
- `byte[] fromLong(long value)`
- `byte[] fromShort(short value)`
- `byte[] fromString(String string)`
- `byte[] fromString(String string, boolean withEndOfStringMark)`
- `byte[] fromStringBase64(String base64)`
- `byte[] fromStringBase64URL(String base64)`

#### from byte array TO value
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
- `String toString(byte[] bytes)`
- `String toString(byte[] bytes, Charset charset)`
- `String toStringBase64(byte[] bytes)`
- `String toStringBase64(byte[] bytes, boolean withPadding)`
- `String toStringBase64URL(byte[] bytes)`
- `String toStringBase64URL(byte[] bytes, boolean withPadding)`
- `String toStringHex(byte[] bytes)`


### Hashes
#### `Hash` interface
- `byte[] compute(InputStream inputStream)`
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


## License
Licensed under GNU LESSER GENERAL PUBLIC LICENSE (LGPL) 3.0: https://www.gnu.org/licenses/lgpl-3.0-standalone.html


## Need More?
If you need support or another licensing type, please contact us: https://www.progralink.com/contact/
