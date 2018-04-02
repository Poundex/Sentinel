package net.poundex.sentinel.caretaker.home;

import java.util.Map;
import java.util.Set;

public interface SensorDevice extends Device
{
	Set<String> getPorts();
}
