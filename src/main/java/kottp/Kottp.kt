package kottp

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.CompletableFuture

class Kottp(private var url: String) {
    private val client = HttpClient.newHttpClient()

    fun customRequest(
        method: String,
        body: ByteArray,
        vararg headers: Header
    ): CompletableFuture<HttpResponse<ByteArray>>? {
        return client.sendAsync(
            HttpRequest.newBuilder()
                .uri(URI.create(url))
                .method(method, HttpRequest.BodyPublishers.ofByteArray(body))
                .run {
                    headers.forEach { header(it.k, it.v) }
                    this
                }
                .build(),
            HttpResponse.BodyHandlers.ofByteArray())
    }

    fun getAsync(vararg headers: Header): CompletableFuture<HttpResponse<ByteArray>>? {
        return customRequest("GET", byteArrayOf(), *headers)
    }

    fun get(vararg headers: Header): HttpResponse<ByteArray>? {
        return getAsync()!!.get()
    }

    fun postAsync(body: ByteArray, vararg headers: Header): CompletableFuture<HttpResponse<ByteArray>>? {
        return customRequest("POST", body, *headers)
    }

    fun post(body: ByteArray, vararg headers: Header): HttpResponse<ByteArray>? {
        return postAsync(body, *headers)!!.get()
    }
}

val String.kottp: Kottp
    get() = Kottp(this)

data class Header(val k: String, val v: String)

fun HttpResponse<ByteArray>?.text(): String {
    if (this == null) return ""
    return String(this.body())
}