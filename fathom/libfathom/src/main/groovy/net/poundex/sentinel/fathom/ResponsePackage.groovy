package net.poundex.sentinel.fathom

import groovy.transform.Canonical
import groovy.transform.Immutable

@Canonical
class ResponsePackage
{
	List<ResponseCommand> responseCommands = []
}
