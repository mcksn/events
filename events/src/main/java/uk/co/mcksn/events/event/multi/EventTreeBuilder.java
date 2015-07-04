package uk.co.mcksn.events.event.multi;

public class EventTreeBuilder {
	public static EventTree and(EventTree ...leaves)
	{
		return new AndEventTree(leaves);
	}
	
	public static OrEventTree or(EventTree ...leaves)
	{
		return new OrEventTree(leaves);
	}
}
