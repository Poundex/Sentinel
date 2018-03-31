package net.poundex.sentinel.caretaker.home.heating.nest

import com.fasterxml.jackson.databind.ObjectMapper
import com.launchdarkly.eventsource.EventHandler
import com.launchdarkly.eventsource.EventSource
import com.launchdarkly.eventsource.MessageEvent
import groovy.transform.CompileStatic
import okhttp3.Headers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

@CompileStatic
class NestEventSourceService
{
	private final ObjectMapper objectMapper
	private final Map<NestThermostat, List<NestEventTarget>> managedDevices = [:]

	NestEventSourceService(ObjectMapper objectMapper)
	{
		this.objectMapper = objectMapper
	}

	void addEventTarget(NestThermostat thermostat, NestEventTarget eventTarget)
	{
		if( ! thermostat.serviceUrl)
			refreshServiceUrl(thermostat)

		if( ! managedDevices[thermostat])
			createClient(thermostat)

		managedDevices[thermostat] << eventTarget
	}

	void refreshServiceUrl(NestThermostat thermostat)
	{
		Response response = new OkHttpClient.Builder().followRedirects(false)
				.build().newCall(new Request.Builder()
				.url("https://developer-api.nest.com/devices/thermostats/${thermostat.nestDeviceId}")
				.addHeader("Authorization", "Bearer ${thermostat.nestAccessToken}")
				.addHeader("Accept", "application/json")
				.build()).execute()

		if(response.code() != 307)
			return

		thermostat.serviceUrl = response.header("Location")
		thermostat.save()
	}

	private void createClient(NestThermostat thermostat)
	{
		new EventSource.Builder(createEventHandler(thermostat),
				thermostat.serviceUrl.toURI())
				.headers(Headers.of([
					Authorization: "Bearer ${thermostat.nestAccessToken}".toString()]))
				.build()
				.start()
		managedDevices[thermostat] = []
	}

	private void handleClientEvent(NestThermostat thermostat, NestPayload payload)
	{
		managedDevices[thermostat].each {
			it.handleEvent(payload)
		}
	}

	private EventHandler createEventHandler(NestThermostat thermostat)
	{
		return new EventHandler() {
			@Override
			void onOpen() throws Exception
			{
				int four = 2 + 2
			}

			@Override
			void onClosed() throws Exception
			{
				int four = 2 + 2
			}

			@Override
			void onMessage(String event, MessageEvent messageEvent) throws Exception
			{
				if(event != "put")
					return
				handleClientEvent(thermostat,
					objectMapper.readValue(messageEvent.data, NestPayload))
			}

			@Override
			void onComment(String comment) throws Exception
			{
				int four = 2 + 2
			}

			@Override
			void onError(Throwable t)
			{
				int four = 2 + 2
			}
		}
	}
}
