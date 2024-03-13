package com.sachinreddy.githubcontentapi.model

import com.fasterxml.jackson.annotation.JsonProperty

data class GitHubTreeFile(
    @JsonProperty("path")
    val path: String,

    @JsonProperty("url")
    val url: String
)