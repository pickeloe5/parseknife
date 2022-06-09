# parseknife
[![](https://jitpack.io/v/pickeloe5/parseknife.svg)](https://jitpack.io/#pickeloe5/parseknife)

Extensible Kotlin parsing library

## Usage
Using ParseKnife primarily revolves around
[`Rule`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife.rule/-rule/)s,
creating them, and using them to produce
[`Token`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife/-token/)s
from
[`Source`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife/-source/)s.

### Making Rules
[`Rule`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife.rule/-rule/)s
are Kotlin classes that implement a shared abstraction.

So you can make rules by constructing those classes, there also exists a helper object for this:
[`r`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife.rule/r/)

E.g. `r.or(r.regex("x|yz"), r.eof())`, `AndRule('a', 'b')`, `RegexRule("[0-9]+")`

### Making Tokens
[`Token`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife/-token/)s
are subsections of source with associated
[`meta`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife/-token/index.html#-1595205917%2FProperties%2F769193423)data
as well as nested
[`children`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife/-token/index.html#-834199895%2FProperties%2F769193423). 

The
[`Parser`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife/-parser/)
abstraction uses
[`Rule`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife.rule/-rule/)s
to produce
[`Token`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife/-token/)s.

It's generic, so it can be extended to return any information transformed from those
[`Token`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife/-token/)s.

[`Rule`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife.rule/-rule/)s
also directly expose a
[`makeToken(Cursor): Token`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife.rule/-rule/index.html#-306097049%2FFunctions%2F769193423)

\* A note on
[`RegexRule`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife.rule/-regex-rule/)s:
Capture-groups are stored as
[`children`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife/-token/index.html#-834199895%2FProperties%2F769193423),
with named groups including a "groupName" key in their
[`meta`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife/-token/index.html#-1595205917%2FProperties%2F769193423).

### Using Metadata
Their exists a
[`meta`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife/-token/index.html#-1595205917%2FProperties%2F769193423)
property, mapping `String` keys to `Any?`s, on both
[`Token`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife/-token/)s
and
[`Rule`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife.rule/-rule/)s.

The
[`meta`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife/-token/index.html#-1595205917%2FProperties%2F769193423)data
of a
[`Rule`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife.rule/-rule/)
is generally applied to all root
[`Token`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife/-token/)s
produced by that
[`Rule`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife.rule/-rule/).

Convenience methods have been added for using this
[`meta`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife/-token/index.html#-1595205917%2FProperties%2F769193423)data:
 - [`withMeta(key: String, value: Any?): this`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife.rule/-rule/index.html#1168209552%2FFunctions%2F769193423)
   accomplishes a sort of "builder" pattern
 - [`query(maxDepth: Int? = null, meth: (Token) -> Boolean)`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife/-token/index.html#1555356706%2FFunctions%2F769193423)
   searches breadth-first

### Parser Parser
Also included is a parser that parses these parsers for you, so you don't have to parse through this parsing documentation to make your parser.

You can use it via
[`metaParser`](https://parseknife.nickmatt.dev/docs/-parse-knife/dev.nickmatt.parseknife.meta/meta-parser.html).
Here is its source for itself.
```
whitespace = /\s+/;

character = /'(?<content>\\.|[^'])'/;
regex = /\/(?<content>(\\.|[^\/])*)\//;
string = /"(?<content>(\\.|[^'])*)"/;

endOfFile = "$eof";
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
