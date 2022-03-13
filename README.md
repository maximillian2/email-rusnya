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