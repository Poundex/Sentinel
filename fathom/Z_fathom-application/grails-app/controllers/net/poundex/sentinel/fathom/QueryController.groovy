package net.poundex.sentinel.fathom

import grails.converters.JSON

class QueryController
{
	private final QueryService queryService
	private final IntentService intentService
	private final ResponseService responseService

	QueryController(QueryService queryService, IntentService intentService, ResponseService responseService)
	{
		this.queryService = queryService
		this.intentService = intentService
		this.responseService = responseService
	}

	def process(Query query)
	{
		QueryResult queryResult = queryService.query(query)
		IntentResult intentResult = intentService.submit(queryResult)
		ResponsePackage responsePackage = responseService.respondTo(intentResult)
		render(responsePackage as JSON)
	}
}
