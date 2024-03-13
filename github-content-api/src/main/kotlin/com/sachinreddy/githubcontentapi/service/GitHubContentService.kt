package com.sachinreddy.githubcontentapi.service

import com.sachinreddy.githubcontentapi.model.GitHubBlobResponse
import com.sachinreddy.githubcontentapi.model.GitHubContentRequest
import com.sachinreddy.githubcontentapi.model.GitHubTreeResponse
import org.apache.tomcat.util.codec.binary.Base64.decodeBase64
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.util.*

@Service
class GitHubContentService @Autowired constructor(
    val webClient: WebClient
) {
    @Value("\${gitHub.url}")
    private val treeUrl: String? = null

    @Value("\${gitHub.auth}")
    private val auth: String? = null

    fun getContent(gitHubContentRequest: GitHubContentRequest): String {
        val url = gitHubContentRequest.url
        val branch = gitHubContentRequest.branch

        val urlComponents = url.split('/')

        if (!urlComponents.contains("github.com")) {
            throw Exception("URL is invalid.")
        }

        val owner = urlComponents[urlComponents.size - 2]
        val repository = urlComponents[urlComponents.size - 1]

        if (repository.endsWith(".git")) {
            throw Exception("URL is invalid.")
        }

        val treeURL = treeUrl!!.format(owner, repository, branch)

        return getContentString(getGitHubTree(treeURL))
    }

    private fun getContentString(gitHubTreeResponse: GitHubTreeResponse): String {
        var ret = "<documents>\n"
        var index = 1

        gitHubTreeResponse.tree.forEach {
            val blob = getGitHubBlob(it.url)

            try {
                blob.content?.let { blobContent ->
                    ret += "<document index=\"${index}\">\n"
                    ret += "<source>\n"
                    ret += "${it.path}\n"
                    ret += "</source>\n"
                    ret += "<document_content>\n"
                    ret += "${decodeBase64(blobContent.replace("\n", ""))}\n"
                    ret += "</document_content>\n"
                    ret += "</document>\n"

                    index += 1
                }
            } catch (e: Exception) {

            }
        }

        ret += "</documents>\n"

        return ret
    }

    private fun decodeBase64(base64String: String): String {
        val decodedBytes = Base64.getDecoder().decode(base64String)
        return String(decodedBytes)
    }

    private fun getGitHubTree(url: String): GitHubTreeResponse {
        val response = webClient.get()
                .uri(url)
                .header("Authorization", auth)
                .retrieve()
                .bodyToMono(GitHubTreeResponse::class.java)

        return response.block()!!
    }

    private fun getGitHubBlob(url: String): GitHubBlobResponse {
        val response = webClient.get()
                .uri(url)
                .header("Authorization", auth)
                .retrieve()
                .bodyToMono(GitHubBlobResponse::class.java)

        return response.block()!!
    }
}