package net.poundex.sentinel.server

import org.grails.datastore.gorm.GormEntity

class ConfigService
{

	public <T extends GormEntity<T>> T getEntity(String key, Class<T> klass)
	{
		ConfigObject config = ConfigObject.get(key)
		if( ! config)
			return null
		return klass.get(config.value)
	}
}
