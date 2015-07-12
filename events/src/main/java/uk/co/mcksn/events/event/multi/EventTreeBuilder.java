package uk.co.mcksn.events.event.multi;

public class EventTreeBuilder {

	public static EventTreeable and(EventTreeable... leaves) {
		return new AndEventTree(leaves);
	}

	public static OrEventTree or(EventTreeable... leaves) {
		return new OrEventTree(leaves);
	}

}
