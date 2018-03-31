package net.poundex.sentinel.server.apiai

import feign.Headers
import feign.RequestLine
import net.poundex.sentinel.fathom.apiai.ApiAiQuery
import net.poundex.sentinel.fathom.apiai.ApiAiQueryResult

interface ApiAiQueryClient
{
	public static String BASE_URL = "https://api.dialogflow.com/v1/"

	@RequestLine("POST /query?v=20170712")
	@Headers("Authorization: Bearer c8064cb8cbef4ba3b21f6410ec08948f")
	ApiAiQueryResult query(ApiAiQuery query)
}
