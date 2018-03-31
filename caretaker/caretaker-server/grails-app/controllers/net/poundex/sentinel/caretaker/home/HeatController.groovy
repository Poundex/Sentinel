package net.poundex.sentinel.caretaker.home

class HeatController
{
	private final HeatService heatService

	HeatController(HeatService heatService)
	{
		this.heatService = heatService
	}

	def status()
	{
		Optional<Boolean> heatStatus = heatService.isHeatingActive()

	}

}
