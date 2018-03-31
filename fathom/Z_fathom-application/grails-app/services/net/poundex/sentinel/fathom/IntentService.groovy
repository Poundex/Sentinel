package net.poundex.sentinel.fathom

class IntentService implements IntentRegistry
{
	private final Map<String, Intent> intents = [:]

	IntentService(List<Intent> intents)
	{
		intents*.registerIn(this)
	}

	IntentResult submit(QueryResult queryResult)
	{
		Intent intent = intents[queryResult.text]
		if( ! intent)
			return new NoSuchIntent()

		return intent.run()
	}

	@Override
	void register(Intent intent, String intentName)
	{
		intents[intentName] = intent
	}
}
