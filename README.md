# Set up docker hub creds (~/.m2/settings.xml)
```
<settings>
    <servers>
        <server>
            <id>registry.hub.docker.com</id>
            <username>docker-name</username>
            <password>docker-pass</password>
        </server>
    </servers>
</settings>
```

# Set up docker image name (pom.xml)
```
...
<plugin>
    <groupId>com.google.cloud.tools</groupId>
    <artifactId>jib-maven-plugin</artifactId>
    <version>3.2.0</version>
    <configuration>
        <to>
            <image>registry.hub.docker.com/docker-username/docker-image-name</image>
        </to>
    </configuration>
...
```

# Build
`mvn clean package`

# Run

`docker run -v /path/to/emails.json:/email.json:ro email_rusnya /email.json`

# Email File format
## Only Gmail and Yahoo emails supported atm!

```json
[
    {
        "username": "ex@ex.com",
        "password": "p@ssw0rd"
    },
    {
      "username": "ex2@ex.com",
      "password": "p@ssw0rd2"
    }
]
```

# Description
Takes each email from JSON file and sends messages till the server refuses (threshold 10 exceptions) and then moves to the next email.