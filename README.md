# Kottp
Simple and easy Kotlin http ver 2 client

```kotlin
var client = "https://google.com".kottp
// Simple get request
var response = client.get()
response = client.get(
    Header("Content-Type", "text/json"),
    Header("login", "blah-blah-blah")
)

response.text() // Text response
response.body() // ByteArray from server
response.statusCode() // Status code from server
// etc
```

## Post
```kotlin
var client = "https://google.com".kottp
// Simple post request
var response = client.post("Hi there!".toByteArray())
response = client.post(
    "Hi there!".toByteArray(),
    Header("Content-Type", "text/json"),
    Header("login", "blah-blah-blah")
)
```

## Asyn
```kotlin
// Get request
var future = client.getAsync()
// Post request 
future = client.postAsync("Info".toByteArray())
// Retriving future object
future.get()
// or
future.thenAccept {
    println(it.text()) // Async print response text
}
```