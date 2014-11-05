# Butterfly

> A Scala-based mini text editor

The name refers to this [xkcd](http://xkcd.com/378/)

## Run, test & build

### With sbt (recommended)

Simply run

`sbt`

Then, to run

`run`

To launch tests

`test`

To build

`assembly`

It will build a `butterfly-assembly-{version}.jar` somewhere in `target/` containing all dependencies

And that's all !

### With Maven (not recommended)

To build

`mvn clean package`

It will build a `.jar` inside the `target/` directory

To launch

`scala target/butterfly-{version}.jar`

To launch tests

`mvn test`

## Contributors

- Matthieu Riou
- Brice Thomas
