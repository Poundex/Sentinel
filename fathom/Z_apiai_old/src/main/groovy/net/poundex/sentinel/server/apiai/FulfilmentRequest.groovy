package net.poundex.sentinel.server.apiai

import groovy.transform.Canonical

@Canonical
class FulfilmentRequest
{
	Map originalRequest
	String id
	String sessionId
	String timestamp
	String timezone
	String lang
	Result result

	static class Result
	{
		String source
		String resolvedQuery
		String speech
		String action
		boolean actionIncomplete
		Map parameters
		List<Context> contexts

		static class Context
		{
			String name
			Map parameters
			Number lifespan
		}

		Metadata metadata

		static class Metadata
		{
			String intentId
			boolean webhookUsed
			boolean webhoolForSlotFillingUsed
			Number nluResponseTime
			String intentName
		}

		Fulfilment fulfilment

		static class Fulfilment
		{
			String speech
			List messages
			Number score
		}
	}
}
