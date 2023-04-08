package org.mustune.util.exceptions

class UnknownServerException(override val message: String? = null, override val cause: Throwable? = null) : Exception()