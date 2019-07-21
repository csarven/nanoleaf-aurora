package io.github.rowak.nanoleafapi.effectbuilder;

import io.github.rowak.nanoleafapi.Color;
import io.github.rowak.nanoleafapi.Effect;

/**
 * A small helper class for easily creating new
 * flow-type effects.
 */
public class FlowEffectBuilder implements EffectBuilder
{
	private String name;
	private String version;
	private String colorType;
	private Color[] palette;
	private int maxTransTime, minTransTime;
	private int maxDelayTime, minDelayTime;
	private float flowFactor;
	private Effect.Direction direction;
	private boolean loop;
	
	/**
	 * Creates an instance of the <code>FlowEffectBuilder</code>
	 */
	public FlowEffectBuilder()
	{
		this.colorType = "HSB";
		this.version = "1.0";
		this.maxTransTime = 10;
		this.minTransTime = 10;
		this.maxDelayTime = 10;
		this.minDelayTime = 10;
	}
	
	/**
	 * Creates a new flow-type effect using the data
	 * stored in the <code>EffectBuilder</code>
	 */
	public Effect build()
	{	
		Effect effect = new Effect();
		effect.setName(name);
		effect.setVersion(version);
		effect.setAnimType(Effect.Type.FLOW);
		effect.setColorType(colorType);
		effect.setPalette(palette);
		effect.setMaxTransTime(maxTransTime);
		effect.setMinTransTime(minTransTime);
		effect.setMaxDelayTime(maxDelayTime);
		effect.setMinDelayTime(minDelayTime);
		effect.setFlowFactor(flowFactor);
		effect.setDirection(direction);
		effect.setLoop(loop);
		return effect;
	}

	public FlowEffectBuilder setName(String name)
	{
		this.name = name;
		return this;
	}

	public FlowEffectBuilder setColorType(String colorType)
	{
		this.colorType = colorType;
		return this;
	}

	public FlowEffectBuilder setPalette(Color[] palette)
	{
		this.palette = palette;
		return this;
	}
	
	/**
	 * Sets the maximum transition time for the effect.
	 * @param transTime  the desired transition time
	 * @return  the current <code>EffectBuilder</code>
	 */
	public FlowEffectBuilder setMaxTransTime(int transTime)
	{
		this.maxTransTime = transTime;
		return this;
	}
	
	/**
	 * Sets the minimum transition time for the effect.
	 * @param transTime  the desired transition time
	 * @return  the current <code>EffectBuilder</code>
	 */
	public FlowEffectBuilder setMinTransTime(int transTime)
	{
		this.minTransTime = transTime;
		return this;
	}
	
	/**
	 * Sets the maximum delay time for the effect.
	 * @param delay  the desired delay time
	 * @return  the current <code>EffectBuilder</code>
	 */
	public FlowEffectBuilder setMaxDelayTime(int delay)
	{
		this.maxDelayTime = delay;
		return this;
	}
	
	/**
	 * Sets the minimum delay time for the effect.
	 * @param delay  the desired delay time
	 * @return  the current <code>EffectBuilder</code>
	 */
	public FlowEffectBuilder setMinDelayTime(int delay)
	{
		this.minDelayTime = delay;
		return this;
	}
	
	/**
	 * Sets the flow factor for the effect.
	 * @param flowFactor  the flow factor
	 * @return  the current <code>EffectBuilder</code>
	 */
	public FlowEffectBuilder setFlowFactor(float flowFactor)
	{
		this.flowFactor = flowFactor;
		return this;
	}
	
	/**
	 * Sets the overall direction of movement for the effect.
	 * @param direction  the direction of movement for the effect
	 * @return  the current <code>EffectBuilder</code>
	 */
	public FlowEffectBuilder setDirection(Effect.Direction direction)
	{
		this.direction = direction;
		return this;
	}

	public FlowEffectBuilder setLoop(boolean loop)
	{
		this.loop = loop;
		return this;
	}
	
	/**
	 * Sets the version of the effect.
	 * <br><b>Note: This value should always be "1.0".</b>
	 * @param version  the version of the effect
	 * @return  the current <code>EffectBuilder</code>
	 */
	public FlowEffectBuilder setVersion(String version)
	{
		this.version = version;
		return this;
	}
}
