package net.poundex.sentinel.fathom

import grails.artefact.controller.support.ResponseRenderer


class ResponseService
{
	private final ResponseRendererService responseRendererService

	ResponseService(ResponseRendererService responseRendererService)
	{
		this.responseRendererService = responseRendererService
	}

	ResponsePackage respondTo(IntentResult intentResult)
	{
		ResponsePackage responsePackage = new ResponsePackage()
		List<Response> responses = intentResult.respond()
		responses.each {
			responseRendererService.render(it, responsePackage)
		}
		return responsePackage
	}
}

// Answer "The date is {}", $date
// Speech "The date is date"

