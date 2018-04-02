package net.poudnex.sentinel.caretaker.home.lighting.hue

import feign.Param
import feign.RequestLine

interface HueBridgeClient
{
	@RequestLine("GET /")
	HueApiResponse getEverything()

	@RequestLine("PUT /lights/{bulbId}/state")
	List<Map> setLightState(
		@Param("bulbId") String bulbId,
		Map state)
}
