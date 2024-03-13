package com.sachinreddy.githubcontentapi.model

import com.fasterxml.jackson.annotation.JsonProperty

data class GitHubContentRequest(
    @JsonProperty("url")
    val url: String,

    @JsonProperty("branch")
    val branch: String
)