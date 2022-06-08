# parseknife
[![](https://jitpack.io/v/pickeloe5/parseknife.svg)](https://jitpack.io/#pickeloe5/parseknife)

Extensible Kotlin parsing library

## Usage
Using ParseKnife primarily revolves around `Rule`s, creating them, and using them to produce `Token`s from `Source`s.

### Making Rules
`Rule`s are Kotlin classes that implement a shared abstraction.

So you can make rules by constructing those classes, there also exists a helper object for this: `r`

E.g. `r.or(r.regex("x|yz"), r.eof())`, `AndRule('a', 'b')`, `RegexRule("[0-9]+")`

### Making Tokens
The `Parser` abstraction uses `Rule`s to produce `Token`s.

It's generic, so it can be extended to return any information transformed from those `Token`s.

`Rule`s also directly expose a `makeToken(Cursor): Token`

\* A note on `RegexRule`s: Capture-groups are stored as `children`, with named groups including a "groupName" key in their `meta`.

### Using Metadata
Their exists a `meta` property, mapping `String` keys to `Any?`s, on both `Token`s and `Rule`s.

The `meta`data of a `Rule` is generally applied to all root `Token`s produced by that `Rule`.

Convenience methods have been added for using this `meta`data:
 - `withMeta(key: String, value: Any?): this` accomplishes a sort of "builder" pattern
 - `query(maxDepth: Int? = null, meth: (Token) -> Boolean)` searches breadth-first

### Parser Parser
Also included is a parser that parses these parsers for you, so you don't have to parse through this parsing documentation to make your parser.

Here is its source for itself.
```
whitespace = /\s+/;

character = /'(?<content>\\.|[^'])'/;
regex = /\/(?<content>(\\.|[^\/])*)\//;
string = /"(?<content>(\\.|[^'])*)"/;

endOfFile = "${'$'}eof";
integer = /[0-9]+/;
ruleName = /[a-zA-Z_]+/;
group = '(' or ')';

decorator = /[+?*!^]*/;
termValue = integer
    | character
    | string
    | regex
    | group
    | ruleName;
term = (termValue decorator)^;

and = (term (whitespace term)^*)^;
or = and ('|' and)*;

rule = ruleName '=' or ';';
language = rule+;
```

## Download
Latest release: [v0.0.1](https://github.com/pickeloe5/parseknife/releases/tag/0.0.1)

Direct download links are bundled with this release for jars containing both
the [compiled](https://github.com/pickeloe5/parseknife/releases/download/0.0.1/parseknife.jar) library and
its [sources](https://github.com/pickeloe5/parseknife/releases/download/0.0.1/parseknife-sources.jar).

### Gradle (Kotlin)
```kotlin
repositories {
  // ...
  mavenCentral()
  maven { url = uri("https://jitpack.io") }
}
dependencies {
  // ...
  implementation("com.github.pickeloe5:parseknife:0.0.1")
}
```

### Gradle (Groovy)
```groovy
repositories {
  // ...
  mavenCentral()
  maven { url 'https://jitpack.io' }
}
dependencies {
  // ...
  implementation 'com.github.pickeloe5:parseknife:0.0.1'
}
```

### Maven
```xml
<repositories>
  <!-- ... -->
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
<dependencies>
  <!-- ... -->
  <dependency>
    <groupId>com.github.pickeloe5</groupId>
    <artifactId>parseknife</artifactId>
    <version>0.0.1</version>
  </dependency>
</dependencies>
```

[JitPack](https://jitpack.io/#pickeloe5/parseknife/0.0.1) has some information about configuring other dependency managers.
