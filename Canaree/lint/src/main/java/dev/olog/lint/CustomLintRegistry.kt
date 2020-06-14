package dev.olog.lint

class CustomLintRegistry : IssueRegistry() {

    override val issues: List<Issue>
        get() = listOf()

    override val api: Int
        get() = CURRENT_API

}