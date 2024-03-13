package com.sachinreddy.githubcontentapi.controller

import com.sachinreddy.githubcontentapi.model.GitHubContentRequest
import com.sachinreddy.githubcontentapi.model.GitHubTreeResponse
import com.sachinreddy.githubcontentapi.service.GitHubContentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/github/content")
class GitHubContentController @Autowired constructor(
    private val gitHubContentService: GitHubContentService
){
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getContent(
        @RequestBody gitHubContentRequest: GitHubContentRequest
    ): String {
        return gitHubContentService.getContent(gitHubContentRequest)
    }
}