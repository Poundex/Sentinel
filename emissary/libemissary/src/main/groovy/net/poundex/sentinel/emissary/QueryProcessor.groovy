package net.poundex.sentinel.emissary

import net.poundex.sentinel.fathom.Query
import net.poundex.sentinel.fathom.QueryClient
import net.poundex.sentinel.scribe.ProcessSpeechResult
import org.springframework.stereotype.Service

@Service
class QueryProcessor implements ControlListener
{
	private final QueryClient queryClient
	private final Responder responder

	QueryProcessor(ControlManager controlManager, QueryClient queryClient, Responder responder)
	{
		this.queryClient = queryClient
		this.responder = responder
		controlManager.addControlListener(this)
	}

	@Override
	void onListeningStarted()
	{

	}

	@Override
	void onHotwordRecognised()
	{

	}

	@Override
	void onSpeechProcessed(ProcessSpeechResult processSpeechResult)
	{
		processQuery(new Query(processSpeechResult.speech))
	}

	void processQuery(Query query)
	{
		responder.respond(queryClient.query(query))
	}

	@Override
	void onSpeechReceived(byte[] bytes)
	{

	}
}
