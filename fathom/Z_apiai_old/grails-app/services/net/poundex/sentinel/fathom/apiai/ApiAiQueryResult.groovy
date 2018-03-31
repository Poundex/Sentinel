package net.poundex.sentinel.fathom.apiai

import groovy.transform.Canonical
import net.poundex.sentinel.server.apiai.FulfilmentRequest

@Canonical
class ApiAiQueryResult
{
	String id
	String lang
	Result result

	static class Result
	{
		String action
		boolean actionIncomplete
		List<FulfilmentRequest.Result.Context> contexts
		Fulfillment fulfillment

		static class Fulfillment
		{
			List<Message> messages

			static class Message
			{
				String speech
				int type
			}

			String speech
		}

		Metadata metadata

		static class Metadata
		{
			String intentId
			String intentName
			String webhookForSlotFillingUsed
			long webhookResponseTime
			String webhookUsed
		}

		Map<String, String> parameters

		String resolvedQuery
		double score
		String source

	}

	String sessionId
	Status status

	static class Status
	{
		int code
		String errorDetails
		String errorType
	}

	String timestamp
}
