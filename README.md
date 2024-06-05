### SARO Image Magick Wrapper
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/me.saro/image-magick-wrapper/badge.svg)](https://maven-badges.herokuapp.com/maven-central/me.saro/image-magick-wrapper)
[![GitHub license](https://img.shields.io/github/license/saro-lab/image-magick-wrapper.svg)](https://github.com/saro-lab/image-magick-wrapper/blob/master/LICENSE)

# QUICK START

## Install Image Magick
- [Image Magick](https://imagemagick.org)
- This project is a wrapper for ImageMagick. Therefore, you need to install ImageMagick from the official website before using this project. This project supports Windows, macOS, Linux, and Docker environments.

## Dependency
#### gradle kts
```
implementation("me.saro:image-magick-wrapper:1.0.0")
```
#### gradle
```
compile 'me.saro:image-magick-wrapper:1.0.0'
```
#### maven
``` xml
<dependency>
  <groupId>me.saro</groupId>
  <artifactId>image-magick-wrapper</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Java Example
- [Image Information](https://github.com/saro-lab/image-magick-wrapper/blob/master/src/test/java/me/saro/imw/jt/ImageInfoTest.java)
- [Convert Image](https://github.com/saro-lab/image-magick-wrapper/blob/master/src/test/java/me/saro/imw/jt/ImageConvertTest.java)
```java
// convert example
var info = ImageMagick.create()
    .resize(120, 120)
    .webpCompression()
    .convert(new File("/tmp/example.gif"), new File("/tmp/example-resize-gif.webp"));
assertTrue(info.isSuccess());
```

```java
// information example
var info = ImageMagick.create()
    .getImageInfo(new File("/tmp/example.gif"));
assertTrue(info.isSuccess());
var data = info.getData();
assertEquals(data.size(), 31);
var first = data.get(0);
assertEquals(first.getWidth(), 600);
assertEquals(first.getHeight(), 600);
assertEquals(first.getFormat(), "GIF");
```

## Kotlin Example
- [Image Information](https://github.com/saro-lab/image-magick-wrapper/blob/master/src/test/kotlin/me/saro/imw/kt/ImageInfoTest.kt)
- [Convert Image](https://github.com/saro-lab/image-magick-wrapper/blob/master/src/test/kotlin/me/saro/imw/kt/ImageConvertTest.kt)
```kotlin
val info: ImageMagick.create()
    .resize(120, 120)
    .webpCompression()
    .convert(File("/tmp/example.gif"), File("/tmp/example-resize-gif.webp"))
Assertions.assertTrue(info.isSuccess)
```

```kotlin
val info = ImageMagick.create()
    .getImageInfo(File("/tmp/example.gif"))
Assertions.assertTrue(info.isSuccess)
val data = info.data!!
Assertions.assertEquals(data.size, 31)
val first = data[0]
Assertions.assertEquals(first.width, 600)
Assertions.assertEquals(first.height, 600)
Assertions.assertEquals(first.format, "GIF")
```

## repository
- https://search.maven.org/artifact/me.saro/image-magick-wrapper
- https://mvnrepository.com/artifact/me.saro/image-magick-wrapper

## see
- [Image Magick](https://imagemagick.org)
- [가리사니의 조각들...](https://gs.saro.me)

