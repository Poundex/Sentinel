package net.poundex.sentinel.caretaker.environment

import net.poundex.sentinel.caretaker.home.Sensor

interface MonitorHandler
{
	void publish(Sensor sensor, Object value)
}
