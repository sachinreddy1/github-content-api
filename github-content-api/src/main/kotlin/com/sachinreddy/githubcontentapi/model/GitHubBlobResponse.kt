package com.sachinreddy.githubcontentapi.model

import com.fasterxml.jackson.annotation.JsonProperty

data class GitHubBlobResponse(
    @JsonProperty("content")
    val content: String?
)