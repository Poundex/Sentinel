package net.poundex.sentinel.fathom.apiai

import grails.gorm.transactions.Transactional
import net.poundex.sentinel.fathom.Query
import net.poundex.sentinel.fathom.QueryResult
import net.poundex.sentinel.fathom.QueryService
import net.poundex.sentinel.server.apiai.ApiAiQueryClient

class ApiAiQueryService implements QueryService
{
	private final ApiAiQueryClient apiAiQueryClient
	static final String sessionId = UUID.randomUUID().toString()

	ApiAiQueryService(ApiAiQueryClient apiAiQueryClient)
	{
		this.apiAiQueryClient = apiAiQueryClient
	}

	@Override
	QueryResult query(Query query)
	{
		return new QueryResult(apiAiQueryClient.query(new ApiAiQuery(query.query, sessionId)).result.metadata.intentName)
	}
}
