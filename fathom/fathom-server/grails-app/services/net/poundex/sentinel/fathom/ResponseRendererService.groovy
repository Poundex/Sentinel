package net.poundex.sentinel.fathom

import static net.poundex.sentinel.fathom.ResponseCommand.Type.SPEAK

class ResponseRendererService
{
	private final SpeechRendererService speechRendererService

	ResponseRendererService(SpeechRendererService speechRendererService)
	{
		this.speechRendererService = speechRendererService
	}
	private final Map<Class<? extends Response>, Closure<ResponseCommand>> renderers = [
			(SpeechResponse): this.&renderSpeech,
	]

	void render(Response response, ResponsePackage responsePackage)
	{
		responsePackage.responseCommands << renderers[response.class](response)
	}

	ResponseCommand renderSpeech(SpeechResponse response)
	{
		new ResponseCommand(SPEAK, [speech: speechRendererService.renderSpeech(response.speech, response.args)])
	}
}
