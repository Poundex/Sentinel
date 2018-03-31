package net.poundex.sentinel.fathom

import feign.Headers
import feign.RequestLine

interface QueryClient
{
	@RequestLine("POST /query")
	@Headers(["Content-type: application/json", "Accept: application/json"])
	ResponsePackage query(Query query)
}
