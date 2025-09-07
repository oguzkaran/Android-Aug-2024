package org.csystem.app.random.text.client.viewmodel

import org.csystem.app.random.text.client.constant.DEFAULT_SERVER_PARAM_COUNT
import org.csystem.app.random.text.client.constant.DEFAULT_SERVER_PARAM_MAX_LENGTH
import org.csystem.app.random.text.client.constant.DEFAULT_SERVER_PARAM_MIN_LENGTH

data class ServerParam(var count: String = DEFAULT_SERVER_PARAM_COUNT,
                       var minLength: String = DEFAULT_SERVER_PARAM_MIN_LENGTH,
                       var maxLength: String = DEFAULT_SERVER_PARAM_MAX_LENGTH
)