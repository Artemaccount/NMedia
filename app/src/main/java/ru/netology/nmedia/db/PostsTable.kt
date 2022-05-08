package ru.netology.nmedia.db

object PostsTable {

    const val NAME = "posts"

    fun ALL_COLUMN_NAMES() = Column.values().map(Column::columnName).toTypedArray()

    val CREATE_SCRIPT = """
        CREATE TABLE $NAME(
        ${Column.ID.columnName} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${Column.AUTHOR.columnName} TEXT NOT NULL,
        ${Column.CONTENT.columnName} TEXT NOT NULL,
        ${Column.PUBLISHED.columnName} TEXT NOT NULL,
        ${Column.LIKED_BY_ME.columnName} BOOLEAN NOT NULL DEFAULT 0,
        ${Column.LIKES.columnName} INTEGER NOT NULL DEFAULT 0,
        ${Column.REPOSTS.columnName} INTEGER NOT NULL DEFAULT 0,
        ${Column.WATCHES.columnName} INTEGER NOT NULL DEFAULT 0,
        ${Column.VIDEO.columnName} TEXT)
    """.trimIndent()

    enum class Column(val columnName: String) {
        ID("id"),
        AUTHOR("author"),
        CONTENT("content"),
        PUBLISHED("published"),
        LIKED_BY_ME("likedByMe"),
        LIKES("likes"),
        REPOSTS("reposts"),
        WATCHES("watches"),
        VIDEO("video")
    }
}