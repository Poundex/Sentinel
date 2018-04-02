package net.poundex.sentinel.caretaker.home.lighting.hue

import feign.Feign
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import net.poudnex.sentinel.caretaker.home.lighting.hue.HueBridgeClient
import net.poudnex.sentinel.caretaker.home.lighting.hue.HueBulbDevice
import net.poundex.sentinel.caretaker.home.DeviceManager
import net.poundex.sentinel.caretaker.home.Driver
import net.poundex.sentinel.caretaker.home.Hardware
import net.poundex.sentinel.caretaker.ligting.hue.HueBridge

@CompileStatic
class HueDriverService implements Driver
{
	@Override
	void createDevices(Hardware hardware, DeviceManager deviceManager)
	{
		if( ! (hardware instanceof HueBridge))
			return
		HueBridge hueBridge = (HueBridge) hardware

		HueBridgeClient client = Feign.builder()
			.encoder(new JacksonEncoder())
			.decoder(new JacksonDecoder())
			.target(HueBridgeClient,
				"http://${hueBridge.bridgeAddress}/api/${hueBridge.bridgeUsername}".toString())

		client.everything.lights.each {
			deviceManager.register(new HueBulbDevice(hueBridge, it.key, client))
		}
	}
}
