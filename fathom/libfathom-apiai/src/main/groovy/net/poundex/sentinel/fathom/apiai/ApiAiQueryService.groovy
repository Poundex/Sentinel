package net.poundex.sentinel.fathom.apiai

import net.poundex.sentinel.fathom.Query
import net.poundex.sentinel.fathom.QueryResult
import net.poundex.sentinel.fathom.QueryService
import org.springframework.stereotype.Service

@Service
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
		return new QueryResult(apiAiQueryClient.query(
				Class.forName('net.poundex.sentinel.server.StupidSecretsProvider').getInstance().secrets.apiai.accessToken,
				new ApiAiQuery(query.query, sessionId)).result.metadata.intentName)
	}
}
