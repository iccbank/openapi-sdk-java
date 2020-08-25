package net.iccbank.openapi.sdk.enums;

public enum LinkTypeEnum {
	
	ETHEREUM("ethereum"),
	TRON("tron")
	
	;

	private String name;
	
	LinkTypeEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public static LinkTypeEnum valueOfByName(String name) {
		for (LinkTypeEnum e : values()) {
			if (e.getName().equalsIgnoreCase(name)) {
				return e;
			}
		}
		return null;
	}
	
}
