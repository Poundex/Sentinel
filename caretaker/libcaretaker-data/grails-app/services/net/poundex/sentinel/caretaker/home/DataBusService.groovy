package net.poundex.sentinel.caretaker.home

import grails.compiler.GrailsCompileStatic
import org.springframework.beans.factory.annotation.Qualifier

import java.util.concurrent.ThreadPoolExecutor

@GrailsCompileStatic
class DataBusService implements DataBus
{
	private final ThreadPoolExecutor pool
	private final List<Closure> portValueAnnouncedListeners = []

	DataBusService(@Qualifier("caretakerWorkerPool") ThreadPoolExecutor pool)
	{
		this.pool = pool
	}

	@Override
	public <T> void announcePortValue(PortValue<T> portValue)
	{
		pool.execute {
			log.debug("Received value to announcePortValue ${portValue}")
			List<SensorReader> readers = SensorReader.findAllByDeviceIdAndPortId(
					portValue.device.deviceId,
					portValue.portId)

			if (readers.empty)
				return

			readers.each { r ->
				portValueAnnouncedListeners.each { final listener ->
					pool.execute {
						listener.call(r.monitor, portValue)
					}
				}
			}
		}
	}

	@Override
	void addPortValueAnnouncementListener(Closure listener)
	{
		portValueAnnouncedListeners << listener
	}

	@Override
	void setPortValues(Appliance appliance, Map<String, Object> portValues)
	{
		throw new RuntimeException("Shouldn't be here")
	}
}
