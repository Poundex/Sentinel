package net.poundex.sentinel.server

import javax.persistence.Id

class ConfigObject
{
//	@Id
	String key
	String value

    static constraints = {
    }

	static mapping = {
		id name: 'key'
	}
}
