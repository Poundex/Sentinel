package net.poundex.sentinel.fathom.apiai

import feign.Headers
import feign.Param
import feign.RequestLine
import net.poundex.sentinel.fathom.apiai.ApiAiQuery
import net.poundex.sentinel.fathom.apiai.ApiAiQueryResult

interface ApiAiQueryClient
{
	public static String BASE_URL = "https://api.dialogflow.com/v1/"

	@RequestLine("POST /query?v=20170712")
	@Headers(["Authorization: Bearer {accessToken}", "Content-type: application/json"])
	ApiAiQueryResult query(@Param("accessToken") String accessToken, ApiAiQuery query)
}
