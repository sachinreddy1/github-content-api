package com.sachinreddy.githubcontentapi.model

import com.fasterxml.jackson.annotation.JsonProperty

data class GitHubTreeResponse(
    @JsonProperty("tree")
    val tree: List<GitHubTreeFile>
)