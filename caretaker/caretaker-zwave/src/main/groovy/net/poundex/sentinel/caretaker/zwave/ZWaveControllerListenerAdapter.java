package net.poundex.sentinel.caretaker.zwave;

import com.whizzosoftware.wzwave.controller.ZWaveControllerListener;
import com.whizzosoftware.wzwave.node.NodeInfo;
import com.whizzosoftware.wzwave.node.ZWaveEndpoint;

interface ZWaveControllerListenerAdapter extends ZWaveControllerListener
{
	@Override
	default void onZWaveNodeAdded(ZWaveEndpoint node)
	{

	}

	@Override
	default void onZWaveNodeUpdated(ZWaveEndpoint node)
	{

	}

	@Override
	default void onZWaveConnectionFailure(Throwable t)
	{

	}

	@Override
	default void onZWaveControllerInfo(String libraryVersion, Integer homeId, Byte nodeId)
	{

	}

	@Override
	default void onZWaveInclusionStarted()
	{

	}

	@Override
	default void onZWaveInclusion(NodeInfo nodeInfo, boolean success)
	{

	}

	@Override
	default void onZWaveInclusionStopped()
	{

	}

	@Override
	default void onZWaveExclusionStarted()
	{

	}

	@Override
	default void onZWaveExclusion(NodeInfo nodeInfo, boolean success)
	{

	}

	@Override
	default void onZWaveExclusionStopped()
	{

	}
}
