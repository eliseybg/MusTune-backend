package org.mustune.util.exceptions

class UnauthorizedException(override val message: String? = null, override val cause: Throwable? = null) : Exception()