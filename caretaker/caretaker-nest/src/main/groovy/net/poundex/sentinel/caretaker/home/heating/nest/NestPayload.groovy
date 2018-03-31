package net.poundex.sentinel.caretaker.home.heating.nest

import com.fasterxml.jackson.annotation.JsonProperty

class NestPayload
{
	public String path

	@Delegate
	public Data data

	static class Data
	{
		public int humidity
		public String locale
		@JsonProperty("temperature_scale")
		public String temperatureScale
		@JsonProperty("ambient_temperature_c")
		public double ambientTemperatureC
	}
}
