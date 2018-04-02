package net.poudnex.sentinel.caretaker.home.lighting.hue

class HueApiResponse
{
	Map<String, LightInfo> lights

	static class LightInfo
	{
		LightState state

		static class LightState
		{
			boolean on
			int bri
			int hue
			int sat
			double[] xy
			int ct
			String alert
			String colormode
			String mode
			boolean reachable
		}

		String type
		String name
		String modelid
		String manufacturername
		String productname
		String uniqueid
	}
}
