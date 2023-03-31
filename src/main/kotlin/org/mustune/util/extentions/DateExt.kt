package org.mustune.util.extentions

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

fun LocalDateTime.toDate(): Date = Date.from(atZone(ZoneId.systemDefault()).toInstant())

fun Date.toLocalDateTime(): LocalDateTime = LocalDateTime.ofInstant(toInstant(), ZoneId.systemDefault())