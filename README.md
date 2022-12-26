# playground-java


### 001-akka-supervisor
Calculating factorials by actors system (with implemented supervision strategy).

#### Running
1. Go to project: `cd 001-akka-supervisor`
2. Build jar: `mvn clean package`
3. Run: `java -jar target/akka-supervisor-1.0.0-allinone.jar`

### 002-game-of-life
Conway's game of life. 
Printing is operating system dependent and works fine only on unix-like systems.

#### Running
1. Go to project: `cd 002-game-of-life`
2. Build jar: `mvn clean package`
3. Run: `java -jar target/gol-1.0.0.jar <universe-size> <number-of-generations>` i.e. `java -jar target/gol-1.0.0.jar 40 60`

### 003-hello-graalvm
`Hallo world` app with maven and graalvm.

#### Running
1. Addjust PATH: `export PATH=/<path>/<to>/<graalvm>/bin:$PATH`
2. Set JAVA_HOME: `export JAVA_HOME=/<path>/<to>/<graalvm>`
3. Go to project: `cd 003-hello-graalvm`
4. Build binary: `mvn -Pnative -Dagent package`
5. Run: `./target/hellograalvm`
