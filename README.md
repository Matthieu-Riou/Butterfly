# Butterfly

*The name refers to this [xkcd](http://xkcd.com/378/)*

<p align="center">
  <img
    alt="butterfly_logo"
    height="169"
    width="246"
    src="https://cloud.githubusercontent.com/assets/1422403/5165970/82a59476-73eb-11e4-802b-9c063f1818f0.png"
  />
</p>

Butterfly is a tiny **text editor library** written in **Scala**, it offers features like multi-(cursors & selections & editors) with a simple API

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

### With Maven

To build

`mvn clean package -U`

It will build a `.jar` inside the `target/` directory

To launch

`scala target/butterfly-{version}.jar`

To launch tests

`mvn test`

## Contributors

- Matthieu Riou :bear:
- Brice Thomas :koala:
