# kotlin-full-sample
A sample of an full application in Kotlin

## How it works
This is a simple service-api writed in [Kotlin](https://kotlinlang.org) with follow libraries:
* [Javalin](https://javalin.io/)
* [Exposed SQL](https://github.com/JetBrains/Exposed)
* [Fuel HTTP](https://github.com/kittinunf/fuel)
* [Koin](https://insert-koin.io/)
* [Flyway](https://flywaydb.org/)

Tests:
* [Spek Framework](https://www.spekframework.org/)
* [AssertJ](https://joel-costigliola.github.io/assertj/)
* [Mockk](https://mockk.io/)

## Getting started
You need just JVM installed.

##### Build
```shell script
$ ./gradlew clean build
```

##### Run docker container to start postgres
```shell script
$ cd {project_path}/others/docker
$ docker-compose up
```
##### Run Flaway
```shell script
$ ./gradlew flywaymigrate
```

##### Start the server
```shell script
$ ./gradlew run
```

##### Import postman collection to test API
```shell script
$ cd {project_path}/others/postman
```

## License

MIT License

Copyright (c) 2019 Emikael Silveira

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

## Author
Emikael Silveira <emikael.silveira@gmail.com>

## Help
Please fork and PR to improve the code.