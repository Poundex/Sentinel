package net.poundex.sentinel.server

import com.moandjiezana.toml.Toml

@Singleton
class StupidSecretsProvider
{
	final Map<String, Map<String, String>> secrets = new Toml().read(
			new File(fileLocation).text)
			.toMap()

	private static String getFileLocation()
	{
		return (System.properties['secretsDir'] ?: "/home/poundex/workspaces/pounder/sentinel") + "/sentinel-secrets.toml"
	}
}
