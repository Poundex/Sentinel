package net.poundex.sentinel.fathom

import groovy.transform.ToString

@ToString
class ResponseCommand
{
	Type command
	Map<String, String> args

	ResponseCommand()
	{
	}

	ResponseCommand(Type command, Map<String, String> args)
	{
		this.command = command
		this.args = args
	}

	static enum Type
	{
		SPEAK
	}
}
